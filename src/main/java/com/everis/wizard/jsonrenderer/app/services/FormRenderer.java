package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;


import static  com.everis.wizard.jsonrenderer.app.render.HtmlFactory.*;
import static j2html.TagCreator.*;

import java.util.Map;

@Service
public class FormRenderer {

	/*
	 Gets a SimpleFormModel and a Map of Attributes
	 Attributes:
	 	pageTitle: title of the page (String)
	*/
	public String getHtmlForm(SimpleFormModel formModel, Map<String, Object> pageAttr) {
		return document(
	            html(
	                head(
	                    title((String) pageAttr.get("pageTitle"))
	                ),
	                body(
	                    header(
	                    	h1(formModel.getName())
	                    ),
	                    main(
	                        //the view from the partials example
	                		div(
	                			form()
	                				.with(
	                					each(formModel.getFields(), field ->
            									formfield(field)
                							),
	                					input()
	                						  .withType("submit")
	                						  .withValue("Enter")
	                					)
		                			.withId(formModel.getId())
            			    )
	                    ),
	                    footer(
	                        
	                    )
	                )
	            )
	        );
		}
		
}
