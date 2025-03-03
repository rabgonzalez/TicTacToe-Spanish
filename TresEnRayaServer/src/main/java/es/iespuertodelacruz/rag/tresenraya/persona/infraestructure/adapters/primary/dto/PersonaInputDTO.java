package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.dto;

public record PersonaInputDTO (int id, String nombre, String correo, String password, String rol, String token_verificacion, boolean verificado, String foto, String fotoNombre) {
}