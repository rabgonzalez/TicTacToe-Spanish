package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "personas")
public class PersonaDocument {
	@Field("nombre")
	private String nombre;

	@Field("password")
	private String password;

	@Field("correo")
	private String correo;

	@Field("token_verificacion")
	private String token_verificacion;

	@Field("rol")
	private String rol;

	@Field("verificado")
	private boolean verificado = false;

	@Field("fotoNombre")
	private String fotoNombre;

	public PersonaDocument() {
	}

	public PersonaDocument(String nombre, String password, String correo, String token_verificacion,
			String rol, boolean verificado, String fotoNombre) {
		this.nombre = nombre;
		this.password = password;
		this.correo = correo;
		this.token_verificacion = token_verificacion;
		this.rol = rol;
		this.verificado = verificado;
		this.fotoNombre = fotoNombre;
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
		if (!(o instanceof PersonaDocument)) {
			return false;
		}
		PersonaDocument personaDocument = (PersonaDocument) o;
		return Objects.equals(nombre, personaDocument.nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public String toString() {
		return "{" +
				"nombre='" + getNombre() + "'" +
				", password='" + getPassword() + "'" +
				", correo='" + getCorreo() + "'" +
				", token_verificacion='" + getToken_verificacion() + "'" +
				", rol='" + getRol() + "'" +
				", verificado='" + isVerificado() + "'" +
				", fotoNombre='" + getFotoNombre() + "'" +
				"}";
	}
}
