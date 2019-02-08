package com.everis.wizard.jsonrenderer.app.render;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.each;
import static j2html.TagCreator.input;
import static j2html.TagCreator.label;
import static j2html.TagCreator.option;
import static j2html.TagCreator.select;
import static j2html.TagCreator.textarea;

import com.everis.wizard.jsonrenderer.app.model.ExpressionFormField;
import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.OptionFormField;

import j2html.tags.Tag;

public class HtmlFactory {

	public static Tag formfield(FormField field) {
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
			return inputt(field);
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
						 ).withCondRequired(field.isRequired())
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
				 .withCondRequired(field.isRequired())
				);
	}
	//CAMBIAR POR h1 o h2 o h3
	private static Tag expression(ExpressionFormField field) {
		return div(
				label(field.getExpression())
					.withType(field.getType())
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
				 				.withCondRequired(field.isRequired())
						 )
				);
	}
	
	private static Tag checkbox(FormField field) {
		field.setType("checkbox");
		return inputt(field);
	}
	private static Tag number(FormField field) {
		field.setType("number");
		return inputt(field);
	}
	
	private static Tag inputt(FormField field) {
		return div(
				inputTitle(field),
				 br(),
				 input()
					.withType(field.getType())
   			        .withPlaceholder(field.getPlaceholder())
   			        .condAttr(
   			        		field.getParam("minLength") != null,
   			        		"minlenght", (String) field.getParam("minLength"))
   			        .condAttr(
			        		field.getParam("maxLength") != null,
			        		"maxlenght", (String) field.getParam("maxLength"))
   			        .withCondRequired(field.isRequired())
			);
	}
	
}
