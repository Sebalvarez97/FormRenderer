package com.everis.wizard.jsonrenderer.app.dtos;

import java.io.Serializable;

public class FieldDTO  {
	public String name;
	public String type;
	public String value;

	public FieldDTO(){
		
	}
	
	public FieldDTO(String name, String type, String value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
