package com.smarvel.springboot.backend.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smarvel.springboot.backend.dto.MarcaConProductosDTO;
import com.smarvel.springboot.backend.entities.Marca;
import com.smarvel.springboot.backend.service.IMarcaService;

@Controller
public class MarcaController {
	
	@Autowired
	IMarcaService marcaService;
	
	@GetMapping("/marcas")
	public String listarMarca(Model model) {
		List<Marca> marcas = marcaService.mostrarMarcas();
	    Map<Long, Boolean> marcasConProductos = new HashMap<>();
	    for (Marca m : marcas) {
	        marcasConProductos.put(m.getId(), marcaService.tieneProductosAsociados(m.getId()));
	    }
	    model.addAttribute("marcas", marcas);
	    model.addAttribute("marcasConProductos", marcasConProductos);
	    return "/marca/listar-marcas";
	}
	
	@GetMapping("/search-marca")
    @ResponseBody
    public List<MarcaConProductosDTO> searchMarca(@RequestParam String nombre) {
		List<Marca> marcas = marcaService.searchMarca(nombre);
	    List<MarcaConProductosDTO> resultado = new ArrayList<>();
	    for (Marca m : marcas) {
	        boolean tieneProductos = marcaService.tieneProductosAsociados(m.getId());
	        resultado.add(new MarcaConProductosDTO(m.getId(), m.getNombre(), tieneProductos));
	    }
	    return resultado;
    }
	
	@GetMapping("/formulario-marca")
	public String formularioMarca(Model model) {
		Marca marca = new Marca();
		model.addAttribute("marca", marca);
		return "/marca/formulario-marca";
	}
	
	@PostMapping("/guardar-marca")
	public String guardarMarca(@ModelAttribute Marca marca, RedirectAttributes redirectAttrs) {
		marcaService.guardarMarca(marca);
		redirectAttrs.addFlashAttribute("success", "Marca guardada correctamente.");
		return "redirect:/marcas";
	}
	
	@GetMapping("/modificar-marca/{id}")
	public String modificarMarca(@PathVariable Long id, Model model) {
		Marca marcaModificada = marcaService.buscarMarcaPorId(id);
		model.addAttribute("marca", marcaModificada);

		return "/marca/formulario-marca";
	}
	
	@GetMapping("/borrar-marca/{id}")
	public String borrarMarca(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		marcaService.borrarMarca(id);
		redirectAttrs.addFlashAttribute("success", "Marca eliminada correctamente.");
		return "redirect:/marcas";
	}

}
