package com.smarvel.springboot.backend.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarvel.springboot.backend.entities.Compra;
import com.smarvel.springboot.backend.repository.CompraRepository;
import com.smarvel.springboot.backend.service.ICompraService;

@Service
public class CompraServiceImpl implements ICompraService{
	
	@Autowired
	CompraRepository compraRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Compra> mostrarCompras() {
		
		return compraRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> searchCompra(String search) {
	    return compraRepository.findByPrecioProducto_ProductoSupermercado_Producto_NombreContainingIgnoreCaseOrPrecioProducto_ProductoSupermercado_Supermercado_NombreContainingIgnoreCase(search, search);
	}

	@Override
	@Transactional(readOnly = true)
	public Compra buscarCompraPorId(Long id) {
		return compraRepository.findById(id).orElseThrow(null);
	}

	@Override
//	@Transactional
//	public Compra guardarCompra(Compra compra) {
//		
//		return compraRepository.save(compra);
//	}
	@Transactional
	public Compra guardarCompra(Compra compra) {
	    Long productoId = compra.getPrecioProducto().getProductoSupermercado().getProducto().getId();
	    Long supermercadoId = compra.getPrecioProducto().getProductoSupermercado().getSupermercado().getId();
	    LocalDate fechaCompra = compra.getFechaCompra();
	    Long compraId = compra.getId();

	    boolean existeCompraMismaFecha = compraRepository.existsByProductoSupermercadoProductoFechaExcludingId(
	    		productoId, supermercadoId, fechaCompra, compraId);

	    if (existeCompraMismaFecha) {
	        throw new IllegalStateException("Ya existe una compra para este producto, supermercado y fecha. No se permite duplicar.");
	    }

	    return compraRepository.save(compra);
	}

	@Override
	@Transactional
	public void borrarCompra(Long id) {
		compraRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> mostrarComprasPorFecha(LocalDate fecha) {
		
		return compraRepository.findByFechaCompra(fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> mostrarComprasEntreFechas(LocalDate fecha1, LocalDate fecha2) {
		
		return compraRepository.findByFechaCompraBetween(fecha1, fecha2);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> buscarPorSupermercado(Long supermercadoId) {
	    return compraRepository.findByPrecioProducto_ProductoSupermercado_Supermercado_Id(supermercadoId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> buscarPorSupermercadoYFecha(Long supermercadoId, LocalDate fecha) {
	    return compraRepository.findByPrecioProducto_ProductoSupermercado_Supermercado_IdAndFechaCompra(supermercadoId, fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> buscarPorSupermercadoYFechas(Long supermercadoId, LocalDate fecha1, LocalDate fecha2) {
	    return compraRepository.findByPrecioProducto_ProductoSupermercado_Supermercado_IdAndFechaCompraBetween(supermercadoId, fecha1, fecha2);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal totalGastadoPorMes(int mes, int anio) {
        // Si no hay datos, devolvemos 0 para evitar null
        BigDecimal total = compraRepository.totalGastadoPorMes(mes, anio);
        return total != null ? total : BigDecimal.ZERO;
    }
	
	@Override
	@Transactional(readOnly = true)
	public List<Compra> buscarComprasPorMesYAnio(int mes, int anio) {
        return compraRepository.findByMesYAnio(mes, anio);
    }
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal totalGastadoPorAnio(int anio) {
	    BigDecimal total = compraRepository.totalGastadoPorAnio(anio);
	    return total != null ? total : BigDecimal.ZERO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Compra> buscarComprasPorAnio(int anio) {
	    return compraRepository.findByAnio(anio);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal totalGastadoPorSupermercado(Long supermercadoId) {
	    BigDecimal total = compraRepository.totalGastadoPorSupermercado(supermercadoId);
	    return total != null ? total : BigDecimal.ZERO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal totalGastadoPorFecha(LocalDate fecha) {
	    BigDecimal total = compraRepository.totalGastadoPorFecha(fecha);
	    return total != null ? total : BigDecimal.ZERO;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal totalGastadoPorFechas(LocalDate fecha1, LocalDate fecha2) {
	    BigDecimal total = compraRepository.totalGastadoPorFechas(fecha1, fecha2);
	    return total != null ? total : BigDecimal.ZERO;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal totalGastadoPorSupermercadoYFecha(Long supermercadoId, LocalDate fecha) {
	    BigDecimal total = compraRepository.totalGastadoPorSupermercadoYFecha(supermercadoId, fecha);
	    return total != null ? total : BigDecimal.ZERO;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal totalGastadoPorSupermercadoYFechas(Long supermercadoId, LocalDate fecha1, LocalDate fecha2) {
	    BigDecimal total = compraRepository.totalGastadoPorSupermercadoYFechas(supermercadoId, fecha1, fecha2);
	    return total != null ? total : BigDecimal.ZERO;
	}
	@Override
	@Transactional(readOnly = true)
	public boolean tieneComprasAsociadas(Long productoId) {
	    // Buscar si alguna compra tiene PrecioProducto que apunte a ProductoSupermercado que a su vez apunte a este producto
	    return compraRepository.existsByPrecioProductoProductoSupermercadoProductoId(productoId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean tieneComprasAsociadasSupermercado(Long supermercadoId) {
	    return compraRepository.existsByPrecioProductoProductoSupermercadoSupermercadoId(supermercadoId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existeCompraDuplicada(Long productoId, Long supermercadoId, LocalDate fechaCompra, Long compraId) {
	    return compraRepository.existsByProductoSupermercadoProductoFechaExcludingId(productoId, supermercadoId, fechaCompra, compraId);
	}



}
