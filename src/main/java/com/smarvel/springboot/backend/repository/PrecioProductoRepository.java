package com.smarvel.springboot.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smarvel.springboot.backend.entities.PrecioProducto;

public interface PrecioProductoRepository extends JpaRepository<PrecioProducto, Long>{

	
	PrecioProducto findByProductoSupermercadoIdAndFecha(Long productoSupermercadoId, LocalDate fecha);
	List<PrecioProducto> findByProductoSupermercadoProductoIdAndProductoSupermercadoSupermercadoId(
    	    Long productoId, Long supermercadoId);
	
	// Último precio (por fecha) para un producto en un supermercado
    PrecioProducto findTopByProductoSupermercadoProductoIdAndProductoSupermercadoSupermercadoIdOrderByFechaDesc(
        Long productoId, Long supermercadoId
    );

    // Todo el histórico de precios (ordenado por fecha)
    List<PrecioProducto> findByProductoSupermercadoProductoIdAndProductoSupermercadoSupermercadoIdOrderByFechaDesc(
        Long productoId, Long supermercadoId
    );

    // Buscar un precio específico
    PrecioProducto findByProductoSupermercadoProductoIdAndProductoSupermercadoSupermercadoIdAndPrecio(
        Long productoId, Long supermercadoId, Double precio
    );

    void deleteByProductoSupermercadoId(Long productoSupermercadoId);
    
    @Query("SELECT p.id FROM PrecioProducto p WHERE p.productoSupermercado.id = :productoSupermercadoId")
    List<Long> findIdsByProductoSupermercadoId(@Param("productoSupermercadoId") Long productoSupermercadoId);

}
