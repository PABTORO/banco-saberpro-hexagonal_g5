package co.edu.unicauca.bancosaberpro.domain.valueobject;

import co.edu.unicauca.bancosaberpro.domain.exception.ReglaNegocioException;

/** Value Object inmutable: sustentacion tecnica de la clave (minimo 15 caracteres). */
public record Justificacion(String texto) {
    public static final int LONGITUD_MINIMA = 15;

    public Justificacion {
        if (texto == null || texto.strip().length() < LONGITUD_MINIMA) {
            throw new ReglaNegocioException(
                    "La justificacion debe tener al menos " + LONGITUD_MINIMA + " caracteres");
        }
        texto = texto.strip();
    }
}
