package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import com.everis.wizard.jsonrenderer.app.dtos.FormRequestDto;
import com.everis.wizard.jsonrenderer.app.model.SimpleFormModel;
import static  com.everis.wizard.jsonrenderer.app.render.HtmlFactory.*;
import static j2html.TagCreator.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FormRenderer {

	/*
	 Gets a SimpleFormModel and a Map of Attributes
	 Attributes:
	 	obligatory:
	 		pageTitle: title of the page (String)
	 	optional:
	 		stylesheets: List of style sheets sources to add (String)
	 		scripts: List of scripts to use as String (List<String>)
	 		scriptSrc: List of script sources to add (List<String>)
	 		
	 		submitbuttonClass: class to set in the submit button (String)
	 		submitbuttonAction: action to set in the submit button (String)
	 		submitbuttonMethod: method to set in the submit button (String)
	 		
	 		formClass: class to set in the submit button (String)
	 		formAction: action to set in the form (String)
	 		formMethod: method to set in the form (String)
	 		formOnsubmit: onsubmit param to set in the form (String)
	 		
	 		formFieldClass: class to set in the formfield (String)
	*/
	@SuppressWarnings("unchecked")
	public String getHtmlForm(String pageTitle, SimpleFormModel formModel, Map<String, Object> pageAttr) {
		if(pageAttr.get("scripts") == null) {
			pageAttr.put("scripts", new ArrayList<String>());
		}
		if(pageAttr.get("scriptSrc") == null) {
			pageAttr.put("scriptSrc", new ArrayList<String>());
		}
		if(pageAttr.get("stylesheets") == null) {
			pageAttr.put("stylesheets", new ArrayList<String>());
		}		
		return document(
	            html(
	                head(
	                    title(pageTitle),
                		scripts((List<String>)pageAttr.get("scripts")),
                    	scriptSrc((List<String>)pageAttr.get("scriptSrc")),
                    	stylesheets((List<String>)pageAttr.get("stylesheets"))
	                ),
	                body(
	                    header(
	                    	h2(formModel.getName())
	                    ),
	                   
	                        //the view from the partials example
	                		div(
	                			form()
	                				.with(
	                					each(formModel.getFields(), field ->
            									formfield(field)
            									.withCondClass(pageAttr.get("formFieldClass") != null, (String) pageAttr.get("formFieldClass"))
                							),
	                					br(),
	                					input()
	                						  .withType("submit")
	                						  .withValue("Enter")
	                						  .withCondAction(pageAttr.get("submitbuttonAction") != null,
	                								  (String) pageAttr.get("submitbuttonAction"))
	                						  .withCondClass(pageAttr.get("submitbuttonClass") != null,
	                								  (String) pageAttr.get("submitbuttonClass"))
	                						  .withCondMethod(pageAttr.get("submitbuttonMethod") != null,
	                								  (String) pageAttr.get("submitbuttonMethod"))
	                					)
		                			.withId(formModel.getId())
		                			.withCondAction(pageAttr.get("formAction") != null, (String) pageAttr.get("formAction"))
		                			.withCondClass(pageAttr.get("formClass") != null,
  								  			(String) pageAttr.get("formClass"))
		                			.withCondMethod(pageAttr.get("formMethod") != null,
  								  			(String) pageAttr.get("formMethod"))
		                			.condAttr(pageAttr.get("formOnsubmit") != null, "onsubmit", (String) pageAttr.get("formOnsubmit"))
            			    )
	                    ,
	                    footer(
	                        
	                    )
	                )
	            )
	        );
		}
	
	public String getHtmlForm(String pageTitle, FormRequestDto formModel, Map<String, Object> pageAttr) {
		if(pageAttr.get("scripts") == null) {
			pageAttr.put("scripts", new ArrayList<String>());
		}
		if(pageAttr.get("scriptSrc") == null) {
			pageAttr.put("scriptSrc", new ArrayList<String>());
		}
		if(pageAttr.get("stylesheets") == null) {
			pageAttr.put("stylesheets", new ArrayList<String>());
		}		
		return document(
	            html(
	                head(
	                    title(pageTitle),
                		scripts((List<String>)pageAttr.get("scripts")),
                    	scriptSrc((List<String>)pageAttr.get("scriptSrc")),
                    	stylesheets((List<String>)pageAttr.get("stylesheets"))
	                ),
	                body(
	                    header(
	                    	h2(formModel.getName())
	                    ),
	                   
	                        //the view from the partials example
	                		div(
	                			form()
	                				.with(
	                					each(formModel.getFields(), field ->
            									formfield(field)
            									.withCondClass(pageAttr.get("formFieldClass") != null, (String) pageAttr.get("formFieldClass"))
                							),
	                					input()
	                						  .withType("submit")
	                						  .withValue("Enter")
	                						  .withCondAction(pageAttr.get("submitbuttonAction") != null,
	                								  (String) pageAttr.get("submitbuttonAction"))
	                						  .withCondClass(pageAttr.get("submitbuttonClass") != null,
	                								  (String) pageAttr.get("submitbuttonClass"))
	                						  .withCondMethod(pageAttr.get("submitbuttonMethod") != null,
	                								  (String) pageAttr.get("submitbuttonMethod"))
	                					)
		                			.withId(formModel.getId())
		                			.withCondAction(pageAttr.get("formAction") != null, (String) pageAttr.get("formAction"))
		                			.withCondClass(pageAttr.get("formClass") != null,
  								  			(String) pageAttr.get("formClass"))
		                			.withCondMethod(pageAttr.get("formMethod") != null,
  								  			(String) pageAttr.get("formMethod"))
		                			.condAttr(pageAttr.get("formOnsubmit") != null, "onsubmit", (String) pageAttr.get("formOnsubmit"))
            			    )
	                    ,
	                    footer(
	                        
	                    )
	                )
	            )
	        );
		}
		
}
