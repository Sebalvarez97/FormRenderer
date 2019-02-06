package com.everis.wizard.jsonrenderer.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.flowable.form.api.FormRepositoryService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.everis.wizard.jsonrenderer.app.converter.FormJsonConverter;
import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.services.FormService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonRendererApplicationTests {

	@Autowired
	private FormService formService;

	private static final String FORM_ID = "c63ce050-24a6-11e9-8a68-e4b97a06e9ac";


	protected void printForm(SimpleFormModel model) {
		System.out.println("--------------------------------------------");
		System.out.println("--------------------------------------------");
		System.out.println("-----------------FORM PRINTER---------------");
		System.out.println("-------NAME : " + model.getName());
		System.out.println("-------DESCRIPTION : " + model.getDescription());
		System.out.println("-------ID : " + model.getId());
		System.out.println("-------KEY : " + model.getKey());
		System.out.println("-----------------FIELDS---------------------");
		for (FormField field : model.getFields()) {
			System.out.println("--------------------------------------------");
			System.out.println("-------NAME : " + field.getName());
			System.out.println("-------TYPE : " + field.getType());
			System.out.println("-------ID : " + field.getId());
			System.out.println("--------------------------------------------");
		}
		System.out.println("--------------------------------------------");
		System.out.println("--------------------------------------------");
	}

	@Test
	public void testGetFormModel() throws Exception {
		SimpleFormModel formModel = formService.getFormModel(FORM_ID);
		assertNotNull(formModel);
		assertNotNull(formModel.getFields());
		assertEquals(FORM_ID, formModel.getId());
		printForm(formModel);
	}

	@Test
	public void testGetForm() throws Exception {
		System.out.println("-----------------FORM TEST 3---------------");
		SimpleFormModel formModel = formService.getFormModel(FORM_ID);
		assertNotNull(formModel);
		System.out.println(formModel);
		System.out.println("--------------------------------------------");
		System.out.println("--------------------------------------------");
	}
}
