package com.smarvel.springboot.backend.dto;

import java.time.LocalDate;

public class BuscarEntreFechasDTO {
	
	private LocalDate fecha1;
    private LocalDate fecha2;
	public LocalDate getFecha1() {
		return fecha1;
	}
	public void setFecha1(LocalDate fecha1) {
		this.fecha1 = fecha1;
	}
	public LocalDate getFecha2() {
		return fecha2;
	}
	public void setFecha2(LocalDate fecha2) {
		this.fecha2 = fecha2;
	}
    
    

}
