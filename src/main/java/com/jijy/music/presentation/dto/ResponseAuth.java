package com.jijy.music.presentation.dto;

// Usando records de Java 16+ para un DTO inmutable y conciso
public record ResponseAuth(String token, String role) {
    // Los records generan automáticamente constructor, getters, equals(), hashCode() y toString()
    // No necesitas añadir nada más aquí a menos que quieras lógica personalizada, lo cual no es el caso para un DTO simple.
}