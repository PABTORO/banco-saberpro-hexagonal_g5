package co.edu.unicauca.bancosaberpro.domain.exception;

import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;

public class PreguntaNoEncontradaException extends RuntimeException {
    public PreguntaNoEncontradaException(PreguntaId id) {
        super("No existe una pregunta con id " + id.valor());
    }
}
