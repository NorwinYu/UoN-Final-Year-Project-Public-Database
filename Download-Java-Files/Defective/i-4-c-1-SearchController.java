package com.aws.codestar.projecttemplates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Controller
@RequestMapping("/")
public class SearchController {
	private final String url = "https://www.theaudiodb.com/api/v1/json/1/search.php?s=";
	//didnt need a key, used "1" 
	public SearchController() {
		
	}
	@RequestMapping("search")
	public ModelAndView search(@RequestParam("field1") String field) {
		ModelAndView mav = new ModelAndView("result");
		
		String finalurl = url + field; //field has the artist name
		String artist, label, year, genre;
		artist = label = year = genre = "N/A"; //incase the DB has no info
		
		try {
			//Open connection and make a buffer stream
			URL obj = new URL(finalurl);
			URLConnection con = obj.openConnection();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String inputLine;
            while ((inputLine = buffer.readLine()) != null) {              
            	String[] keys = inputLine.split(",");
            	if (keys.length < 11) break;
            	
            	//poorly parsing info from the json...
	           	artist = keys[1].split(":")[1];
	           	label = keys[4].split(":")[1];
	           	year = keys[7].split(":")[1];
	            genre = keys[11].split(":")[1];
            	break;
            }
            buffer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
		} 
		
		mav.addObject("artist", artist);
		mav.addObject("label", label);
		mav.addObject("year", year);
		mav.addObject("genre", genre);

		return mav;
	}
}
