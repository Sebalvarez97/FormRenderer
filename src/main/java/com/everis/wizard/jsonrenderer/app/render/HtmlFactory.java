package com.everis.wizard.jsonrenderer.app.render;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.each;
import static j2html.TagCreator.input;
import static j2html.TagCreator.label;
import static j2html.TagCreator.option;
import static j2html.TagCreator.select;
import static j2html.TagCreator.textarea;
import static j2html.TagCreator.script;
import static j2html.TagCreator.link;

import java.util.List;

import static j2html.TagCreator.h1;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.h4;
import static j2html.TagCreator.h5;
import static j2html.TagCreator.h6;

import com.everis.wizard.jsonrenderer.app.model.ExpressionFormField;
import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.OptionFormField;

import j2html.tags.Tag;

public class HtmlFactory {

	public static Tag scripts(List<String> scripts) {
		return div(
					each(scripts, script ->
						script(script)
							)
				);
	}
	
	public static Tag scriptSrc(List<String> scriptsrc) {
		return div(
					each(scriptsrc, script ->
						script().withSrc(script)
							)
				);
	}
	
	public static Tag stylesheets(List<String> stylesheets) {
		return div(
					each(stylesheets, stylesheetsrc ->
						link().withRel("stylesheet").withSrc(stylesheetsrc)
							)
				);
	}
	
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
				 select(
						 each(field.getOptions(), option ->
						 		option(option.getName())
						 		.withValue(option.getName())
						 		.withName(field.getName())
								 )
						 ).withCondRequired(field.isRequired())
				 		.withValue((String) field.getValue())
				);
	} 
	
	private static Tag inputTitle(FormField field) {
		return div(
				label(field.getName())
				 .attr("for",field.getId())
				 ,br());
	}
	
	private static Tag textArea(FormField field) {
		return div(
				inputTitle(field),
				 textarea()
				 .withCondRequired(field.isRequired())
				);
	}
	//CAMBIAR POR h1 o h2 o h3
	private static Tag expression(ExpressionFormField field) {
		if(field.getParam("size") != null) {
			switch((String) field.getParam("size")) {
			case "1":
				return h6(field.getExpression());
			case "2":
				return h5(field.getExpression());
			case "3":
				return h4(field.getExpression());
			case "4":
				return h3(field.getExpression());
			case "5":
				return h2(field.getExpression());
			case "6":
				return h1(field.getExpression());
			} 
		}
		return div(
				label(field.getExpression())
					.withType(field.getType())
				);
	}
	
	private static Tag radiobutton(OptionFormField field) {
		field.setType("radio");
		return div(
				inputTitle(field),
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
				 				.withValue((String) field.getValue())
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
				 input()
					.withType(field.getType())
   			        .withPlaceholder(field.getPlaceholder())
   			        .withCondTitle(field.getParam("mask") != null, (String) field.getParam("mask"))
   			        .condAttr(field.getParam("regexPattern") != null,
   			        		"pattern", (String) field.getParam("regexPattern")).condAttr(
   		   			        		field.getParam("minLength") != null,
   		   			        		"minlenght", (String) field.getParam("minLength"))
   		   			        .condAttr(
   					        		field.getParam("maxLength") != null,
   					        		"maxlenght", (String) field.getParam("maxLength"))
   			        .withCondRequired(field.isRequired())
   			        .withValue((String) field.getValue())
			);
	}
	
}
