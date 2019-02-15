package com.everis.wizard.jsonrenderer.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.services.exceptions.FormServiceException;
import com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService;

@Controller
@RequestMapping(path = "/form-renderer")
public class FormController {
	
	@Autowired
	private IFormService formService;
	
	@GetMapping(path = "/form/{formId}")
	public @ResponseBody ResponseEntity<String> getForm(@PathVariable String formId, Map<String, Object> model) {
		try {
			String htmlform = formService.getFormById(formId, model);
			return ResponseEntity
					.ok()
					.body(htmlform);
		}catch(Exception ex) {
			return ResponseEntity.status(204).build();
		}
	}
	
	@GetMapping(path = "/form/key/{formKey}")
	public @ResponseBody ResponseEntity<String> getFormByKey(@PathVariable String formKey, Map<String, Object> model) {
		try {
			String htmlform = formService.getFormByKey(formKey, model);
			return ResponseEntity
					.ok()
					.body(htmlform);
		}catch(Exception ex) {
			return ResponseEntity.status(204).build();
		}
	}
	
	@GetMapping(path = "/formwea/{formId}")
	public String getTemplateById(@PathVariable String formId, Map<String, Object> model) {
		try {
			return formService.writeHtmlFormById("newForm", formId, model);
		} catch (FormServiceException e) {
			e.printStackTrace();
			return "formError";
		}
	}
	
	@GetMapping(path = "/formwea/key/{formKey}")
	public String getTemplateByKey(@PathVariable String formKey, Map<String, Object> model) {
		try {
			return formService.writeHtmlFormByKey("keyform", formKey, model);
		} catch (FormServiceException e) {
			e.printStackTrace();
			return "formError";
		}
	}
	
	@PostMapping(path = "/form/save")
	public String saveForm(SimpleFormModel form) {
		System.out.println("SAVING");
		for (FormField field : form.getFields()) {
			System.out.println(field.getId());
			System.out.println(field.getName());
			System.out.println(field.getValue());
		}
		return "formError";
	}
}