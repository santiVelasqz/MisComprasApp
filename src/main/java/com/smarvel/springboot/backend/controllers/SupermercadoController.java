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

import com.smarvel.springboot.backend.dto.SupermercadoConBorradoDTO;
import com.smarvel.springboot.backend.dto.SupermercadoConComprasDTO;
import com.smarvel.springboot.backend.entities.Supermercado;
import com.smarvel.springboot.backend.service.ICompraService;
import com.smarvel.springboot.backend.service.IProductoService;
import com.smarvel.springboot.backend.service.ISupermercadoService;


@Controller
public class SupermercadoController {
	
	@Autowired
	private ISupermercadoService supermercadoService;
	@Autowired
	private ICompraService compraService;
	@Autowired
	private IProductoService productoService;
	
//	@GetMapping("/supermercados")
//	public String mostrarSupermercados(Model model) {
//		List<Supermercado> supermercados = supermercadoService.mostrarSupermercados();
//		model.addAttribute("supermercados", supermercados);
//		return "/supermercado/listar-supermercados";
//	}
	
	@GetMapping("/supermercados")
	public String mostrarSupermercados(Model model) {
		List<Supermercado> supermercados = supermercadoService.mostrarSupermercados();
	    Map<Long, Boolean> supermercadosNoBorrables = new HashMap<>();

	    for (Supermercado s : supermercados) {
	        boolean tieneCompras = compraService.tieneComprasAsociadasSupermercado(s.getId());
	        boolean tieneProductos = productoService.existeProductoEnSupermercado(s.getId());
	        supermercadosNoBorrables.put(s.getId(), tieneCompras || tieneProductos);
	    }

	    model.addAttribute("supermercados", supermercados);
	    model.addAttribute("supermercadosNoBorrables", supermercadosNoBorrables);
	    return "/supermercado/listar-supermercados";
	}
	
	@GetMapping("search-supermercado")
	@ResponseBody
	public List<SupermercadoConBorradoDTO> searchSupermercado(@RequestParam String nombre) {
		List<Supermercado> supermercados = supermercadoService.searchSupermercado(nombre);
	    List<SupermercadoConBorradoDTO> resultado = new ArrayList<>();

	    for (Supermercado s : supermercados) {
	        boolean tieneCompras = compraService.tieneComprasAsociadasSupermercado(s.getId());
	        boolean tieneProductos = productoService.existeProductoEnSupermercado(s.getId());
	        resultado.add(new SupermercadoConBorradoDTO(
	            s.getId(), s.getNombre(), s.getCiudad(), tieneCompras || tieneProductos));
	    }

	    return resultado;
	}

	
	@GetMapping("/formulario-supermercado")
	public String formularioSupermercado(Model model) {
		Supermercado supermercado = new Supermercado();
		model.addAttribute("supermercado", supermercado);
		return "/supermercado/formulario-supermercado";
	}
	
	@PostMapping("/guardar-supermercado")
	public String guardarSupermercado(@ModelAttribute Supermercado supermercado, RedirectAttributes redirectAttrs) {
		supermercadoService.guardarSupermercado(supermercado);
		redirectAttrs.addFlashAttribute("success", "Supermercado guardado correctamente.");
		return "redirect:/supermercados";
	}
	
	@GetMapping("/modificar-supermercado/{id}")
	public String modificarSupermercado(@PathVariable Long id, Model model) {
		Supermercado supermercadoModificado = supermercadoService.buscarSupermercadoPorId(id);
		model.addAttribute("supermercado", supermercadoModificado);
		return "/supermercado/formulario-supermercado";
	}
	
	@GetMapping("/borrar-supermercado/{id}")
	public String borrarSupermercado(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		supermercadoService.borrarSupermercado(id);
		redirectAttrs.addFlashAttribute("success", "Supermercado eliminado correctamente.");
		return "redirect:/supermercados";
	}
	


}
