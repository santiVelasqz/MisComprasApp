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

import com.smarvel.springboot.backend.dto.ProductoConComprasDTO;
import com.smarvel.springboot.backend.entities.Categoria;
import com.smarvel.springboot.backend.entities.Marca;
import com.smarvel.springboot.backend.entities.Producto;
import com.smarvel.springboot.backend.service.ICategoriaService;
import com.smarvel.springboot.backend.service.ICompraService;
import com.smarvel.springboot.backend.service.IMarcaService;
import com.smarvel.springboot.backend.service.IProductoService;

@Controller
public class ProductoController {
	
	@Autowired
	IProductoService productoService;
	
	@Autowired
	ICategoriaService categoriaService;
	
	@Autowired
	IMarcaService marcaService;
	@Autowired
	ICompraService compraService;
	
//	@GetMapping("/productos")
//	public String listarProductos(Model model) {
//		List<Producto> productos = productoService.mostrarProductos();
//		model.addAttribute("productos", productos);
//		return "/producto/listar-productos";
//	}
	
	@GetMapping("/productos")
	public String listarProductos(Model model) {
	    List<Producto> productos = productoService.mostrarProductos();

	    // Mapa: ID producto -> true/false si tiene compras
	    Map<Long, Boolean> productosConCompras = new HashMap<>();
	    for (Producto p : productos) {
	        boolean tieneCompras = compraService.tieneComprasAsociadas(p.getId());
	        productosConCompras.put(p.getId(), tieneCompras);
	    }

	    model.addAttribute("productos", productos);
	    model.addAttribute("productosConCompras", productosConCompras);
	    return "/producto/listar-productos";
	}

	
//	@GetMapping("/search-producto")
//	@ResponseBody
//	public List<Producto> searchProducto(@RequestParam String search) {
//		return productoService.searchProducto(search);
//	}
	
	@GetMapping("/search-producto")
	@ResponseBody
	public List<ProductoConComprasDTO> searchProducto(@RequestParam String search) {
	    List<Producto> productos = productoService.searchProducto(search);

	    List<ProductoConComprasDTO> resultado = new ArrayList<>();
	    for (Producto p : productos) {
	        boolean tieneCompras = compraService.tieneComprasAsociadas(p.getId());
	        resultado.add(new ProductoConComprasDTO(
	            p.getId(),
	            p.getNombre(),
	            p.getCategoria() != null ? p.getCategoria().getNombre() : "No hay categoria",
	            p.getMarca() != null ? p.getMarca().getNombre() : "No hay marca",
	            tieneCompras
	        ));
	    }
	    return resultado;
	}

	
	@GetMapping("/formulario-producto")
	public String formularioProducto(Model model) {
		List<Categoria> categorias = categoriaService.mostrarCategorias();
		List<Marca> marcas = marcaService.mostrarMarcas();
		Producto producto = new Producto();
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categorias);
		model.addAttribute("marcas", marcas);
		return "/producto/formulario-producto";
	}
//	
//	@PostMapping("/guardar-producto")
//	public String guardarPruducto(@ModelAttribute Producto producto) {
//		productoService.guardarProducto(producto);
//		return "redirect:/productos";
//	}
	
//	@GetMapping("/modificar-producto/{id}")
//	public String modificarProducto(@PathVariable Long id, Model model) {
//		List<Categoria> categorias = categoriaService.mostrarCategorias();
//		List<Marca> marcas = marcaService.mostrarMarcas();
//		Producto productoModificado = productoService.buscarProductoPorId(id);
//		model.addAttribute("producto", productoModificado);
//		model.addAttribute("categorias", categorias);
//		model.addAttribute("marcas", marcas);
//		return "/producto/formulario-producto";
//	}
	
	@PostMapping("/guardar-producto")
	public String guardarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttrs) {
	    if (producto.getId() != null) {
	        boolean tieneCompras = compraService.tieneComprasAsociadas(producto.getId());

	        if (tieneCompras) {
	            Producto original = productoService.buscarProductoPorId(producto.getId());
	            producto.setNombre(original.getNombre());
	            producto.setCategoria(original.getCategoria());
	            producto.setMarca(original.getMarca());
	            redirectAttrs.addFlashAttribute("warning",
	                "Este producto tiene compras asociadas, no se pueden modificar nombre, categoría ni marca.");
	        }
	    }

	    productoService.guardarProducto(producto);
	    return "redirect:/productos";
	}


	
	@GetMapping("/modificar-producto/{id}")
	public String modificarProducto(@PathVariable Long id, Model model) {
	    Producto producto = productoService.buscarProductoPorId(id);
	    boolean tieneCompras = compraService.tieneComprasAsociadas(id);

	    model.addAttribute("producto", producto);
	    model.addAttribute("categorias", categoriaService.mostrarCategorias());
	    model.addAttribute("marcas", marcaService.mostrarMarcas());
	    model.addAttribute("tieneCompras", tieneCompras); // flag para el HTML

	    return "/producto/formulario-producto";
	}


	
	@GetMapping("/borrar-producto/{id}")
	public String borrarProducto(@PathVariable Long id) {
		productoService.borrarProducto(id);
		return "redirect:/productos";
		
	}

}
