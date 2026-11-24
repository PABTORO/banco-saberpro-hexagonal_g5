package co.edu.unicauca.bancosaberpro.domain.valueobject;

import co.edu.unicauca.bancosaberpro.domain.exception.ReglaNegocioException;

/** Value Object inmutable: una opcion de respuesta y si es la clave correcta. */
public record OpcionRespuesta(String texto, boolean esCorrecta) {
    public OpcionRespuesta {
        if (texto == null || texto.isBlank()) {
            throw new ReglaNegocioException("El texto de una opcion no puede ser nulo ni vacio");
        }
        texto = texto.strip();
    }
}
