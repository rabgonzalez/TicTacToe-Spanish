package es.iespuertodelacruz.rag.tresenraya.persona.domain;

import java.util.Objects;

public class Persona {
    private int id;
	private String nombre;
	private String password;
	private String correo;
	private String token_verificacion;
	private String rol;
	private boolean verificado;
    private String fotoNombre;

    public Persona() {
    }

    public Persona(int id, String nombre, String password, String correo, String token_verificacion, String rol, boolean verificado, String fotoNombre) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.correo = correo;
        this.token_verificacion = token_verificacion;
        this.rol = rol;
        this.verificado = verificado;
        this.fotoNombre = fotoNombre;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getToken_verificacion() {
        return this.token_verificacion;
    }

    public void setToken_verificacion(String token_verificacion) {
        this.token_verificacion = token_verificacion;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isVerificado() {
        return this.verificado;
    }

    public boolean getVerificado() {
        return this.verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getFotoNombre() {
        return this.fotoNombre;
    }

    public void setFotoNombre(String fotoNombre) {
        this.fotoNombre = fotoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Persona)) {
            return false;
        }
        Persona persona = (Persona) o;
        return id == persona.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", password='" + getPassword() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", token_verificacion='" + getToken_verificacion() + "'" +
            ", rol='" + getRol() + "'" +
            ", verificado='" + isVerificado() + "'" +
            ", fotoNombre='" + getFotoNombre() + "'" +
            "}";
    }
}
