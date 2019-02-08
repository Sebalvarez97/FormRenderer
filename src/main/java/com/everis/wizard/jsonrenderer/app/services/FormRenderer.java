package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.model.ExpressionFormField;
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
			return expression((ExpressionFormField) field);
		case "dropdown":
			return dropdown((OptionFormField) field);
		case "boolean" :
			return checkbox(field);
		case "radio-buttons" :
			return radiobutton((OptionFormField) field);
		case "integer" :
			return number(field);
		case "decimal" :
			return number(field);
		case "multi-line-text" :
			return textArea(field);
		default :
			return commoninput(field);
		}
	}
	
	private static Tag dropdown(OptionFormField field) {
		return div(
				inputTitle(field),
				 br(),
				 select(
						 each(field.getOptions(), option ->
						 		option(option.getName())
						 		.withValue(option.getName())
						 		.withName(field.getName())
								 )
						 ).withId(field.getId())
			 			  .withCondRequired(field.isRequired())
				);
	} 
	
	private static Tag inputTitle(FormField field) {
		return label(field.getName())
				 .attr("for",field.getId());
	}
	
	private static Tag textArea(FormField field) {
		return div(
				inputTitle(field),
				 br(),
				 textarea()
				);
	}
	
	private static Tag expression(ExpressionFormField field) {
		return div(
				label(field.getExpression())
					.withType(field.getType())
			        .withId(field.getId())
			        .withCondRequired(field.isRequired())
				);
	}
	
	private static Tag radiobutton(OptionFormField field) {
		field.setType("radio");
		return div(
				inputTitle(field),
				 br(),
					 each(field.getOptions(), option ->
				 		div(
				 			input()
						 		.withType(field.getType())
						 		.withValue(option.getName())
						 		.withName(field.getName())
						 		,label(option.getName())
						 		,br()
						 		)
						 )
				).withId(field.getId())
	 			  .withCondRequired(field.isRequired());
	}
	
	private static Tag checkbox(FormField field) {
		field.setType("checkbox");
		return commoninput(field);
	}
	private static Tag number(FormField field) {
		field.setType("number");
		return commoninput(field);
	}
	
	private static Tag commoninput(FormField field) {
		return div(
				inputTitle(field),
				 br(),
				 input()
					.withType(field.getType())
					.withId(field.getId())
   			        .withPlaceholder(field.getPlaceholder())
   			        .withCondRequired(field.isRequired())
			);
	}
}
