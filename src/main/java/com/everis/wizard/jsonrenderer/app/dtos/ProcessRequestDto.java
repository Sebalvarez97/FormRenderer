package com.everis.wizard.jsonrenderer.app.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessRequestDto {

	
	
	/*
		 private String processDefinitionKey;
		 ArrayList < Object > startFormVariables = new ArrayList < Object > ();


		 // Getter Methods 

		 public String getProcessDefinitionKey() {
		  return processDefinitionKey;
		 }

		 // Setter Methods 

		 public void setProcessDefinitionKey(String processDefinitionKey) {
		  this.processDefinitionKey = processDefinitionKey;
		 }
		
*/
		 
	
	private String processDefinitionKey;
	private ArrayList<FieldDTO> startFormVariables;
	
	public ProcessRequestDto() {
		
	}
	
	public ProcessRequestDto(String processDefinitionKey) {
	this.processDefinitionKey = processDefinitionKey;	
	}
	public ProcessRequestDto(String processDefinitionKey, ArrayList<FieldDTO> startFormVariables) {
		super();
		this.processDefinitionKey = processDefinitionKey;
		this.startFormVariables = startFormVariables;
	}
	
	public void AddField(FieldDTO field) {
		
		startFormVariables.add(field);
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public List<FieldDTO> getStartFormVariables() {
		return startFormVariables;
	}
	public void setStartFormVariables(ArrayList<FieldDTO> startFormVariables) {
		this.startFormVariables = startFormVariables;
	}
	
}

