package co.edu.unicauca.bancosaberpro.domain.model;

import java.util.Objects;
import java.util.UUID;

/** Identidad fuertemente tipada de la Pregunta. */
public record PreguntaId(UUID valor) {
    public PreguntaId {
        Objects.requireNonNull(valor, "El id no puede ser nulo");
    }

    public static PreguntaId nuevo() {
        return new PreguntaId(UUID.randomUUID());
    }

    public static PreguntaId de(String texto) {
        return new PreguntaId(UUID.fromString(texto));
    }
}
