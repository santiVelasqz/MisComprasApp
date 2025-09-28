package com.smarvel.springboot.backend.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarvel.springboot.backend.entities.PrecioProducto;
import com.smarvel.springboot.backend.repository.CompraRepository;
import com.smarvel.springboot.backend.repository.PrecioProductoRepository;
import com.smarvel.springboot.backend.service.IPrecioProductoService;

@Service
public class PrecioProductoImpl implements IPrecioProductoService{
	
	@Autowired
	PrecioProductoRepository precioProductoRepository;
	@Autowired
	CompraRepository compraRepository;

	public PrecioProducto buscarPorProductoSupermercadoYFecha(Long psId, LocalDate fecha) {
        return precioProductoRepository.findByProductoSupermercadoIdAndFecha(psId, fecha);
	}

	@Override
    public PrecioProducto guardar(PrecioProducto precioProducto) {
        return precioProductoRepository.save(precioProducto);
    }

	@Override
    public List<PrecioProducto> buscarPorProductoYSupermercado(Long productoId, Long supermercadoId) {
        return precioProductoRepository.findByProductoSupermercadoProductoIdAndProductoSupermercadoSupermercadoId(
                productoId, supermercadoId
        );
    }
	
	@Override
	@Transactional
    public void borrarPrecioProducto(Long id) {
        if (compraRepository.existsByPrecioProductoId(id)) {
            throw new IllegalStateException("No se puede borrar este precio porque existen compras asociadas.");
        }
        precioProductoRepository.deleteById(id);
    }
}
