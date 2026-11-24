package co.edu.unicauca.bancosaberpro.domain.ports.inbound;

import java.util.List;

/** Comando de entrada del caso de uso (tipos primitivos, sin dependencias de framework). */
public record CrearPreguntaCommand(String enunciado, String justificacion, List<OpcionCommand> opciones) {
    public record OpcionCommand(String texto, boolean esCorrecta) { }
}
