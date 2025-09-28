package com.smarvel.springboot.backend.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

import com.smarvel.springboot.backend.dto.BuscarEntreFechasDTO;
import com.smarvel.springboot.backend.entities.Compra;
import com.smarvel.springboot.backend.entities.PrecioProducto;
import com.smarvel.springboot.backend.entities.Producto;
import com.smarvel.springboot.backend.entities.ProductoSupermercado;
import com.smarvel.springboot.backend.entities.Supermercado;
import com.smarvel.springboot.backend.enums.Mes;
import com.smarvel.springboot.backend.service.ICompraService;
import com.smarvel.springboot.backend.service.IPrecioProductoService;
import com.smarvel.springboot.backend.service.IProductoService;
import com.smarvel.springboot.backend.service.IProductoSupermercadoService;
import com.smarvel.springboot.backend.service.ISupermercadoService;

@Controller
public class CompraController {
	
	  @Autowired
	    private ICompraService compraService;
	    @Autowired
	    private IProductoService productoService;
	    @Autowired
	    private ISupermercadoService supermercadoService;
	    @Autowired
	    private IProductoSupermercadoService productoSupermercadoService;
	    @Autowired
	    private IPrecioProductoService precioProductoService;

	    // ======= LISTADO GENERAL =======
	    @GetMapping("/compras")
	    public String listarCompras(Model model) {
	        model.addAttribute("compras", compraService.mostrarCompras());
	        model.addAttribute("buscarentrefechas", new BuscarEntreFechasDTO()); // solo el DTO de fechas
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        return "/compra/listar-compras";
	    }

	    // ======= FILTROS =======
	    @GetMapping("/compras-fecha")
	    public String mostrarCompraPorFecha(Model model, @RequestParam LocalDate fecha) {
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        model.addAttribute("comprafecha", compraService.mostrarComprasPorFecha(fecha));
	     // ✅ Añadir total gastado ese día (todos los supermercados)
	        BigDecimal total = compraService.totalGastadoPorFecha(fecha);
	        model.addAttribute("totalGastado", total);
	        return "/compra/listar-compras-filtros";
	    }

	    @GetMapping("/compras-rango-fecha")
	    public String mostrarCompraEntreFecha(Model model, 
	                                          @RequestParam LocalDate fecha1, 
	                                          @RequestParam LocalDate fecha2) {
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        model.addAttribute("comprafechas", compraService.mostrarComprasEntreFechas(fecha1, fecha2));
	     // ✅ Añadir total gastado en el rango (todos los supermercados)
	        BigDecimal total = compraService.totalGastadoPorFechas(fecha1, fecha2);
	        model.addAttribute("totalGastado", total);
	        return "/compra/listar-compras-filtros";
	    }

	    @GetMapping("/compras-supermercado")
	    public String buscarCompraPorSupermercado(Model model, @RequestParam Long supermercado) {
	        model.addAttribute("comprasuper", compraService.buscarPorSupermercado(supermercado));
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        model.addAttribute("supermercadoSeleccionado", supermercado);
	     // 🔹 Añadir el total gastado en este supermercado
	        BigDecimal total = compraService.totalGastadoPorSupermercado(supermercado);
	        model.addAttribute("totalGastado", total);
	        return "/compra/listar-compras-filtros";
	    }

	    @GetMapping("/compras-supermercado-fecha")
	    public String filtrarPorSupermercadoYFecha(Model model,
	                                               @RequestParam Long supermercado,
	                                               @RequestParam LocalDate fecha) {
	        model.addAttribute("comprasuper", compraService.buscarPorSupermercadoYFecha(supermercado, fecha));
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        model.addAttribute("supermercadoSeleccionado", supermercado);
	        BigDecimal total = compraService.totalGastadoPorSupermercadoYFecha(supermercado, fecha);
	        model.addAttribute("totalGastado", total);
	        return "/compra/listar-compras-filtros";
	    }

	    @GetMapping("/compras-supermercado-rango")
	    public String filtrarPorSupermercadoYRango(Model model,
	                                               @RequestParam Long supermercado,
	                                               @RequestParam LocalDate fecha1,
	                                               @RequestParam LocalDate fecha2) {
	        model.addAttribute("comprasuper", compraService.buscarPorSupermercadoYFechas(supermercado, fecha1, fecha2));
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        model.addAttribute("supermercadoSeleccionado", supermercado);
	        BigDecimal total = compraService.totalGastadoPorSupermercadoYFechas(supermercado, fecha1, fecha2);
	        model.addAttribute("totalGastado", total);
	        return "/compra/listar-compras-filtros";
	    }

	    @GetMapping("/compras-mes")
	    public String compraPorMes(Model model, @RequestParam Mes mes, @RequestParam int anio) {
	        BigDecimal total = compraService.totalGastadoPorMes(mes.getNumero(), anio);
	        List<Compra> compras = compraService.buscarComprasPorMesYAnio(mes.getNumero(), anio);

	        model.addAttribute("totalGastado", total);
	        model.addAttribute("comprasMes", compras);
	        model.addAttribute("mes", mes);
	        model.addAttribute("anio", anio);
	        return "/compra/listar-compras-filtros";
	    }

	    @GetMapping("/compras-anio")
	    public String compraPorAnio(@RequestParam int anio, Model model) {
	        BigDecimal total = compraService.totalGastadoPorAnio(anio);
	        List<Compra> compras = compraService.buscarComprasPorAnio(anio);

	        model.addAttribute("totalGastado", total);
	        model.addAttribute("comprasAnio", compras);
	        model.addAttribute("anio", anio);
	        return "/compra/listar-compras-filtros";
	    }

	    // ======= BÚSQUEDA AJAX =======
	    @GetMapping("/search-compra")
	    @ResponseBody
	    public List<Compra> searchCompra(@RequestParam String search) {
	        return compraService.searchCompra(search);
	    }

	    // ======= FORMULARIOS =======
//	    @GetMapping("/formulario-compra")
//	    public String formularioCompra(Model model) {
//	        Compra compra = new Compra();
//
//	        PrecioProducto pp = new PrecioProducto();
//	        pp.setProductoSupermercado(new ProductoSupermercado());
//
//	        compra.setPrecioProducto(pp);
//
//	        List<Producto> productos = productoService.mostrarProductos();
//	        List<Producto> productosUnicos = productos.stream()
//	                .collect(Collectors.collectingAndThen(
//	                        Collectors.toMap(
//	                                p -> p.getNombre() + "_" + (p.getMarca() != null ? p.getMarca().getNombre() : ""),
//	                                p -> p,
//	                                (p1, p2) -> p1
//	                        ),
//	                        m -> new ArrayList<>(m.values())
//	                ));
//
//	        model.addAttribute("compra", compra);
//	        model.addAttribute("productos", productosUnicos);
//	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
//	        return "/compra/formulario-compra";
//	    }
//	    @GetMapping("/formulario-compra")
//	    public String formularioCompra(Model model) {
//	        Compra compra = new Compra();
//
//	        PrecioProducto pp = new PrecioProducto();
//	        pp.setProductoSupermercado(new ProductoSupermercado());
//	        compra.setPrecioProducto(pp);
//
//	        List<Producto> productos = productoService.mostrarProductos();
//	        List<Producto> productosUnicos = productos.stream()
//	                .collect(Collectors.collectingAndThen(
//	                        Collectors.toMap(
//	                                p -> p.getNombre() + "_" + (p.getMarca() != null ? p.getMarca().getNombre() : ""),
//	                                p -> p,
//	                                (p1, p2) -> p1
//	                        ),
//	                        m -> new ArrayList<>(m.values())
//	                ));
//
//	        model.addAttribute("compra", compra);
//	        model.addAttribute("productos", productosUnicos);
//	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
//	        return "/compra/formulario-compra";
//	    }
//	    @GetMapping("/formulario-compra")
//	    public String formularioCompra(@ModelAttribute Compra compra, Model model) {
//	        if (compra == null || compra.getId() == null) {
//	            compra = new Compra();
//
//	            PrecioProducto pp = new PrecioProducto();
//	            pp.setProductoSupermercado(new ProductoSupermercado());
//	            compra.setPrecioProducto(pp);
//	        }
//
//	        List<Producto> productos = productoService.mostrarProductos();
//	        List<Producto> productosUnicos = productos.stream()
//	                .collect(Collectors.collectingAndThen(
//	                        Collectors.toMap(
//	                                p -> p.getNombre() + "_" + (p.getMarca() != null ? p.getMarca().getNombre() : ""),
//	                                p -> p,
//	                                (p1, p2) -> p1
//	                        ),
//	                        m -> new ArrayList<>(m.values())
//	                ));
//
//	        model.addAttribute("compra", compra);
//	        model.addAttribute("productos", productosUnicos);
//	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
//
//	        return "/compra/formulario-compra";
//	    }
	    @GetMapping("/formulario-compra")
	    public String formularioCompra(@ModelAttribute Compra compra, Model model) {
	        if (compra == null || compra.getId() == null) {
	            compra = new Compra();

	            PrecioProducto pp = new PrecioProducto();
	            pp.setProductoSupermercado(new ProductoSupermercado());
	            compra.setPrecioProducto(pp);
	        } else {
	            // Si viene con id (modo edición o error), aseguramos cargar precio y total para mostrar correctamente
	            if (compra.getPrecioProducto() == null) {
	                compra.setPrecioProducto(new PrecioProducto());
	            }
	            // No modificar productoSupermercado aquí porque formulario carga el id directamente y lista productos/supermercados
	        }

	        List<Producto> productos = productoService.mostrarProductos();
	        List<Producto> productosUnicos = productos.stream()
	                .collect(Collectors.collectingAndThen(
	                        Collectors.toMap(
	                                p -> p.getNombre() + "_" + (p.getMarca() != null ? p.getMarca().getNombre() : ""),
	                                p -> p,
	                                (p1, p2) -> p1
	                        ),
	                        m -> new ArrayList<>(m.values())
	                ));

	        model.addAttribute("compra", compra);
	        model.addAttribute("productos", productosUnicos);
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
	        
	        // En caso de que quieras inicializar variables para el script JS que carga precios y total
	        BigDecimal precioUnitario = (compra.getPrecioProducto() != null && compra.getPrecioProducto().getPrecio() != null) ? 
	                                    compra.getPrecioProducto().getPrecio() : BigDecimal.ZERO;
	        BigDecimal precioTotal = (compra.getPrecioPagado() != null) ? compra.getPrecioPagado() : BigDecimal.ZERO;

	        model.addAttribute("precioUnitario", precioUnitario);
	        model.addAttribute("precioTotal", precioTotal);

	        return "/compra/formulario-compra";
	    }

	    @PostMapping("/guardar-compra")
	    public String guardarCompra(
	            @ModelAttribute Compra compra,
	            @RequestParam(required = false) Double precioManual,
	            @RequestParam(required = false) Double precioUnitarioSeleccionado,
	            RedirectAttributes redirectAttrs) {

	        try {
	            Long productoId = compra.getPrecioProducto()
	                    .getProductoSupermercado()
	                    .getProducto()
	                    .getId();

	            Long supermercadoId = compra.getPrecioProducto()
	                    .getProductoSupermercado()
	                    .getSupermercado()
	                    .getId();

	            Compra compraFinal = (compra.getId() != null)
	                    ? compraService.buscarCompraPorId(compra.getId())
	                    : new Compra();

	            // 1️⃣ ProductoSupermercado
	            ProductoSupermercado ps = productoSupermercadoService
	                    .buscarPorProductoYSupermercado(productoId, supermercadoId);
	            if (ps == null) {
	                ps = new ProductoSupermercado();
	                ps.setProducto(productoService.buscarProductoPorId(productoId));
	                ps.setSupermercado(supermercadoService.buscarSupermercadoPorId(supermercadoId));
	                ps = productoSupermercadoService.guardarProductoSupermercado(ps);
	            }

	            // 2️⃣ Precio unitario real (en BigDecimal)
	            BigDecimal precioUnitarioBD = null;
	            if (precioManual != null) {
	                precioUnitarioBD = BigDecimal.valueOf(precioManual);
	            } else if (precioUnitarioSeleccionado != null) {
	                precioUnitarioBD = BigDecimal.valueOf(precioUnitarioSeleccionado);
	            }

	            // 3️⃣ PrecioProducto
	            PrecioProducto precioProducto = precioProductoService
	                    .buscarPorProductoSupermercadoYFecha(ps.getId(), compra.getFechaCompra());

	            if (precioProducto == null) {
	                precioProducto = new PrecioProducto();
	                precioProducto.setProductoSupermercado(ps);
	                precioProducto.setPrecio(precioUnitarioBD != null ? precioUnitarioBD : BigDecimal.ZERO);
	                precioProducto.setFecha(compra.getFechaCompra());
	                precioProducto = precioProductoService.guardar(precioProducto);
	            } else {
	                if (precioUnitarioBD != null) {
	                    precioProducto.setPrecio(precioUnitarioBD);
	                    precioProducto = precioProductoService.guardar(precioProducto);
	                }
	            }

	            // 4️⃣ Compra
	            compraFinal.setPrecioProducto(precioProducto);
	            compraFinal.setCantidad(compra.getCantidad());
	            compraFinal.setFechaCompra(compra.getFechaCompra());

	            BigDecimal cantidadBD = BigDecimal.valueOf(compra.getCantidad() != null ? compra.getCantidad() : 0);

	            BigDecimal precioPagado = (precioProducto.getPrecio() != null ? precioProducto.getPrecio() : BigDecimal.ZERO)
	                    .multiply(cantidadBD);

	            compraFinal.setPrecioPagado(precioPagado);

	            // 5️⃣ Guardar con validación en el service (lanza IllegalStateException si existe duplicado)
	            compraService.guardarCompra(compraFinal);

	            redirectAttrs.addFlashAttribute("success", "Compra guardada correctamente.");
	            return "redirect:/compras";

	        } catch (IllegalStateException e) {
	        	if (compra.getPrecioProducto() == null) {
	                compra.setPrecioProducto(new PrecioProducto());
	            }
	            if (compra.getPrecioProducto().getPrecio() == null) {
	                // Opcional: setea un BigDecimal.ZERO o extrae el precio manual o seleccionado
	                compra.getPrecioProducto().setPrecio(BigDecimal.ZERO);
	            }
	            redirectAttrs.addFlashAttribute("error", e.getMessage());
	            redirectAttrs.addFlashAttribute("compra", compra); // mantener datos y modo edición
	            return "redirect:/formulario-compra";
	        }
	    }

	    
	    @GetMapping("/modificar-compra/{id}")
	    public String modificarCompra(@PathVariable Long id, Model model) {
	        Compra compra = compraService.buscarCompraPorId(id);

	        // Evitar NPE (por si no estaba inicializado)
	        if (compra.getPrecioProducto() == null) {
	            compra.setPrecioProducto(new PrecioProducto());
	        }
	        if (compra.getPrecioProducto().getProductoSupermercado() == null) {
	            compra.getPrecioProducto().setProductoSupermercado(new ProductoSupermercado());
	        }

	        model.addAttribute("compra", compra);

	        // Productos sin duplicados
	        List<Producto> productos = productoService.mostrarProductos();
	        List<Producto> productosUnicos = productos.stream()
	                .collect(Collectors.collectingAndThen(
	                        Collectors.toMap(
	                                p -> p.getNombre() + "_" + (p.getMarca() != null ? p.getMarca().getNombre() : ""),
	                                p -> p,
	                                (p1, p2) -> p1
	                        ),
	                        m -> new ArrayList<>(m.values())
	                ));

	        model.addAttribute("productos", productosUnicos);
	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());

	        return "/compra/formulario-compra";
	    }



//	    @PostMapping("/guardar-compra")
//	    public String guardarCompra(@ModelAttribute Compra compra,
//	                                @RequestParam(required = false) Double precioManual) {
//
//	        Long productoId = compra.getPrecioProducto()
//	                                .getProductoSupermercado()
//	                                .getProducto()
//	                                .getId();
//
//	        Long supermercadoId = compra.getPrecioProducto()
//	                                    .getProductoSupermercado()
//	                                    .getSupermercado()
//	                                    .getId();
//
//	        Compra compraFinal = (compra.getId() != null)
//	                ? compraService.buscarCompraPorId(compra.getId())
//	                : new Compra();
//
//	        // 1️⃣ Buscar o crear ProductoSupermercado
//	        ProductoSupermercado ps = productoSupermercadoService
//	                .buscarPorProductoYSupermercado(productoId, supermercadoId);
//
//	        if (ps == null) {
//	            ps = new ProductoSupermercado();
//	            ps.setProducto(productoService.buscarProductoPorId(productoId));
//	            ps.setSupermercado(supermercadoService.buscarSupermercadoPorId(supermercadoId));
//	            ps = productoSupermercadoService.guardarProductoSupermercado(ps);
//	        }
//
//	        // 2️⃣ Buscar o crear PrecioProducto
//	        PrecioProducto precioProducto = precioProductoService
//	                .buscarPorProductoSupermercadoYFecha(ps.getId(), compra.getFechaCompra());
//
//	        if (precioProducto == null) {
//	            precioProducto = new PrecioProducto();
//	            precioProducto.setProductoSupermercado(ps);
//	            precioProducto.setPrecio(precioManual != null ? precioManual : 0.0);
//	            precioProducto.setFecha(compra.getFechaCompra());
//	            precioProducto = precioProductoService.guardar(precioProducto);
//	        }
//
//	        // 3️⃣ Asignar en la compra el precioProducto
//	        compraFinal.setPrecioProducto(precioProducto);
//	        compraFinal.setCantidad(compra.getCantidad());
//	        compraFinal.setPrecioPagado(precioManual != null ? precioManual : precioProducto.getPrecio());
//	        compraFinal.setFechaCompra(compra.getFechaCompra());
//
//	        // 4️⃣ Guardar la compra
//	        compraService.guardarCompra(compraFinal);
//
//	        return "redirect:/compras";
//	    }


//	    @GetMapping("/modificar-compra/{id}")
//	    public String modificarCompra(@PathVariable Long id, Model model) {
//	        Compra compra = compraService.buscarCompraPorId(id);
//	        model.addAttribute("compra", compra);
//	        model.addAttribute("productos", productoService.mostrarProductos());
//	        model.addAttribute("supermercados", supermercadoService.mostrarSupermercados());
//	        return "/compra/formulario-compra";
//	    }

	    @GetMapping("/borrar-compra/{id}")
	    public String borrarCompra(@PathVariable Long id) {
	        compraService.borrarCompra(id);
	        return "redirect:/compras";
	    }

}
