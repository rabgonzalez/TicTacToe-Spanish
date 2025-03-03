package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity(name = "personas")
public class PersonaEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false, length = 45)
	private String nombre;

	@Column(nullable=false, length=200)
	private String password;

	@Column(unique = true, nullable=false, length=100)
	private String correo;

	@Column(name= "token_verificacion", length=100)
	private String token_verificacion;

	@Column(nullable=false, length=45)
	private String rol;

	@Column(length = 1)
	private boolean verificado = false;

    @Column(name= "foto_nombre", length=100)
    private String fotoNombre;

    public PersonaEntity() {
    }

    public PersonaEntity(int id, String nombre, String password, String correo, String token_verificacion, String rol, boolean verificado, String fotoNombre) {
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
        if (!(o instanceof PersonaEntity)) {
            return false;
        }
        PersonaEntity personaEntity = (PersonaEntity) o;
        return id == personaEntity.id && Objects.equals(nombre, personaEntity.nombre) && Objects.equals(password, personaEntity.password) && Objects.equals(correo, personaEntity.correo) && Objects.equals(token_verificacion, personaEntity.token_verificacion) && Objects.equals(rol, personaEntity.rol) && verificado == personaEntity.verificado && Objects.equals(fotoNombre, personaEntity.fotoNombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, password, correo, token_verificacion, rol, verificado, fotoNombre);
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
