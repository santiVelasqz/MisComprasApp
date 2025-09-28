package com.smarvel.springboot.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smarvel.springboot.backend.entities.Supermercado;
import com.smarvel.springboot.backend.service.ISupermercadoService;

@Controller
public class InicioController {
	
	@Autowired
	ISupermercadoService supermercadoService;
	
	@GetMapping("/")
	public String inicio(Model model) {
		 List<Supermercado> supermercados = supermercadoService.mostrarSupermercados();
		    model.addAttribute("supermercados", supermercados);
		return "inicio";
	}

}
