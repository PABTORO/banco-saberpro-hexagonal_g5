package co.edu.unicauca.bancosaberpro.application;

import co.edu.unicauca.bancosaberpro.domain.exception.PreguntaNoEncontradaException;
import co.edu.unicauca.bancosaberpro.domain.exception.ReglaNegocioException;
import co.edu.unicauca.bancosaberpro.domain.model.EstadoPregunta;
import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaCommand;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaCommand.OpcionCommand;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Misma prueba de integracion, ejecutada con cada adaptador (ver subclases).
 * Es la evidencia del Paso 3 del taller: el comportamiento es identico.
 */
abstract class FlujoPreguntaBaseTest {

    @Autowired
    protected CrearPreguntaUseCase useCase;

    private CrearPreguntaCommand comando(List<OpcionCommand> opciones) {
        return new CrearPreguntaCommand("¿Cual es el patron que desacopla el nucleo de la infraestructura?",
                "Los puertos y adaptadores invierten la dependencia hacia el dominio.", opciones);
    }

    private List<OpcionCommand> opcionesValidas() {
        return List.of(new OpcionCommand("MVC", false), new OpcionCommand("Hexagonal", true),
                new OpcionCommand("Singleton", false), new OpcionCommand("Observer", false));
    }

    @Test
    void creaYEnviaARevision() {
        PreguntaId id = useCase.crearPregunta(comando(opcionesValidas()));
        assertEquals(EstadoPregunta.BORRADOR, useCase.buscarPorId(id).estado());

        Pregunta enviada = useCase.enviarARevision(id);
        assertEquals(EstadoPregunta.PENDIENTE_REVISION, enviada.estado());
        assertEquals(EstadoPregunta.PENDIENTE_REVISION, useCase.buscarPorId(id).estado());
    }

    @Test
    void noEnviaARevisionConOpcionesInvalidas() {
        PreguntaId id = useCase.crearPregunta(comando(List.of(
                new OpcionCommand("A", true), new OpcionCommand("B", false))));
        assertThrows(ReglaNegocioException.class, () -> useCase.enviarARevision(id));
        assertEquals(EstadoPregunta.BORRADOR, useCase.buscarPorId(id).estado());
    }

    @Test
    void lanzaExcepcionSiNoExiste() {
        assertThrows(PreguntaNoEncontradaException.class, () -> useCase.buscarPorId(PreguntaId.nuevo()));
    }
}
