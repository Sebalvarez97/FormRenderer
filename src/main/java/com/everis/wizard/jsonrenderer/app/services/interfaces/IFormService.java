package com.everis.wizard.jsonrenderer.app.services.interfaces;

import java.util.List;

import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.services.exceptions.FormServiceException;

public interface IFormService {

	public String getForm(String formId) throws FormServiceException;
	
	public SimpleFormModel getFormModel(String formId) throws FormServiceException;
	
	public List<SimpleFormModel> getAll() throws FormServiceException;
}
