package com.everis.wizard.jsonrenderer.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.converter.FormJsonConverter;
import com.everis.wizard.jsonrenderer.app.dtos.FieldResponseDto;
import com.everis.wizard.jsonrenderer.app.dtos.FormResponseDto;
import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.services.exceptions.FormServiceException;
import com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class FormService implements IFormService {

	@Autowired
	private FormRenderer formRenderer;
	
	@Autowired
	private HtmlManager htmlManager;

	@Value("${application.formservice.flowableUrl}")
	private String BASE_URL;

	@Value("${application.formservice.formRepositoryURL}")
	private String FORM_REPOSITORY_URL;

	@Value("${application.formservice.modelUrl}")
	private String FORM_MODEL_URL;

	@Value("${application.formservice.flowable.user}")
	private String FLOWABLE_USER;

	@Value("${application.formservice.flowable.password}")
	private String FLOWABLE_PASS;

	/*
	 * Returns a HtmlForm by formId using a Map<String, Object> (parameter,value)
	 * Paramaters: (String) formId (non-Javadoc)
	 * 
	 * @see com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService#
	 * getFormById(java.lang.String)
	 */
	public String getFormById(String formId, Map<String, Object> model) throws FormServiceException {
		SimpleFormModel formModel = getFormModel(formId);
		Map<String, Object> htmlmodel = new HashMap<String, Object>();
		List<String> scripts = new ArrayList<String>();
		List<String> scriptsrc = new ArrayList<String>();
		scriptsrc.add("/src/main/resources/static/js/scripts.js");
		scripts.add("function myFunction(){alert()}");
		// htmlmodel.put("formMethod", "post");
		// htmlmodel.put("formAction", "/form-renderer/form/save");
		// htmlmodel.put("scripts", scripts);s
		// htmlmodel.put("formOnsubmit", "myFunction()");
		return formRenderer.getHtmlForm("Wizard", formModel, htmlmodel);
	}

	public String writeHtmlFormById(String templatename, String formId, Map<String, Object> model)
			throws FormServiceException {
		SimpleFormModel formModel = getFormModel(formId);
		int index = 0;
		for (FormField field : formModel.getFields()) {
			System.out.println(index);
			System.out.println(field.getId());
			System.out.println(field.getName());
			System.out.println(field.getValue());
			index++;
		}
		model.put("form", formModel);
		model.put("formMethod", "post");
		model.put("formAction", "/form-renderer/form/save");
		String htmlString = formRenderer.getHtmlTemplate("Wizard", formModel, model);
		System.out.println(htmlString);
		try {
			htmlManager.writeTemplate(htmlString, templatename);
			return templatename;
		} catch (IOException e) {
			throw new FormServiceException("Fail to write Template: " + templatename, e.getCause());
		}
	}

	public String writeHtmlFormByKey(String templatename, String formKey, Map<String, Object> model)
			throws FormServiceException {
		return writeHtmlFormById(templatename, getByKey(formKey).getId(), model);
	}

	/*
	 * Returns a htmlform(String) by a formKey(String) (non-Javadoc)
	 * 
	 * @see com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService#
	 * getFormByKey(java.lang.String)
	 */
	public String getFormByKey(String formKey, Map<String, Object> model) throws FormServiceException {
		return getFormById(getByKey(formKey).getId(), model);
	}

	/*
	 * Returns a SimpleFormModel by a formId (String) (non-Javadoc)
	 * 
	 * @see com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService#
	 * getFormModel(java.lang.String)
	 */
	public SimpleFormModel getFormModel(String formId) throws FormServiceException {
		String testJsonResource = null;
		String formURL = FormUrl(formId);
		try {
			testJsonResource = Unirest.get(formURL).asJson().getBody().getObject().toString();
		} catch (UnirestException e) {
			throw new FormServiceException("Fail to get JSON from URL: " + formURL, e.getCause());
		}
		return new FormJsonConverter().convertToFormModel(testJsonResource);
	}

	/*
	 * Returns a List<SimpleFormModel> with all the Forms in FlowableRepository
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService#getAll()
	 */
	public List<SimpleFormModel> getAll() throws FormServiceException {
		List<SimpleFormModel> models = new ArrayList<SimpleFormModel>();
		HttpResponse<JsonNode> result;
		try {
			result = Unirest.get(AllFormsURL()).asJson();
		} catch (UnirestException e) {
			throw new FormServiceException("Fail to get JSON Array from URL: " + AllFormsURL(), e.getCause());
		}
		JSONArray arr = (JSONArray) result.getBody().getObject().get("data");
		for (int i = 0; i < arr.length(); i++) {
			String id = arr.getJSONObject(i).get("id").toString();
			SimpleFormModel formModel = getFormModel(id);
			models.add(formModel);
		}
		return models;
	}
	/*
	 * Returns a SimpleFormModel by a formKey (String)
	 */
	private SimpleFormModel getByKey(String formKey) throws FormServiceException {
		SimpleFormModel form = null;
		int version = 0;
		for (SimpleFormModel formModel : getAll()) {
			if (formModel.getKey().equals(formKey)) {
				if (formModel.getVersion() > version) {
					form = formModel;
					version = formModel.getVersion();
				}
			}
		}
		if (form == null) {
			throw new FormServiceException("Fail to find by formKey");
		}
		return form;
	}

	/*
	 * Returns the Flowable baseUrl
	 */
	private String getBaseUrl() {
		return String.format(BASE_URL, FLOWABLE_USER, FLOWABLE_PASS);
	}

	/*
	 * Returns the Url to use in a HttpRequest Gets the formModel by the id
	 */
	private String FormUrl(String id) throws FormServiceException {
		if (id == null) {
			throw new FormServiceException("Null Id Exception");
		}
		return String.format("%s/%s/%s/%s", getBaseUrl(), FORM_REPOSITORY_URL, id, FORM_MODEL_URL);
	}

	/*
	 * Returns the Url for a HttpRequest Gets the last version of all forms
	 */
	private String AllFormsURL() {
		return String.format("%s/%s", getBaseUrl(), FORM_REPOSITORY_URL + "?latest=true");
	}

}
