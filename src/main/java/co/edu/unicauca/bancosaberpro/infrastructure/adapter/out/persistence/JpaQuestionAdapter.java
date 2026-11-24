package co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.persistence;

import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Justificacion;
import co.edu.unicauca.bancosaberpro.domain.valueobject.OpcionRespuesta;

import java.util.List;
import java.util.Optional;

/** Adaptador de salida con Spring Data JPA + H2. Traduce dominio <-> entidad JPA. */
public class JpaQuestionAdapter implements PreguntaRepositoryPort {

    private final SpringDataPreguntaRepository repositorio;

    public JpaQuestionAdapter(SpringDataPreguntaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Pregunta save(Pregunta pregunta) {
        return aDominio(repositorio.save(aEntidad(pregunta)));
    }

    @Override
    public Optional<Pregunta> findById(PreguntaId id) {
        return repositorio.findById(id.valor().toString()).map(this::aDominio);
    }

    private PreguntaJpaEntity aEntidad(Pregunta p) {
        List<OpcionJpa> opciones = p.opciones().stream()
                .map(o -> new OpcionJpa(o.texto(), o.esCorrecta()))
                .toList();
        return new PreguntaJpaEntity(p.id().valor().toString(), p.enunciado().texto(),
                p.justificacion().texto(), p.estado(), opciones);
    }

    private Pregunta aDominio(PreguntaJpaEntity e) {
        List<OpcionRespuesta> opciones = e.getOpciones().stream()
                .map(o -> new OpcionRespuesta(o.getTexto(), o.isCorrecta()))
                .toList();
        return Pregunta.reconstruir(PreguntaId.de(e.getId()), new Enunciado(e.getEnunciado()),
                new Justificacion(e.getJustificacion()), opciones, e.getEstado());
    }
}
