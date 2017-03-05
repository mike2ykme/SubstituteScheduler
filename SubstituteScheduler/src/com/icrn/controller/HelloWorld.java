package com.icrn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	
	@RequestMapping("/welcome")
	public String helloWorld(Model model){
		return "welcome";
	}
}
