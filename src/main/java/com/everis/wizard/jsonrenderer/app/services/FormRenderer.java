package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;

import j2html.TagCreator;

import static j2html.TagCreator.*;

@Service
public class FormRenderer {

	private String pageTitle = "TestPage";

	public String getHtmlForm(SimpleFormModel formModel) {
		return document(
	            html(
	                head(
	                    title(pageTitle),
	                    link().withRel("stylesheet")
	                    .withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css")
	                ),
	                body(
	                    header(
	                        h1(formModel.getName()).withClass("display-2")
	                    ),
	                    main(
	                        //the view from the partials example
	                		div(
	                				form().withMethod("post").with(
	            				 	each(formModel.getFields(), field ->
	                				 div(
		        						 input()
		                			        .withType(field.getType())
		                			        .withId(field.getId())
		                			        .withName(field.getName())
		                			        .withPlaceholder(field.getPlaceholder())
		                			        .withCondRequired(field.isRequired())
	                						 )
	                				 )
	            				 	)
	                				.withClass("form-horizontal")
            			    )
	                		.withClass("form-group")
	                    ),
	                    footer(
	                        
	                    )
	                )
	            )
	        );
		}
}
