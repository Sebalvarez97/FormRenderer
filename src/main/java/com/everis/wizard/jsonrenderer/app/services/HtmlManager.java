/**
 * 
 */
package com.everis.wizard.jsonrenderer.app.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author salvarsa
 *
 */
@Service
public class HtmlManager {

	public void writeTemplate(String htmlString, String name) throws IOException {
		writeHtml(htmlString, String.format("src/main/resources/templates/%s.html", name));
	}
	
	public void writeHtml(String htmlString, String path) throws IOException {
		    String absolutpath = new File(path).getAbsolutePath();
		    File newHtmlFile = new File(absolutpath);
		    FileUtils.writeStringToFile(newHtmlFile, htmlString, "UTF-8");
	}
}
