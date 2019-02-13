package com.everis.wizard.jsonrenderer.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.everis.wizard.jsonrenderer.app.dtos.ProcessRequestDto;
import com.everis.wizard.jsonrenderer.app.services.ProcessService;
import com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService;


	@Controller
	@RequestMapping(path = "/process")
	public class ProcessController {
		
		@Autowired
		private ProcessService processService;
		
		@GetMapping(path = "/getall")
		public @ResponseBody ResponseEntity<String> getAll() {		
			
			try {
				String form = processService.GetAll();
				return ResponseEntity
						.ok()
						.body(form);
			}catch(Exception ex) {
				return ResponseEntity.status(204).build();
			}
		}
		
		@GetMapping(path = "/getall/{key}")
		public @ResponseBody ResponseEntity<String> getAll(@PathVariable String key) {
			
			
			try {
				String form = processService.GetAll(key);
				return ResponseEntity
						.ok()
						.body(form);
			}catch(Exception ex) {
				return ResponseEntity.status(204).build();
			}
		}
		
		@GetMapping(path = "/getlatest/{key}")
		public @ResponseBody ResponseEntity<String> getLatest(@PathVariable String key) {
						
			try {
				String form = processService.GetLatest (key);
				return ResponseEntity
						.ok()
						.body(form);
			}catch(Exception ex) {
				return ResponseEntity.status(204).build();
			}
		}
		
		@PostMapping(path = "/create/")
		public @ResponseBody ResponseEntity<String> createProcess(@RequestBody ProcessRequestDto pdto)
		{
			System.out.println("Perro");
			
			try {
				String form = processService.CreateProcess(pdto);
				System.out.println("ok" + form);
				
				return ResponseEntity
						.ok()
						.body(form);
			}catch(Exception ex) {
				System.out.println("not ok");
				return ResponseEntity.status(204).build();
			}
			
		}
		
}
