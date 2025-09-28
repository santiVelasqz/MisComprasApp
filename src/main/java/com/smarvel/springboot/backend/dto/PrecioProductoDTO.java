package com.smarvel.springboot.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PrecioProductoDTO {
	
	private Long id;
    private BigDecimal precio;
    private LocalDate fecha;

    public PrecioProductoDTO(Long id, BigDecimal precio, LocalDate fecha) {
        this.id = id;
        this.precio = precio;
        this.fecha = fecha;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecio() { return precio; }
    public LocalDate getFecha() { return fecha; }

}
