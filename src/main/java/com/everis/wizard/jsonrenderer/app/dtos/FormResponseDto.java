package com.everis.wizard.jsonrenderer.app.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;

public class FormResponseDto implements Serializable {

	protected String id;
	protected String name;
	protected String key;
	protected int version;

	protected Map<String,Object> fieldsmap = new HashMap<String, Object>();
	
	protected List<FieldResponseDto> fields = new ArrayList<FieldResponseDto>();

	public FormResponseDto(SimpleFormModel form) {
		this.id = form.getId();
		this.name = form.getName();
		this.key = form.getKey();
		this.version = form.getVersion();
		for (FormField field : form.getFields()) {
			fieldsmap.put(field.getId(), field.getValue());
			fields.add(new FieldResponseDto(field));
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<FieldResponseDto> getFields() {
		return fields;
	}

	public void setFields(List<FieldResponseDto> fields) {
		this.fields = fields;
	}
	
}
