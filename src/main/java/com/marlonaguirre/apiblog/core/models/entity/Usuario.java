package com.marlonaguirre.apiblog.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    @NotEmpty(message = "Debe proporcionar un nombre")
    @Column(name = "nombre")
    private String nombre;

    @NotEmpty(message = "Debe proporcionar un apellido")
    @Column(name = "apellido")
    private String apellido;

    @NotEmpty(message = "Debe porporcionar un correo")
    @Column(name = "correo")
    private String correo;

    @NotEmpty(message = "Debe proporcionar un nombre de usuario")
    @Column(name = "usuario")
    private String usuario;

    @Column(name = "estado")
    private int estado = 0;

    @NotEmpty(message = "Debe proporcionar una contraseña")
    @Column(name = "pass")
    private String pass;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol")
    private Rol rol;

    public Usuario(){

    }

    public Usuario(Long id, String nombre, String apellido, String correo, String usuario, int estado, String pass, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.usuario = usuario;
        this.estado = estado;
        this.pass = pass;
        this.rol = rol;

    }

    public Long getId() {
        return this.id;
    }

    public void setUsuarioId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol){
        this.rol = rol;
    }

}
