package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.converter.FormJsonConverter;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class FormService {

	@Autowired
	private FormRenderer formRenderer;

	private static final String BASE_URL = "http://admin:test@192.168.1.135:8080";

	private static final String FORM_REPOSITORY_URL = "flowable-task/form-api/form-repository/form-definitions";

	private static final String FORM_MODEL_URL = "model";


	public String getForm(String formId) throws UnirestException {
		SimpleFormModel formModel = getFormModel(formId);
		return formRenderer.getHtmlForm(formModel);
	}

	public SimpleFormModel getFormModel(String formId) throws UnirestException {
		HttpResponse<JsonNode> result = Unirest.get(FormUrl(formId)).asJson();
		String testJsonResource = result.getBody().getObject().toString();
		SimpleFormModel formModel = new FormJsonConverter().convertToFormModel(testJsonResource);
		return formModel;
	}
	
	private String FormUrl(String id) {
		return  String.format("%s/%s/%s/%s", BASE_URL, FORM_REPOSITORY_URL, id, FORM_MODEL_URL);
	}
}
