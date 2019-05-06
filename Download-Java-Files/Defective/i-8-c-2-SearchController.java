package com.aws.codestar.projecttemplates.controller;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/")
public class SearchController {

	public SearchController() {
		
	}
	@RequestMapping("search")
	public ModelAndView search() {
		ModelAndView mav = new ModelAndView();
		
		//mav.AddObject("result", field);
		mav.setViewName("result.jsp");
	
		return mav;
	}
}
