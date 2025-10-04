package com.smarvel.springboot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarvel.springboot.backend.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	List<Producto> findByNombreContainingIgnoreCase(String nombre);
	List<Producto> findByNombreContainingIgnoreCaseOrMarca_NombreContainingIgnoreCaseOrCategoria_NombreContainingIgnoreCase(
		    String nombre, String marca, String categoria);
	boolean existsByMarcaId(Long marcaId);
	boolean existsByCategoriaId(Long Categoria);



}
