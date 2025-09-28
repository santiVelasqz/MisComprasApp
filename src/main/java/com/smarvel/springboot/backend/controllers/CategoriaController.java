package com.smarvel.springboot.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smarvel.springboot.backend.entities.Categoria;
import com.smarvel.springboot.backend.service.ICategoriaService;

@Controller
public class CategoriaController {
	
	@Autowired
	ICategoriaService categoriaService;
	
	@GetMapping("/categorias")
	public String listarCategoria(Model model) {
		List<Categoria> categorias = categoriaService.mostrarCategorias();
		model.addAttribute("categorias", categorias);
		return "categoria/listar-categorias";
	}
	
	@GetMapping("/search-categoria")
	@ResponseBody
    public List<Categoria> searchCategoria(@RequestParam String nombre) {
        return categoriaService.searchCategoria(nombre);
    }
	
	@GetMapping("/formulario-categoria")
	public String formularioCategoria(Model model) {
		
		Categoria categoria = new Categoria();
		model.addAttribute("categoria", categoria);
		
		return "categoria/formulario-categoria";
	}
	
	@PostMapping("/guardar-categoria")
	public String guardarCategoria(@ModelAttribute Categoria categoria) {
		
		categoriaService.guardarCategoria(categoria);
		
		return "redirect:/categorias";
	}
	
	@GetMapping("/modificar-categoria/{id}")
	public String modificarCategoria(@PathVariable Long id, Model model) {
		
		Categoria categoriaModificada = categoriaService.buscarCategoriaPorId(id);
		model.addAttribute("categoria", categoriaModificada);
		
		return "categoria/formulario-categoria";
	}
	
	@GetMapping("/borrar-categoria/{id}")
	public String borrarCategoria(@PathVariable Long id) {
		categoriaService.borrarCategoria(id);
		return "redirect:/categorias";
	}

}
