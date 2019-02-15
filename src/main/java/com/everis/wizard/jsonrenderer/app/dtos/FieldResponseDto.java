package com.everis.wizard.jsonrenderer.app.dtos;

import java.io.Serializable;
import java.util.Map;

import com.everis.wizard.jsonrenderer.app.model.FormField;

public class FieldResponseDto implements Serializable{

	protected String id;
    protected String name;
    protected String type;
    protected Object value;
    protected boolean required;
	protected boolean readOnly;
    protected String placeholder;
    protected String expression;
    protected Map<String, Object> params;
    
    public FieldResponseDto(FormField field) {
		super();
		this.id = field.getId();
		this.name = field.getName();
		this.type = field.getType();
		this.value = field.getValue();
		this.required = field.isRequired();
		this.readOnly = field.isReadOnly();
		this.placeholder = field.getPlaceholder();
		this.expression = field.getExpression();
		this.params = field.getParams();
	}


	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
