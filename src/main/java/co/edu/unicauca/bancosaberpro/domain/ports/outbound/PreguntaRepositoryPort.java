package co.edu.unicauca.bancosaberpro.domain.ports.outbound;

import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;

import java.util.Optional;

/** Puerto de salida (driven): persistencia del agregado Pregunta. */
public interface PreguntaRepositoryPort {
    Pregunta save(Pregunta pregunta);

    Optional<Pregunta> findById(PreguntaId id);
}
