package co.edu.unicauca.bancosaberpro.infrastructure;

import co.edu.unicauca.bancosaberpro.domain.model.EstadoPregunta;
import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Justificacion;
import co.edu.unicauca.bancosaberpro.domain.valueobject.OpcionRespuesta;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de contrato del puerto: la ejecutan TODOS los adaptadores.
 * Si ambos la pasan, son intercambiables.
 */
public abstract class PreguntaRepositoryContractTest {

    protected abstract PreguntaRepositoryPort repositorio();

    private Pregunta nueva() {
        return Pregunta.crear(new Enunciado("¿Que es un puerto en arquitectura hexagonal?"),
                new Justificacion("Es un contrato que define la interaccion con el nucleo."),
                List.of(new OpcionRespuesta("Una clase de infraestructura", false),
                        new OpcionRespuesta("Una interfaz que define un contrato", true),
                        new OpcionRespuesta("Una tabla de la BD", false),
                        new OpcionRespuesta("Un framework web", false)));
    }

    @Test
    void guardaYRecuperaLaPregunta() {
        Pregunta p = nueva();
        repositorio().save(p);

        Pregunta leida = repositorio().findById(p.id()).orElseThrow();
        assertEquals(p.id(), leida.id());
        assertEquals(p.enunciado(), leida.enunciado());
        assertEquals(p.justificacion(), leida.justificacion());
        assertEquals(p.opciones(), leida.opciones());
        assertEquals(EstadoPregunta.BORRADOR, leida.estado());
    }

    @Test
    void persisteElCambioDeEstado() {
        Pregunta p = nueva();
        repositorio().save(p);
        p.enviarARevision();
        repositorio().save(p);

        assertEquals(EstadoPregunta.PENDIENTE_REVISION,
                repositorio().findById(p.id()).orElseThrow().estado());
    }

    @Test
    void devuelveVacioSiNoExiste() {
        Optional<Pregunta> r = repositorio().findById(PreguntaId.nuevo());
        assertTrue(r.isEmpty());
    }
}
