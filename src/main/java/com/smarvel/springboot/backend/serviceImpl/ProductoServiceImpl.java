package com.smarvel.springboot.backend.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarvel.springboot.backend.entities.Producto;
import com.smarvel.springboot.backend.repository.ProductoRepository;
import com.smarvel.springboot.backend.repository.ProductoSupermercadoRepository;
import com.smarvel.springboot.backend.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{
	
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
    private ProductoSupermercadoRepository productoSupermercadoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> mostrarProductos() {
		
		return productoRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> searchProducto(String search) {
		return productoRepository.findByNombreContainingIgnoreCaseOrMarca_NombreContainingIgnoreCaseOrCategoria_NombreContainingIgnoreCase(
				search, search, search);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto buscarProductoPorId(Long id) {
		
		return productoRepository.findById(id).orElseThrow(null);
	}

	@Override
	@Transactional
	public Producto guardarProducto(Producto producto) {
		
		return productoRepository.save(producto);
	}

	@Override
	@Transactional
	public void borrarProducto(Long id) {
		boolean tieneSupermercado = productoSupermercadoRepository.existsByProductoId(id);
	    
	    if(tieneSupermercado) {
	        throw new IllegalStateException("No se puede borrar este producto porque está asociado a un supermercado.");
	    }
		productoRepository.deleteById(id);
		
	}
	
	@Override
    @Transactional(readOnly = true)
    public boolean existeProductoEnSupermercado(Long supermercadoId) {
        return productoSupermercadoRepository.existsBySupermercadoId(supermercadoId);
    }

}
