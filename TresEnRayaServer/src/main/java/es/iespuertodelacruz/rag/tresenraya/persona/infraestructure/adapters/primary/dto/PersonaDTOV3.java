package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.dto;

public record PersonaDTOV3(int id, String nombre, String correo, String rol, String token_verificacion, boolean verificado, String fotoNombre) {
}