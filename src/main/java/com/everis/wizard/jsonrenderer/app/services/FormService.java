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

	public String getForm(String formId) throws FormServiceException {
		SimpleFormModel formModel = getFormModel(formId);
		Map<String, Object> htmlmodel = new HashMap<String, Object>();
		htmlmodel.put("pageTitle", "TestPage");
		return formRenderer.getHtmlForm(formModel, htmlmodel);
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

	private String FormUrl(String id) throws FormServiceException {
		if (id == null) {
			throw new FormServiceException("Null Id Exception");
		}
		return String.format("%s/%s/%s/%s", BASE_URL, FORM_REPOSITORY_URL, id, FORM_MODEL_URL);
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

	private String AllFormsURL() {
		return String.format("%s/%s", BASE_URL, FORM_REPOSITORY_URL);
	}
}
