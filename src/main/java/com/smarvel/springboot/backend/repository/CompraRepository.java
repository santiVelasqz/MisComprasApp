package com.smarvel.springboot.backend.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smarvel.springboot.backend.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
	
	
    List<Compra> findByPrecioProducto_ProductoSupermercado_Producto_NombreContainingIgnoreCaseOrPrecioProducto_ProductoSupermercado_Supermercado_NombreContainingIgnoreCase(String nombreProducto, String nombreSupermercado);


    // Buscar por supermercado
    List<Compra> findByPrecioProducto_ProductoSupermercado_Supermercado_Id(Long supermercadoId);


    // Buscar por fecha exacta
    List<Compra> findByFechaCompra(LocalDate fecha);

    // Buscar por rango de fechas
    List<Compra> findByFechaCompraBetween(LocalDate fecha1, LocalDate fecha2);

    // Buscar en un supermercado una fecha concreta
    List<Compra> findByPrecioProducto_ProductoSupermercado_Supermercado_IdAndFechaCompra(
    	    Long supermercadoId, 
    	    LocalDate fecha
    	);


    // Buscar en un supermercado un rango de fechas
    List<Compra> findByPrecioProducto_ProductoSupermercado_Supermercado_IdAndFechaCompraBetween(
    	    Long supermercadoId, 
    	    LocalDate fecha1, 
    	    LocalDate fecha2
    	);


    // ==== Querys para gasto por mes/año ====

 // Suma total de todos los precios pagados en el mes y año dado
    @Query("SELECT SUM(c.precioPagado) FROM Compra c " +
           "WHERE FUNCTION('MONTH', c.fechaCompra) = :mes " +
           "AND FUNCTION('YEAR', c.fechaCompra) = :anio")
    BigDecimal totalGastadoPorMes(@Param("mes") int mes, @Param("anio") int anio);

    // Lista de compras del mes y año dado
    @Query("SELECT c FROM Compra c " +
           "WHERE FUNCTION('MONTH', c.fechaCompra) = :mes " +
           "AND FUNCTION('YEAR', c.fechaCompra) = :anio")
    List<Compra> findByMesYAnio(@Param("mes") int mes, @Param("anio") int anio);

    // Suma total de todos los precios pagados en el año dado
    @Query("SELECT SUM(c.precioPagado) FROM Compra c WHERE FUNCTION('YEAR', c.fechaCompra) = :anio")
    BigDecimal totalGastadoPorAnio(@Param("anio") int anio);

    // Lista de compras del año dado
    @Query("SELECT c FROM Compra c WHERE FUNCTION('YEAR', c.fechaCompra) = :anio")
    List<Compra> findByAnio(@Param("anio") int anio);

    // Suma total por supermercado
    @Query("SELECT SUM(c.precioPagado) " +
           "FROM Compra c " +
           "WHERE c.precioProducto.productoSupermercado.supermercado.id = :supermercadoId")
    BigDecimal totalGastadoPorSupermercado(@Param("supermercadoId") Long supermercadoId);

    // Total por fecha exacta
    @Query("SELECT SUM(c.precioPagado) FROM Compra c WHERE c.fechaCompra = :fecha")
    BigDecimal totalGastadoPorFecha(@Param("fecha") LocalDate fecha);

    // Total entre fechas
    @Query("SELECT SUM(c.precioPagado) FROM Compra c WHERE c.fechaCompra BETWEEN :fecha1 AND :fecha2")
    BigDecimal totalGastadoPorFechas(@Param("fecha1") LocalDate fecha1,
                                     @Param("fecha2") LocalDate fecha2);

    // Total por supermercado en una fecha concreta
    @Query("SELECT SUM(c.precioPagado) " +
           "FROM Compra c " +
           "WHERE c.precioProducto.productoSupermercado.supermercado.id = :supermercadoId " +
           "AND c.fechaCompra = :fecha")
    BigDecimal totalGastadoPorSupermercadoYFecha(@Param("supermercadoId") Long supermercadoId,
                                                 @Param("fecha") LocalDate fecha);

    // Total por supermercado en un rango de fechas
    @Query("SELECT SUM(c.precioPagado) " +
           "FROM Compra c " +
           "WHERE c.precioProducto.productoSupermercado.supermercado.id = :supermercadoId " +
           "AND c.fechaCompra BETWEEN :fecha1 AND :fecha2")
    BigDecimal totalGastadoPorSupermercadoYFechas(@Param("supermercadoId") Long supermercadoId,
                                                  @Param("fecha1") LocalDate fecha1,
                                                  @Param("fecha2") LocalDate fecha2);

    boolean existsByPrecioProductoId(Long precioProductoId);
    
    boolean existsByPrecioProductoProductoSupermercadoProductoId(Long productoId);
    boolean existsByPrecioProductoProductoSupermercadoSupermercadoId(Long supermercadoId);
    boolean existsByPrecioProducto_ProductoSupermercado_Producto_IdAndPrecioProducto_ProductoSupermercado_Supermercado_IdAndFechaCompra(
    	    Long productoId, Long supermercadoId, LocalDate fechaCompra);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Compra c " +
    	       "WHERE c.precioProducto.productoSupermercado.producto.id = :productoId " +
    	       "AND c.precioProducto.productoSupermercado.supermercado.id = :supermercadoId " +
    	       "AND c.fechaCompra = :fechaCompra " +
    	       "AND (:compraId IS NULL OR c.id <> :compraId)")
    	boolean existsByProductoSupermercadoProductoFechaExcludingId(
    	    @Param("productoId") Long productoId,
    	    @Param("supermercadoId") Long supermercadoId,
    	    @Param("fechaCompra") LocalDate fechaCompra,
    	    @Param("compraId") Long compraId);


    
//    @Query("SELECT c.precioProducto.precio FROM Compra c WHERE c.precioProducto.productoSupermercado.id = :psId ORDER BY c.fechaCompra DESC")
//    List<Double> findPreciosMasRecientesByProductoSupermercado(@Param("psId") Long productoSupermercadoId, Pageable pageable);
    
    @Query("SELECT c FROM Compra c WHERE c.precioProducto.productoSupermercado.id = :psId ORDER BY c.fechaCompra DESC")
    List<Compra> findCompraMasRecienteByProductoSupermercado(@Param("psId") Long productoSupermercadoId, Pageable pageable);




}
