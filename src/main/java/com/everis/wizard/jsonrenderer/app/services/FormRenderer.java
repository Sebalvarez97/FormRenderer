package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import static j2html.TagCreator.*;

@Service
public class FormRenderer {

	private String pageTitle = "testPage";

	public String getHtmlForm(SimpleFormModel formModel) {
		return document(
	            html(
	                head(
	                    title(pageTitle)
	                ),
	                body(
	                    header(
	                        h1(formModel.getName())
	                    ),
	                    main(
	                        //the view from the partials example
	                		 form().withMethod("post").with(
            				 	each(formModel.getFields(), field ->
                				 input()
                			        .withType(field.getType())
                			        .withId(field.getId())
                			        .withName(field.getName())
                			        .withPlaceholder(field.getPlaceholder())
                			        .withCondRequired(field.isRequired())
            					)
            			    )
	                    ),
	                    footer(
	                        
	                    )
	                )
	            )
	        );
		}
}
