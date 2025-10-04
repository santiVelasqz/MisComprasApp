package com.smarvel.springboot.backend.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smarvel.springboot.backend.dto.CategoriaConProductoDTO;
import com.smarvel.springboot.backend.dto.MarcaConProductosDTO;
import com.smarvel.springboot.backend.entities.Categoria;
import com.smarvel.springboot.backend.entities.Marca;
import com.smarvel.springboot.backend.service.ICategoriaService;

@Controller
public class CategoriaController {
	
	@Autowired
	ICategoriaService categoriaService;
	
	@GetMapping("/categorias")
	public String listarCategoria(Model model) {
		List<Categoria> categorias = categoriaService.mostrarCategorias();
		 Map<Long, Boolean> categoriaConProductos = new HashMap<>();
		    for (Categoria m : categorias) {
		    	categoriaConProductos.put(m.getId(), categoriaService.tieneProductosAsociados(m.getId()));
		    }
		model.addAttribute("categorias", categorias);
		model.addAttribute("categoriaConProductos", categoriaConProductos);
		return "/categoria/listar-categorias";
	}
	
//	@GetMapping("/search-categoria")
//	@ResponseBody
//    public List<Categoria> searchCategoria(@RequestParam String nombre) {
//        return categoriaService.searchCategoria(nombre);
//    }
	@GetMapping("/search-categoria")
	@ResponseBody
    public List<CategoriaConProductoDTO> searchCategoria(@RequestParam String nombre) {
		List<Categoria> categoria = categoriaService.searchCategoria(nombre);
	    List<CategoriaConProductoDTO> resultado = new ArrayList<>();
	    for (Categoria m : categoria) {
	        boolean tieneProductos = categoriaService.tieneProductosAsociados(m.getId());
	        resultado.add(new CategoriaConProductoDTO(m.getId(), m.getNombre(), tieneProductos));
	    }
	    return resultado;
	}
	
	@GetMapping("/formulario-categoria")
	public String formularioCategoria(Model model) {
		
		Categoria categoria = new Categoria();
		model.addAttribute("categoria", categoria);
		
		return "categoria/formulario-categoria";
	}
	
	@PostMapping("/guardar-categoria")
	public String guardarCategoria(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttrs) {
		
		categoriaService.guardarCategoria(categoria);
		redirectAttrs.addFlashAttribute("success", "Categoría guardada correctamente.");
		return "redirect:/categorias";
	}
	
	@GetMapping("/modificar-categoria/{id}")
	public String modificarCategoria(@PathVariable Long id, Model model) {
		
		Categoria categoriaModificada = categoriaService.buscarCategoriaPorId(id);
		model.addAttribute("categoria", categoriaModificada);
		
		return "categoria/formulario-categoria";
	}
	
//	@GetMapping("/borrar-categoria/{id}")
//	public String borrarCategoria(@PathVariable Long id, RedirectAttributes redirectAttrs) {
//		categoriaService.borrarCategoria(id);
//		redirectAttrs.addFlashAttribute("success", "Categoría eliminada correctamente.");
//		return "redirect:/categorias";
//	}
	@GetMapping("/borrar-categoria/{id}")
	public String borrarCategoria(@PathVariable Long id, RedirectAttributes redirectAttrs) {
	    
	        categoriaService.borrarCategoria(id);
	        redirectAttrs.addFlashAttribute("success", "Categoría eliminada correctamente.");
	    
	    return "redirect:/categorias";
	}

}
