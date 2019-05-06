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
	
	public SearchController() {
		
	}
	@RequestMapping("search")
	public ModelAndView search(@RequestParam("field1") String field) {
		ModelAndView mav = new ModelAndView("result");
		
		String finalurl = url + field;
		String bio = "";
		
		try {
			URL obj = new URL(finalurl);
			URLConnection con = obj.openConnection();
			BufferedReader buf = new BufferedReader(new InputStreamReader(con.getInputStream()));
					
			String inputLine;
            while ((inputLine = buf.readLine()) != null) {              
            	String[] keys = inputLine.split(",");
            	String[] pairs = ;
            	int i = 0;
            	
            	for (String str : keys) {
            		String[] pairs = str.split(":");
            		if (pairs[0] == "strBiographyEN") {
            			bio = pairs[1];
            			break;
            		}
            		i++;
            	}
            	if (bio != "") break;
            	/*JSONArray a = (JSONArray) parser.parse(inputLine);
               
                for (Object o : a) {
                    JSONObject artist = (JSONObject) o;

                    String info = (String) artist.get("strBiographyEN");
                    bio = info;

                }*/
            }
            buf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
		
		mav.addObject("result", bio);

		return mav;
	}
}
