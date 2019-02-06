package com.everis.wizard.jsonrenderer.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.everis.wizard.jsonrenderer.app.services.FormService;

@Controller
@RequestMapping(path = "/form-renderer")
public class FormController {
	
	@Autowired
	private FormService formService;
	
	@GetMapping(path = "/form/{formId}")
	public @ResponseBody ResponseEntity<String> getForm(@PathVariable String formId) {
		try {
			String form = formService.getForm(formId);
			return ResponseEntity
					.ok()
					.body(form);
		}catch(Exception ex) {
			return ResponseEntity.status(204).build();
		}
	}
}
