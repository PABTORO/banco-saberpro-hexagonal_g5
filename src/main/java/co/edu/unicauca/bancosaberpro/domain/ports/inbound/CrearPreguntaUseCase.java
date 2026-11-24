package co.edu.unicauca.bancosaberpro.domain.ports.inbound;

import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;

/** Puerto de entrada (driving): casos de uso de creacion y envio a revision. */
public interface CrearPreguntaUseCase {
    PreguntaId crearPregunta(CrearPreguntaCommand comando);

    Pregunta enviarARevision(PreguntaId id);

    Pregunta buscarPorId(PreguntaId id);
}
