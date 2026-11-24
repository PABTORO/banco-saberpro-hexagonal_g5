package co.edu.unicauca.bancosaberpro.domain.valueobject;

import co.edu.unicauca.bancosaberpro.domain.exception.ReglaNegocioException;

/** Value Object inmutable: texto de la pregunta. No puede ser nulo ni vacio. */
public record Enunciado(String texto) {
    public Enunciado {
        if (texto == null || texto.isBlank()) {
            throw new ReglaNegocioException("El enunciado no puede ser nulo ni vacio");
        }
        texto = texto.strip();
    }
}
