package com.cruz.denunciasapp.Models;

/**
 * Created by FERNANDO on 16/11/2017.
 */

public class Denuncia {

    private Integer id;
    private String titulo;
    private String comentario;
    private float lat;
    private float lon;
    private String imagen;
    private String estado;

    public Denuncia() {
    }

    public Denuncia(Integer id, String titulo, String comentario, float lat, float lon, String imagen, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.comentario = comentario;
        this.lat = lat;
        this.lon = lon;
        this.imagen = imagen;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", comentario='" + comentario + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", imagen='" + imagen + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
