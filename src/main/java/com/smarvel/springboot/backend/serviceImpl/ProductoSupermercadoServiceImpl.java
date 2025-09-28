package com.smarvel.springboot.backend.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarvel.springboot.backend.dto.ProductoSupermercadoConComprasDTO;
import com.smarvel.springboot.backend.dto.ProductoSupermercadoDTO;
import com.smarvel.springboot.backend.entities.Compra;
import com.smarvel.springboot.backend.entities.PrecioProducto;
import com.smarvel.springboot.backend.entities.ProductoSupermercado;
import com.smarvel.springboot.backend.repository.CompraRepository;
import com.smarvel.springboot.backend.repository.PrecioProductoRepository;
import com.smarvel.springboot.backend.repository.ProductoSupermercadoRepository;
import com.smarvel.springboot.backend.service.ICompraService;
import com.smarvel.springboot.backend.service.IProductoSupermercadoService;

@Service
public class ProductoSupermercadoServiceImpl implements IProductoSupermercadoService{
	
	@Autowired
	ProductoSupermercadoRepository productoSupermercadoRepository;
	@Autowired
	private PrecioProductoRepository precioProductoRepository;
	@Autowired
	CompraRepository compraRepository;
	@Autowired
	private ICompraService compraService;


	@Override
	@Transactional(readOnly = true)
	public List<ProductoSupermercado> mostrarProductoSupermercados() {
		
		return productoSupermercadoRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductoSupermercado> searchProductoSupermercado(String nombre) {
		
		return productoSupermercadoRepository.findByProducto_NombreContainingIgnoreCase(nombre);
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<ProductoSupermercadoDTO> productosPorSupermercado(Long supermercadoId) {
//	    List<ProductoSupermercado> lista = productoSupermercadoRepository.findBySupermercadoId(supermercadoId);
//
//	    return lista.stream().map(ps -> {
//	        ProductoSupermercadoDTO dto = new ProductoSupermercadoDTO();
//	        dto.setId(ps.getId());
//	        dto.setProducto(ps.getProducto());
//	        dto.setSupermercado(ps.getSupermercado());
//
//	        // Obtener precio más reciente
//	        ps.getHistoricoPrecios().stream()
//	            .max(Comparator.comparing(PrecioProducto::getFecha))
//	            .ifPresent(precioProducto -> {
//	                dto.setPrecioActual(precioProducto.getPrecio());
//	                dto.setFechaActual(precioProducto.getFecha());
//	            });
//
//	        return dto;
//	    }).toList();
//	}
	@Override
	@Transactional(readOnly = true)
	public List<ProductoSupermercadoDTO> productosPorSupermercado(Long supermercadoId) {
	    List<ProductoSupermercado> lista = productoSupermercadoRepository.findBySupermercadoId(supermercadoId);

	    return lista.stream().map(ps -> {
	        ProductoSupermercadoDTO dto = new ProductoSupermercadoDTO();
	        dto.setId(ps.getId());
	        dto.setProducto(ps.getProducto());
	        dto.setSupermercado(ps.getSupermercado());

	        // Obtener la compra más reciente (con precio y fecha) de una sola consulta
	        List<Compra> compras = compraRepository.findCompraMasRecienteByProductoSupermercado(
	            ps.getId(), PageRequest.of(0, 1)
	        );

	        if (!compras.isEmpty()) {
	            Compra compra = compras.get(0);
	            dto.setPrecioActual(compra.getPrecioProducto().getPrecio());
	            dto.setFechaActual(compra.getFechaCompra());
	        } else {
	            dto.setPrecioActual(null); // o BigDecimal.ZERO si prefieres
	            dto.setFechaActual(null);
	        }

	        return dto;
	    }).toList();
	}


	
//	@Override
//	@Transactional(readOnly = true)
//	public List<ProductoSupermercadoConComprasDTO> productosPorSupermercadoConCompras(Long supermercadoId) {
//	    List<ProductoSupermercado> lista = productoSupermercadoRepository.findBySupermercadoId(supermercadoId);
//
//	    return lista.stream().map(ps -> {
//	        ProductoSupermercadoConComprasDTO dto = new ProductoSupermercadoConComprasDTO();
//	        dto.setId(ps.getId());
//	        dto.setProducto(ps.getProducto());
//	        dto.setSupermercado(ps.getSupermercado());
//
//	        ps.getHistoricoPrecios().stream()
//	            .max(Comparator.comparing(PrecioProducto::getFecha))
//	            .ifPresent(precioProducto -> {
//	                dto.setPrecioActual(precioProducto.getPrecio());
//	                dto.setFechaActual(precioProducto.getFecha());
//	            });
//
//	        boolean tieneCompras = compraService.tieneComprasAsociadasSupermercado(ps.getId());
//	        dto.setTieneCompras(tieneCompras);
//
//	        return dto;
//	    }).toList();
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<ProductoSupermercadoConComprasDTO> searchPorNombreYSuperConCompras(Long supermercadoId, String nombre) {
//	    List<ProductoSupermercado> lista = productoSupermercadoRepository.searchPorNombreYSuper(supermercadoId, nombre);
//
//	    return lista.stream().map(ps -> {
//	        ProductoSupermercadoConComprasDTO dto = new ProductoSupermercadoConComprasDTO();
//	        dto.setId(ps.getId());
//	        dto.setProducto(ps.getProducto());
//	        dto.setSupermercado(ps.getSupermercado());
//
//	        ps.getHistoricoPrecios().stream()
//	            .max(Comparator.comparing(PrecioProducto::getFecha))
//	            .ifPresent(precioProducto -> {
//	                dto.setPrecioActual(precioProducto.getPrecio());
//	                dto.setFechaActual(precioProducto.getFecha());
//	            });
//
//	        boolean tieneCompras = compraService.tieneComprasAsociadasSupermercado(ps.getId());
//	        dto.setTieneCompras(tieneCompras);
//
//	        return dto;
//	    }).toList();
//	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductoSupermercadoConComprasDTO> productosPorSupermercadoConCompras(Long supermercadoId) {
	    List<ProductoSupermercado> lista = productoSupermercadoRepository.findBySupermercadoId(supermercadoId);

	    return lista.stream().map(ps -> {
	        ProductoSupermercadoConComprasDTO dto = new ProductoSupermercadoConComprasDTO();
	        dto.setId(ps.getId());
	        dto.setProducto(ps.getProducto());
	        dto.setSupermercado(ps.getSupermercado());

	        // Obtener compra más reciente (precio y fecha)
	        List<Compra> compras = compraRepository.findCompraMasRecienteByProductoSupermercado(ps.getId(), PageRequest.of(0, 1));

	        if (!compras.isEmpty()) {
	            Compra compra = compras.get(0);
	            dto.setPrecioActual(compra.getPrecioProducto().getPrecio());
	            dto.setFechaActual(compra.getFechaCompra());
	        } else {
	            dto.setPrecioActual(null);
	            dto.setFechaActual(null);
	        }

	        boolean tieneCompras = compraService.tieneComprasAsociadasSupermercado(ps.getId());
	        dto.setTieneCompras(tieneCompras);

	        return dto;
	    }).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductoSupermercadoConComprasDTO> searchPorNombreYSuperConCompras(Long supermercadoId, String nombre) {
	    List<ProductoSupermercado> lista = productoSupermercadoRepository.searchPorNombreYSuper(supermercadoId, nombre);

	    return lista.stream().map(ps -> {
	        ProductoSupermercadoConComprasDTO dto = new ProductoSupermercadoConComprasDTO();
	        dto.setId(ps.getId());
	        dto.setProducto(ps.getProducto());
	        dto.setSupermercado(ps.getSupermercado());

	        // Obtener compra más reciente (precio y fecha)
	        List<Compra> compras = compraRepository.findCompraMasRecienteByProductoSupermercado(ps.getId(), PageRequest.of(0, 1));

	        if (!compras.isEmpty()) {
	            Compra compra = compras.get(0);
	            dto.setPrecioActual(compra.getPrecioProducto().getPrecio());
	            dto.setFechaActual(compra.getFechaCompra());
	        } else {
	            dto.setPrecioActual(null);
	            dto.setFechaActual(null);
	        }

	        boolean tieneCompras = compraService.tieneComprasAsociadasSupermercado(ps.getId());
	        dto.setTieneCompras(tieneCompras);

	        return dto;
	    }).toList();
	}


	@Override
	@Transactional(readOnly = true)
	public ProductoSupermercado buscarProductoSupermercadoPorId(Long id) {
	    return productoSupermercadoRepository.findById(id)
	        .orElseThrow(() -> new NoSuchElementException("ProductoSupermercado no encontrado con id " + id));
	}


	@Override
	@Transactional
	public ProductoSupermercado guardarProductoSupermercado(ProductoSupermercado productoSupermercado) {
		
		return productoSupermercadoRepository.save(productoSupermercado);
	}

//	@Override
//	@Transactional
//	public void borrarProductoSupermercado(Long id) {
//		if (compraRepository.existsByPrecioProductoId(id)) {
//            throw new IllegalStateException("No se puede borrar este precio porque existen compras asociadas.");
//        }
//		precioProductoRepository.deleteByProductoSupermercadoId(id);
//		productoSupermercadoRepository.deleteById(id);
//		
//	}
	@Override
	@Transactional
	public void borrarProductoSupermercado(Long id) {
	    // Obtener los IDs de precios asociados al producto-supermercado
	    List<Long> precioIds = precioProductoRepository.findIdsByProductoSupermercadoId(id);

	    // Verificar para cada precio si existen compras asociadas
	    for (Long precioId : precioIds) {
	        if (compraRepository.existsByPrecioProductoId(precioId)) {
	            throw new IllegalStateException("No se puede borrar este producto porque existen compras asociadas (debe de eliminar primero las compras asociadas a este producto).");
	        }
	    }

	    // Si no hay compras asociadas, borrar precios y luego el producto-supermercado
	    precioProductoRepository.deleteByProductoSupermercadoId(id);
	    productoSupermercadoRepository.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProductoSupermercado buscarPorProductoYSupermercado(Long productoId, Long supermercadoId) {
	    return productoSupermercadoRepository.findByProducto_IdAndSupermercado_Id(productoId, supermercadoId);
	}
	/*@Override
	@Transactional(readOnly = true)
	public ProductoSupermercado buscarPrecioActual(Long productoId, Long supermercadoId) {
	    return productoSupermercadoRepository.findTopByProducto_IdAndSupermercado_IdOrderByFechaDesc(productoId, supermercadoId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductoSupermercado> buscarTodosPorProductoYSupermercado(Long productoId, Long supermercadoId) {
	    return productoSupermercadoRepository.findByProducto_IdAndSupermercado_IdOrderByFechaDesc(productoId, supermercadoId);
	}*/

	@Override
	@Transactional(readOnly = true)
	public List<ProductoSupermercadoDTO> searchPorNombreYSuper(Long superId, String search) {
	    List<ProductoSupermercado> lista;
	    if (search == null || search.isBlank()) {
	        lista = productoSupermercadoRepository.findBySupermercadoId(superId);
	    } else {
	        lista = productoSupermercadoRepository
	                .findBySupermercadoIdAndProductoNombreContainingIgnoreCase(superId, search);
	    }

	    return lista.stream().map(ps -> {
	        ProductoSupermercadoDTO dto = new ProductoSupermercadoDTO();
	        dto.setId(ps.getId());
	        dto.setProducto(ps.getProducto());
	        dto.setSupermercado(ps.getSupermercado());

	        ps.getHistoricoPrecios().stream()
	            .max(Comparator.comparing(PrecioProducto::getFecha))
	            .ifPresent(precioProducto -> {
	                dto.setPrecioActual(precioProducto.getPrecio());
	                dto.setFechaActual(precioProducto.getFecha());
	            });

	        return dto;
	    }).toList();
	}


	/* @Override
	    public ProductoSupermercado buscarPorProductoSupermercadoPrecio(Long productoId, Long supermercadoId, Double precio) {
	        return productoSupermercadoRepository.buscarPorProductoSupermercadoPrecio(productoId, supermercadoId, precio);

	 }*/
	

}
