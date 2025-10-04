package com.smarvel.springboot.backend.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.smarvel.springboot.backend.dto.PrecioProductoDTO;
import com.smarvel.springboot.backend.dto.ProductoSupermercadoConComprasDTO;
import com.smarvel.springboot.backend.dto.ProductoSupermercadoDTO;
import com.smarvel.springboot.backend.entities.PrecioProducto;
import com.smarvel.springboot.backend.entities.Producto;
import com.smarvel.springboot.backend.entities.ProductoSupermercado;
import com.smarvel.springboot.backend.entities.Supermercado;
import com.smarvel.springboot.backend.service.ICompraService;
import com.smarvel.springboot.backend.service.IProductoService;
import com.smarvel.springboot.backend.service.IProductoSupermercadoService;
import com.smarvel.springboot.backend.service.ISupermercadoService;

@Controller
public class ProductoSupermercadoController {
	
	 @Autowired
	    private IProductoSupermercadoService productoSupermercadoService;
	 @Autowired
	    private ISupermercadoService supermercadoService; // Para mostrar el nombre
	 @Autowired
	 private IProductoService productoService;
	 @Autowired
	 private ICompraService compraService;

	    @GetMapping("/supermercado/{id}/productos")
	    public String productosDeSupermercado(@PathVariable Long id, Model model) {
	        List<ProductoSupermercadoDTO> productosSuper = productoSupermercadoService.productosPorSupermercado(id);
	        Map<Long, Boolean> productosuperConCompras = new HashMap<>();
		    for (ProductoSupermercadoDTO s : productosSuper) {
		        boolean tieneCompras = compraService.tieneComprasAsociadasSupermercado(s.getId());
		        productosuperConCompras.put(s.getId(), tieneCompras);
		    }
	        Supermercado supermercado = supermercadoService.buscarSupermercadoPorId(id);
	        model.addAttribute("productosSuper", productosSuper);
	        model.addAttribute("supermercado", supermercado);
	        model.addAttribute("productosuperConCompras", productosuperConCompras);
	        return "/productosupermercado/listar-productos-supermercado";
	    }
	    
	    @GetMapping("/search-producto-supermercado")
	    @ResponseBody
	    public List<ProductoSupermercadoConComprasDTO> searchProductoSupermercado(@RequestParam String search, @RequestParam Long superId) {
	    	if (search == null || search.isBlank()) {
	            // Sin búsqueda, devuelves solo los productos de ese supermercado
	            return productoSupermercadoService.productosPorSupermercadoConCompras(superId);
	        } else {
	            // Con búsqueda, devuelves solo los productos de ese super + filtro
	            return productoSupermercadoService.searchPorNombreYSuperConCompras(superId, search);
	        }
	    }
	    
	    @PostMapping("/guardar-producto-supermercado")
	    public String guardarProductoSupermercado(@ModelAttribute ProductoSupermercado formPS) {
	        ProductoSupermercado ps;

	        if (formPS.getId() != null) {
	            ps = productoSupermercadoService.buscarProductoSupermercadoPorId(formPS.getId());
	        } else {
	            ps = new ProductoSupermercado();
	        }

	        ps.setProducto(productoService.buscarProductoPorId(formPS.getProducto().getId()));
	        ps.setSupermercado(supermercadoService.buscarSupermercadoPorId(formPS.getSupermercado().getId()));

	        PrecioProducto nuevoPrecio = formPS.getPrecioActual();
	        nuevoPrecio.setProductoSupermercado(ps);

	        if (ps.getHistoricoPrecios().isEmpty()) {
	            ps.getHistoricoPrecios().add(nuevoPrecio);
	        } else {
	            // Puedes decidir si reemplazar el último o añadirlo al histórico
	            ps.getHistoricoPrecios().set(ps.getHistoricoPrecios().size() - 1, nuevoPrecio);
	        }

	        productoSupermercadoService.guardarProductoSupermercado(ps);

	        return "redirect:/supermercado/" + ps.getSupermercado().getId() + "/productos";
	    }
	    
	    @GetMapping("/modificar-producto-supermercado/{id}")
	    public String modificarProductoSupermercado(@PathVariable Long id, Model model) {
	        ProductoSupermercado ps = productoSupermercadoService.buscarProductoSupermercadoPorId(id);

	        // Obtener último precio o crear uno nuevo
	        PrecioProducto precioActual = ps.getHistoricoPrecios().isEmpty()
	            ? new PrecioProducto()
	            : ps.getHistoricoPrecios().get(ps.getHistoricoPrecios().size() - 1);

	        ps.setPrecioActual(precioActual);

	        model.addAttribute("productoSupermercado", ps);
	        model.addAttribute("productos", productoService.mostrarProductos());
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());

	        return "productosupermercado/formulario-producto-supermercado";
	    }

	    @GetMapping("/formulario-producto-supermercado")
	    public String formularioProductoSupermercado(Model model) {
	        ProductoSupermercado ps = new ProductoSupermercado();
	        ps.setPrecioActual(new PrecioProducto());
	        model.addAttribute("productoSupermercado", ps);
	        model.addAttribute("productos", productoService.mostrarProductos());
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        return "productosupermercado/formulario-producto-supermercado";
	    }
	    
	    @GetMapping("/borrar-producto-supermercado/{id}")
	    public String borrarProductoSupermercado(@PathVariable Long id, RedirectAttributes redirectAttrs) {
	    	Long supermercadoId = null;
	        try {
	        	supermercadoId = productoSupermercadoService.buscarProductoSupermercadoPorId(id).getSupermercado().getId();
	            productoSupermercadoService.borrarProductoSupermercado(id);
	            redirectAttrs.addFlashAttribute("success", "Producto eliminado correctamente.");
	        } catch (IllegalStateException e) {
	            redirectAttrs.addFlashAttribute("error", e.getMessage());
	        }
	        return "redirect:/supermercado/" + supermercadoId + "/productos";
	    }

	    
	    @GetMapping("/precios-producto-supermercado")
	    @ResponseBody
	    public List<PrecioProductoDTO> obtenerPreciosProductoSupermercado(
	            @RequestParam Long productoId,
	            @RequestParam Long supermercadoId) {

	        // 1️⃣ Buscar el ProductoSupermercado correspondiente
	        ProductoSupermercado ps = productoSupermercadoService
	                .buscarPorProductoYSupermercado(productoId, supermercadoId);

	        if (ps == null) {
	            return Collections.emptyList();
	        }

	        // 2️⃣ Tomar su histórico de precios y mapearlo a DTO
	        return ps.getHistoricoPrecios().stream()
	                .filter(pp -> pp.getPrecio() != null) // opcional
	                .map(pp -> new PrecioProductoDTO(pp.getId(), pp.getPrecio(), pp.getFecha()))
	                .collect(Collectors.toList());
	    }





}
