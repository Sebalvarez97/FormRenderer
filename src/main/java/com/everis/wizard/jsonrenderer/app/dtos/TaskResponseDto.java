package com.everis.wizard.jsonrenderer.app.dtos;

import java.util.ArrayList;

public class TaskResponseDto {
	public String action;

	public ArrayList<FieldDto> variables;

	public TaskResponseDto() {

	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public ArrayList<FieldDto> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<FieldDto> variables) {
		this.variables = variables;
	}

	public void addVariable(FieldDto field) {
		variables.add(field);
	}

}
