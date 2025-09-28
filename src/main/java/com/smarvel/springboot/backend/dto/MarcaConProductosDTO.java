package com.smarvel.springboot.backend.dto;

public class MarcaConProductosDTO {
	
	 private Long id;
	    private String nombre;
	    private boolean tieneProductos;

	    public MarcaConProductosDTO(Long id, String nombre, boolean tieneProductos) {
	        this.id = id;
	        this.nombre = nombre;
	        this.tieneProductos = tieneProductos;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public boolean isTieneProductos() {
			return tieneProductos;
		}

		public void setTieneProductos(boolean tieneProductos) {
			this.tieneProductos = tieneProductos;
		}
	    
	    

}
