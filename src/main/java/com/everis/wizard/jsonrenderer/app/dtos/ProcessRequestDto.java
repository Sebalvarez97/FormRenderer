package com.everis.wizard.jsonrenderer.app.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessRequestDto {

	
	private String processDefinitionKey;
	private ArrayList<FieldDto> startFormVariables;
	
	public ProcessRequestDto() {
		
	}
	
	public ProcessRequestDto(String processDefinitionKey) {
	this.processDefinitionKey = processDefinitionKey;	
	}
	public ProcessRequestDto(String processDefinitionKey, ArrayList<FieldDto> startFormVariables) {
		super();
		this.processDefinitionKey = processDefinitionKey;
		this.startFormVariables = startFormVariables;
	}
	
	public void AddField(FieldDto field) {
		
		startFormVariables.add(field);
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public List<FieldDto> getStartFormVariables() {
		return startFormVariables;
	}
	public void setStartFormVariables(ArrayList<FieldDto> startFormVariables) {
		this.startFormVariables = startFormVariables;
	}
	
}

