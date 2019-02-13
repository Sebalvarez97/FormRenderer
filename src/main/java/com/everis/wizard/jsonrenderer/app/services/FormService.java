package com.everis.wizard.jsonrenderer.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.converter.FormJsonConverter;
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

	@Value("${application.formservice.baseUrl}")
	private String BASE_URL;

	@Value("${application.formservice.formRepositoryURL}")
	private String FORM_REPOSITORY_URL;

	@Value("${application.formservice.modelUrl}")
	private String FORM_MODEL_URL;
	
	/*
	 Gets a SimpleFormModel and a Map of Attributes
	 Attributes:
	 	obligatory:
	 		pageTitle: title of the page (String)
	 	optional:
	 		stylesheets: List of style sheets sources to add (String)
	 		scripts: List of scripts to use as String (List<String>)
	 		scriptSrc: List of script sources to add (List<String>)
	 		
	 		submitbuttonClass: class to set in the submit button (String)
	 		submitbuttonAction: action to set in the submit button (String)
	 		submitbuttonMethod: method to set in the submit button (String)
	 		
	 		formClass: class to set in the submit button (String)
	 		formAction: action to set in the submit button (String)
	 		formMethod: method to set in the submit button (String)
	*/
	public String getFormById(String formId) throws FormServiceException {
		SimpleFormModel formModel = getFormModel(formId);
		Map<String, Object> htmlmodel = new HashMap<String, Object>();
		htmlmodel.put("pageTitle", "Wizard");
		return formRenderer.getHtmlForm(formModel, htmlmodel);
	}
	
	public String getFormByKey(String formKey) throws FormServiceException {
		return getFormById(getByKey(formKey).getId());
	}

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

	public List<SimpleFormModel> getAll() throws FormServiceException {
		List<SimpleFormModel> models = new ArrayList<SimpleFormModel>();
		HttpResponse<JsonNode> result;
		try {
			result = Unirest.get(AllFormsURL()).asJson();
		} catch (UnirestException e) {
			throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}
		JSONArray arr = (JSONArray) result.getBody().getObject().get("data");
		for (int i = 0; i < arr.length(); i++) {
			String id = arr.getJSONObject(i).get("id").toString();
			SimpleFormModel formModel = getFormModel(id);
			models.add(formModel);
		}
		return models;
	}
	
	private SimpleFormModel getByKey(String formKey) throws FormServiceException {
		SimpleFormModel form = null;
		int version = 0;
		for (SimpleFormModel formModel : getAll()) {
			if(formModel.getKey().equals(formKey)) {
				if(formModel.getVersion()> version) {
					form = formModel;
					version = formModel.getVersion();
				}
			}
		}
		if(form == null) {
			throw new FormServiceException("Fail to find by formKey");
		}
		return form;
	}

	private String FormUrl(String id) throws FormServiceException {
		if (id == null) {
			throw new FormServiceException("Null Id Exception");
		}
		return String.format("%s/%s/%s/%s", BASE_URL, FORM_REPOSITORY_URL, id, FORM_MODEL_URL);
	}
	private String AllFormsURL() {
		return String.format("%s/%s", BASE_URL, FORM_REPOSITORY_URL);
	}
}
