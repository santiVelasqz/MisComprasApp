package com.smarvel.springboot.backend.controllers;

import java.util.Date;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAttributes {
	
	@ModelAttribute("today")
    public Date getTodayDate() {
        return new Date();
    }

}
