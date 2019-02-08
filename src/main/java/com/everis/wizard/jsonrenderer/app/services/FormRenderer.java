package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import com.everis.wizard.jsonrenderer.app.model.ExpressionFormField;
import com.everis.wizard.jsonrenderer.app.model.FormField;
import com.everis.wizard.jsonrenderer.app.model.FormFieldTypes;
import com.everis.wizard.jsonrenderer.app.model.OptionFormField;

import static  com.everis.wizard.jsonrenderer.app.render.HtmlFactory.*;
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
            									formfield(field)
            									.withClass("form-group")
                							),
	                					button("Submit").withType("submit")
	                					)
		                			.withClass("form-horizontal")
		                			.withRole("form")
		                			.withMethod("post")
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
		
}
