package com.samachar.pddm.jpg.model;

public class Catalogo {
	private String linkOrigen;
	private String imagen;
	private boolean suscrito;
	private String nombre;
	
	

	public Catalogo(){}
	
	
	
	public Catalogo(String linkOrigen, String imagen, boolean suscrito,
			String nombre) {
		this.linkOrigen = linkOrigen;
		this.imagen = imagen;
		this.suscrito = suscrito;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getLinkOrigen() {
		return linkOrigen;
	}
	public void setLinkOrigen(String linkOrigen) {
		this.linkOrigen = linkOrigen;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public boolean isSuscrito() {
		return suscrito;
	}
	public void setSuscrito(boolean suscrito) {
		this.suscrito = suscrito;
	}
	
	

}
