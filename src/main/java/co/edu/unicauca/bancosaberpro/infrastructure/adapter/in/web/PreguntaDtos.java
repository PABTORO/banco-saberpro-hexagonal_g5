package co.edu.unicauca.bancosaberpro.infrastructure.adapter.in.web;

import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaCommand;

import java.util.List;

/** DTOs del adaptador REST. */
public final class PreguntaDtos {

    private PreguntaDtos() { }

    public record OpcionRequest(String texto, boolean esCorrecta) { }

    public record CrearPreguntaRequest(String enunciado, String justificacion, List<OpcionRequest> opciones) {
        public CrearPreguntaCommand aComando() {
            List<CrearPreguntaCommand.OpcionCommand> ops = opciones == null ? null
                    : opciones.stream()
                            .map(o -> new CrearPreguntaCommand.OpcionCommand(o.texto(), o.esCorrecta()))
                            .toList();
            return new CrearPreguntaCommand(enunciado, justificacion, ops);
        }
    }

    public record OpcionResponse(String texto, boolean esCorrecta) { }

    public record PreguntaResponse(String id, String enunciado, String justificacion,
                                   String estado, List<OpcionResponse> opciones) {
        public static PreguntaResponse desde(Pregunta p) {
            return new PreguntaResponse(
                    p.id().valor().toString(),
                    p.enunciado().texto(),
                    p.justificacion().texto(),
                    p.estado().name(),
                    p.opciones().stream().map(o -> new OpcionResponse(o.texto(), o.esCorrecta())).toList());
        }
    }
}
