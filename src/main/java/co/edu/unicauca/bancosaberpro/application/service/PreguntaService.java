package co.edu.unicauca.bancosaberpro.application.service;

import co.edu.unicauca.bancosaberpro.domain.exception.PreguntaNoEncontradaException;
import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaCommand;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaUseCase;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.NotificacionPort;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Justificacion;
import co.edu.unicauca.bancosaberpro.domain.valueobject.OpcionRespuesta;

import java.util.List;

/** Servicio de aplicacion: orquesta el caso de uso. Sin anotaciones de Spring. */
public class PreguntaService implements CrearPreguntaUseCase {

    private final PreguntaRepositoryPort repositorio;
    private final NotificacionPort notificaciones;

    public PreguntaService(PreguntaRepositoryPort repositorio, NotificacionPort notificaciones) {
        this.repositorio = repositorio;
        this.notificaciones = notificaciones;
    }

    @Override
    public PreguntaId crearPregunta(CrearPreguntaCommand comando) {
        List<OpcionRespuesta> opciones = comando.opciones() == null
                ? List.of()
                : comando.opciones().stream()
                        .map(o -> new OpcionRespuesta(o.texto(), o.esCorrecta()))
                        .toList();
        Pregunta pregunta = Pregunta.crear(
                new Enunciado(comando.enunciado()),
                new Justificacion(comando.justificacion()),
                opciones);
        return repositorio.save(pregunta).id();
    }

    @Override
    public Pregunta enviarARevision(PreguntaId id) {
        Pregunta pregunta = buscarPorId(id);
        pregunta.enviarARevision();
        Pregunta guardada = repositorio.save(pregunta);
        notificaciones.notificarEnvioARevision(guardada.id());
        return guardada;
    }

    @Override
    public Pregunta buscarPorId(PreguntaId id) {
        return repositorio.findById(id).orElseThrow(() -> new PreguntaNoEncontradaException(id));
    }
}
