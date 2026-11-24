package co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.persistence;

import co.edu.unicauca.bancosaberpro.domain.model.EstadoPregunta;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/** Entidad JPA (modelo de persistencia). El dominio NO conoce esta clase. */
@Entity
@Table(name = "preguntas")
public class PreguntaJpaEntity {

    @Id
    private String id;

    @Column(nullable = false, length = 2000)
    private String enunciado;

    @Column(nullable = false, length = 2000)
    private String justificacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPregunta estado;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pregunta_opciones", joinColumns = @JoinColumn(name = "pregunta_id"))
    @OrderColumn(name = "orden")
    private List<OpcionJpa> opciones = new ArrayList<>();

    protected PreguntaJpaEntity() { }

    public PreguntaJpaEntity(String id, String enunciado, String justificacion,
                             EstadoPregunta estado, List<OpcionJpa> opciones) {
        this.id = id;
        this.enunciado = enunciado;
        this.justificacion = justificacion;
        this.estado = estado;
        this.opciones = new ArrayList<>(opciones);
    }

    public String getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public String getJustificacion() { return justificacion; }
    public EstadoPregunta getEstado() { return estado; }
    public List<OpcionJpa> getOpciones() { return opciones; }
}
