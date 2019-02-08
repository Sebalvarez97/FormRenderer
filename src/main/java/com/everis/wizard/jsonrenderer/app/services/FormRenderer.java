package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.FormFieldTypes;
import com.everis.wizard.jsonrenderer.app.model.OptionFormField;

import j2html.TagCreator;
import j2html.tags.Tag;

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
	                        h1(formModel.getName())
	                        .withClass("display-2")
	                    ),
	                    main(
	                        //the view from the partials example
	                		div(
	                			form()
	                				.with(
	                					each(formModel.getFields(), field ->
            									HtmlFactory(field)
            									.withClass("form-group")
                							)
	                					)
		                			.withClass("form-horizontal")
		                			.withRole("form")
		                			.withMethod("post")
		                			.withAction("")
		                			.withId(formModel.getId())
            			    )
	                		.withClass("jumbotron")
	                    ),
	                    footer(
	                        
	                    )
	                )
	            )
	        );
		}
	
	protected static Tag HtmlFactory(FormField formfield, String test) {
		if(test.equals("prueba")) {
			System.out.println("-----------------FORMFIELD---------------");
			System.out.println(formfield.getType());
			System.out.println("-----------------------------------------");
		}
		return label();
	}
	
	protected static Tag HtmlFactory(FormField field) {
		switch(field.getType()) {
		case "expression":
			return div(
					label(field.getExpression())
						.withType(field.getType())
    			        .withId(field.getId())
    			        .withCondRequired(field.isRequired())
					);
		case "dropdown":
			OptionFormField optionfield = (OptionFormField) field;
			return div(
					label(field.getName())
					 .attr("for",field.getId()),
					 br(),
					 select(
							 each(optionfield.getOptions(), option ->
							 		option(option.getName())
							 		.withValue(option.getName())
									 )
							 ).withId(field.getId())
				 			  .withCondRequired(field.isRequired())
					);
		case "boolean" :
			field.setType("checkbox");
			return HtmlFactory(field);
		case "integer" :
			field.setType("number");
			return HtmlFactory(field);
		case "decimal" :
			field.setType("number");
			return HtmlFactory(field);
		case "amount" :
			field.setType("number");
			return HtmlFactory(field);
		default :
			return div(
						label(field.getName())
						 .attr("for",field.getId()),
						 br(),
						 input()
							.withType(field.getType())
							.withId(field.getId())
		   			        .withPlaceholder(field.getPlaceholder())
		   			        .withCondRequired(field.isRequired())
					);
		}
	}
}
