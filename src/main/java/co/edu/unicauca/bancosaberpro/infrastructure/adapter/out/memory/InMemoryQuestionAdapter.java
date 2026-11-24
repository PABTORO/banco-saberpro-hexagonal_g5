package co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.memory;

import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.PreguntaRepositoryPort;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** Adaptador de salida basado en un mapa en memoria (prueba de intercambiabilidad). */
public class InMemoryQuestionAdapter implements PreguntaRepositoryPort {

    private final Map<PreguntaId, Pregunta> almacen = new ConcurrentHashMap<>();

    @Override
    public Pregunta save(Pregunta pregunta) {
        almacen.put(pregunta.id(), copiar(pregunta));
        return pregunta;
    }

    @Override
    public Optional<Pregunta> findById(PreguntaId id) {
        return Optional.ofNullable(almacen.get(id)).map(this::copiar);
    }

    /** Copia para simular persistencia real (evita compartir la misma instancia). */
    private Pregunta copiar(Pregunta p) {
        return Pregunta.reconstruir(p.id(), p.enunciado(), p.justificacion(), p.opciones(), p.estado());
    }
}
