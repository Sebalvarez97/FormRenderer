package com.everis.wizard.jsonrenderer.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.services.exceptions.FormServiceException;
import com.everis.wizard.jsonrenderer.app.services.interfaces.IFormService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonRendererApplicationTests {

	@Autowired
	private IFormService formService;

	private static String FORM_ID = null;
	
	@Before
	public void before() throws FormServiceException {
		FORM_ID = getRandomFormId();
		System.out.println("-----------------TESTED WITH:---------------");
		System.out.println("---------FORM ID " + FORM_ID);
	}

	@Test
	public void testGetFormModel() throws Exception {
		SimpleFormModel formModel = formService.getFormModel(FORM_ID);
		assertNotNull(formModel);
		assertNotNull(formModel.getFields());
		assertEquals(FORM_ID, formModel.getId());
		//printForm(formModel);
	}
	
	@Test
	public void testGetAllForms() throws Exception{
		List<SimpleFormModel> models = formService.getAll();
		assertNotNull(models);
		int number = getRnumber(models.size());
		assertNotNull(models.get(number));
		assertNotNull(models.get(number).getId());
	}

	@Test
	public void testGetFormByKey() throws Exception {
		SimpleFormModel formModel = formService.getFormModel(FORM_ID);
		System.out.println("---------FORM KEY " + formModel.getKey());
		String HtmlModel = formService.getFormByKey(formModel.getKey());
		assertNotNull(HtmlModel);
	}
	
	@Test
	public void testGetForm() throws Exception {
		//System.out.println("--------------------HTML-------------------");
		//System.out.println("-------------------------------------------");
		String HtmlModel = formService.getFormById(FORM_ID);
		assertNotNull(HtmlModel);
		//System.out.println(HtmlModel);
		//System.out.println("--------------------------------------------");
		//System.out.println("--------------------------------------------");
	}

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
			if(field.getParam("regexPattern") != null) {
				System.out.println("-------PATTERN : " + field.getParam("regexPattern"));
			}
			if(field.getPlaceholder() != null) {
				System.out.println("-------PLACEHOLDER : " + field.getPlaceholder());
			}
			if(field.getLayout() != null) {
				System.out.println("-------LAYOUT : " + field.getLayout().toString());
			}
			if(field.getValue() != null) {
				System.out.println("-------VALUE : " + field.getValue());
			}
			System.out.println("--------------------------------------------");
		}
		System.out.println("--------------------------------------------");
		System.out.println("--------------------------------------------");
	}
	
	protected int getRnumber(int max) {
		Random r = new Random();
		int rnum = r.nextInt(max);
		return rnum;
	}
	
	protected String getRandomFormId() throws FormServiceException {
		List<SimpleFormModel> models = formService.getAll();
		assertNotNull(models);
		assertTrue(!models.isEmpty());
		return models.get(getRnumber(models.size())).getId();
	}
}
