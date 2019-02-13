package com.everis.wizard.jsonrenderer.app.services;

import java.util.ArrayList;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.dtos.FieldDTO;
import com.everis.wizard.jsonrenderer.app.dtos.ProcessRequestDto;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.services.exceptions.FormServiceException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;



@Service
public class ProcessService {

	@Value("${application.formservice.baseUrl}")
	private String BASE_URL ;
	
	private String ALLPROCESS_URL = "flowable-rest/service/repository/process-definitions";

	
	private String CREATE_PROCESS_URL = "flowable-rest/service/runtime/process-instances";
	
	private String GETTASK_URL = "flowable-rest/service/runtime/tasks";

	@Autowired
	private FormService formService;
	
	public String GetAll() {
		
		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(AllProcessUrl());
			result = Unirest.get(AllProcessUrl()).asJson();
			System.out.println(result.toString());
		} catch (UnirestException e) {
			System.out.println("Exceptiion e " + e);
			//throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}
		
	return result.getBody().toString();
		
	}
	
	
	/*
	public String CreateProcess(String key) {
		
		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(CreateProcessWithFormVariablesUrl());
			
		//ProcessRequestDto dto = new ProcessRequestDto(key);
		//dto.AddField(new FieldDTO("name", "string", "Nicolas"));
		//dto.AddField(new FieldDTO("sexo", "string", "Otro"));
		result = Unirest.post(CreateProcessWithFormVariablesUrl()).body(dto).asJson();
			
		} catch (UnirestException e) {
			System.out.println("Exceptiion e " + e);
			//throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}
		
		return result.toString();
	}
	*/
	public String CreateProcess(ProcessRequestDto pdto) {
	
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
		
			json = mapper.writeValueAsString(pdto);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(CreateProcessWithFormVariablesUrl());
			
			System.out.println(json);
			
			JSONObject obj = new JSONObject(json);
			System.out.println(obj);
			result = Unirest.post(CreateProcessWithFormVariablesUrl()).
					 header("Content-Type", "application/json").
					body(obj).
					asJson();
			
			String pdid = (String)result.getBody().getObject().get("id");
			
			HttpResponse<JsonNode> result2 = null;
			result2 = Unirest.get(GetTaskInstanceID() + "?processInstanceId="+pdid).asJson();
			
			
			System.out.println("Obje" + result2.getBody().getObject().toString());
			System.out.println("Body" + result2.getBody().toString());
			
			JSONArray arr = (JSONArray) result2.getBody().getObject().get("data");
			
			
			System.out.println(arr.getJSONObject(0).toString());
			String fk = arr.getJSONObject(0).get("id").toString();
				
			return formService.getFormByTaskId(fk);
			
		} catch (Exception e) {
			
			
			System.out.println("Exceptiion e " + e);
			return "";
			//throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}
		
		
	}
	
	
	public String GetTaskInstanceID() {
		return String.format("%s/%s", BASE_URL, GETTASK_URL);	
		
	}
	public String GetAll(String key) {
		
		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(AllProcessUrl());
			result = Unirest.get(AllProcessUrl() + "?key=" + key).asJson();
			System.out.println(result.toString());
		} catch (UnirestException e) {
			System.out.println("Exceptiion e " + e);
			//throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}
		
	return result.getBody().toString();
		
	}

	public String GetLatest(String key) {
		
		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(AllProcessUrl());
			result = Unirest.get(AllProcessUrl() + "?key=" + key + "&latest=" + true).asJson();
			System.out.println(result.toString());
		} catch (UnirestException e) {
			System.out.println("Exceptiion e " + e);
			//throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}
		
	return result.getBody().toString();
	}
	
	String CreateProcessWithFormVariablesUrl() {		
		return String.format("%s/%s", BASE_URL, CREATE_PROCESS_URL);	
	}
	
	String AllProcessUrl() {
		return String.format("%s/%s", BASE_URL, ALLPROCESS_URL);		
	}
}
