package com.everis.wizard.jsonrenderer.app.services.interfaces;

import java.util.List;
import java.util.Map;

import com.everis.wizard.jsonrenderer.app.dtos.FormRequestDto;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.services.exceptions.FormServiceException;

public interface IFormService {

	public String getFormById(String formId, Map<String, Object> model) throws FormServiceException;
	
	public String getFormByKey(String formKey, Map<String, Object> model) throws FormServiceException;
	
	public SimpleFormModel getFormModel(String formId) throws FormServiceException;
	
	public List<SimpleFormModel> getAll() throws FormServiceException;
	
	public String saveForm(FormRequestDto dto) throws FormServiceException;
}
