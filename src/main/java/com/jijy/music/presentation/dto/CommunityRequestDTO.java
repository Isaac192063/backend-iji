package com.jijy.music.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min; // Para validar valores mínimos en números
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List; // Si los tags son una lista de strings

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityRequestDTO {

    // El ID no se incluye aquí porque se autogenera en la creación y se pasa por @PathVariable en el PUT
    // private String id; // No incluido en el DTO de request

    @NotBlank(message = "El nombre de la comunidad no puede estar vacío.")
    @Size(min = 3, max = 100, message = "El nombre de la comunidad debe tener entre 3 y 100 caracteres.")
    private String name;

    @NotBlank(message = "La descripción de la comunidad no puede estar vacía.")
    @Size(min = 10, max = 500, message = "La descripción de la comunidad debe tener entre 10 y 500 caracteres.")
    private String description;

    @NotNull(message = "Debe especificar si la comunidad es privada.")
    private Boolean isPrivateCommunity; // Usar el mismo nombre que en CommunityEntity para Lombok

    // Opcional: Validar la lista de tags
    // @NotNull(message = "La lista de tags no puede ser nula.")
    // @Size(min = 1, message = "La comunidad debe tener al menos un tag.") // Si al menos un tag es obligatorio
    private List<String> tags; // Asumo que son una lista de strings

    @Min(value = 2, message = "El número máximo de miembros debe ser al menos 2.") // Una comunidad necesita al menos 2 miembros
    @NotNull(message = "El número máximo de miembros no puede ser nulo.")
    private Integer maxMembers;

    // Opcional: Reglas, si son importantes para la validación
    // @Size(max = 1000, message = "Las reglas no pueden exceder los 1000 caracteres.")
    private String rules;

    // Si tienes un campo para el "creador" o "propietario" de la comunidad,
    // y lo envías en el request (ej. por ID de usuario), también lo validarías aquí.
    // @NotBlank(message = "El ID del propietario no puede estar vacío.")
    // private String ownerId;
}