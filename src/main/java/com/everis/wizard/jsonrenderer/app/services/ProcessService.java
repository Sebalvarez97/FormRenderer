package com.everis.wizard.jsonrenderer.app.services;

import java.util.ArrayList;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.dtos.FieldDto;
import com.everis.wizard.jsonrenderer.app.dtos.ProcessRequestDto;
import com.everis.wizard.jsonrenderer.app.dtos.TaskResponseDto;
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
	private String BASE_URL;

	private String ALLPROCESS_URL = "flowable-rest/service/repository/process-definitions";

	private String CREATE_PROCESS_URL = "flowable-rest/service/runtime/process-instances";

	private String GETTASK_URL = "flowable-rest/service/runtime/tasks";

	private String GET_START_FORM_URL = "flowable-rest/service/repository/process-definitions";

	private String START_FORM_URL = "start-form";

	@Autowired
	private FormService formService;

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
			System.out.println(UrlCreateProcess());

			System.out.println(json);

			JSONObject obj = new JSONObject(json);
			System.out.println(obj);
			result = Unirest.post(UrlCreateProcess()).header("Content-Type", "application/json").body(obj).asJson();

			// Obtengo el process Instance ID
			String pdid = (String) result.getBody().getObject().get("id");

			// Obtengo el task Id del proceso
			String taskId = GetCurrentTaskId(pdid);

			return  "Task ID " + taskId + "\n" +
					"Process ID " + pdid + "\n" +
					formService.getFormByTaskId(taskId);

		} catch (Exception e) {

			System.out.println("Exceptiion e " + e);
			return "";
			// throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}

	}

	public String GetFormByTaskId(String taskId) {
		try {
			return formService.getFormByTaskId(taskId);
		} catch (FormServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error Ocurred";
	}

	String GetCurrentTaskId(String processInstanceId) {

		HttpResponse<JsonNode> result2 = null;
		String taskId = "null";
		try {

			result2 = Unirest.get(UrlGetTaskInstanceID() + "?processInstanceId=" + processInstanceId).asJson();

			JSONArray arr = (JSONArray) result2.getBody().getObject().get("data");

			System.out.println(arr.getJSONObject(0).toString());

			taskId = arr.getJSONObject(0).get("id").toString();

			return taskId;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return taskId;
	}

	String GetProcessInstanceIdFromTaskId(String taskId) {
		HttpResponse<JsonNode> result = null;
		try {
		
			String url = UrlSubmitTask(taskId);
			System.out.println("Url " + url);
			result = Unirest.get(url).asJson();
			System.out.println("ResultLog " + result.getBody().toString());
			return result.getBody().getObject().getString("processInstanceId");
		} catch (Exception e) {
			System.out.println(e);
		}
		return result.toString();
	}

	public String GetNextForm(String processInstanceId) {

		try {
			String taskId = GetCurrentTaskId(processInstanceId);
			return formService.getFormByTaskId(taskId);
		} catch (FormServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Some error ocurred";
	}

	public String SubmitTaskResults(String taskId, TaskResponseDto tdto) {
		
		System.out.println("Submit Task Results " + taskId);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		
		String pid = GetProcessInstanceIdFromTaskId(taskId); 
		System.out.println("Process Instance Id " + pid);
		try {

			json = mapper.writeValueAsString(tdto);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HttpResponse<JsonNode> result = null;
		try {		
			JSONObject obj = new JSONObject(json);
			result = Unirest.post(UrlSubmitTask(taskId)).header("Content-Type", "application/json").body(obj).asJson();

			
			
			String nextTask = GetCurrentTaskId(pid);			
			System.out.println("Next Task Id " + nextTask);
			
			return formService.getFormByTaskId(nextTask);

		} catch (Exception e) {

			System.out.println("Exceptiion e " + e);
			return "";
			// throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}

	}

	
	
	
	// Get all Process Definitions
	public String GetAll() {

		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(UrlProcessDefinitions());
			result = Unirest.get(UrlProcessDefinitions()).asJson();
			System.out.println(result.toString());
		} catch (UnirestException e) {
			System.out.println("Exceptiion e " + e);
			// throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}

		return result.getBody().toString();

	}

	// Get all Process Definitions with key..
	public String GetAll(String key) {

		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(UrlProcessDefinitions());
			result = Unirest.get(UrlProcessDefinitions() + "?key=" + key).asJson();
			System.out.println(result.toString());
		} catch (UnirestException e) {
			System.out.println("Exceptiion e " + e);
			// throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}

		return result.getBody().toString();

	}

	// Get latest Process Definitions with key..
	public String GetLatest(String key) {

		HttpResponse<JsonNode> result = null;
		try {
			System.out.println(UrlProcessDefinitions());
			result = Unirest.get(UrlProcessDefinitions() + "?key=" + key + "&latest=" + true).asJson();
			System.out.println(result.toString());
		} catch (UnirestException e) {
			System.out.println("Exceptiion e " + e);
			// throw new FormServiceException("Fail to get JSON Array", e.getCause());
		}

		return result.getBody().toString();
	}

	String UrlSubmitTask(String taskId) {
		return String.format("%s/%s/%s", BASE_URL, GETTASK_URL, taskId);
	}

	String UrlGetTaskInstanceID() {
		return String.format("%s/%s", BASE_URL, GETTASK_URL);
	}

	String UrlStartForm(String processId) {
		return String.format("%s/%s/%s/%s", BASE_URL, ALLPROCESS_URL, processId, START_FORM_URL);
	}

	String UrlCreateProcess() {
		return String.format("%s/%s", BASE_URL, CREATE_PROCESS_URL);
	}

	String UrlProcessDefinitions() {
		return String.format("%s/%s", BASE_URL, ALLPROCESS_URL);
	}
}
