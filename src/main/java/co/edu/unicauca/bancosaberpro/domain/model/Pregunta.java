package co.edu.unicauca.bancosaberpro.domain.model;

import co.edu.unicauca.bancosaberpro.domain.exception.ReglaNegocioException;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Justificacion;
import co.edu.unicauca.bancosaberpro.domain.valueobject.OpcionRespuesta;

import java.util.List;
import java.util.Objects;

/**
 * Raiz del Agregado Pregunta.
 * Encapsula enunciado, justificacion y opciones, y protege las invariantes:
 * exactamente 4 opciones y exactamente 1 clave correcta antes de pasar a PENDIENTE_REVISION.
 */
public final class Pregunta {

    public static final int NUMERO_OPCIONES = 4;

    private final PreguntaId id;
    private final Enunciado enunciado;
    private final Justificacion justificacion;
    private final List<OpcionRespuesta> opciones;
    private EstadoPregunta estado;

    private Pregunta(PreguntaId id, Enunciado enunciado, Justificacion justificacion,
                     List<OpcionRespuesta> opciones, EstadoPregunta estado) {
        this.id = Objects.requireNonNull(id);
        this.enunciado = Objects.requireNonNull(enunciado);
        this.justificacion = Objects.requireNonNull(justificacion);
        if (opciones == null) {
            throw new ReglaNegocioException("La lista de opciones no puede ser nula");
        }
        this.opciones = List.copyOf(opciones); // copia defensiva e inmutable
        this.estado = Objects.requireNonNull(estado);
    }

    /** Fabrica: toda pregunta nueva nace en BORRADOR. */
    public static Pregunta crear(Enunciado enunciado, Justificacion justificacion,
                                 List<OpcionRespuesta> opciones) {
        return new Pregunta(PreguntaId.nuevo(), enunciado, justificacion, opciones,
                EstadoPregunta.BORRADOR);
    }

    /** Fabrica para que los adaptadores de persistencia rehidraten el agregado. */
    public static Pregunta reconstruir(PreguntaId id, Enunciado enunciado, Justificacion justificacion,
                                       List<OpcionRespuesta> opciones, EstadoPregunta estado) {
        return new Pregunta(id, enunciado, justificacion, opciones, estado);
    }

    /** BORRADOR -> PENDIENTE_REVISION, solo si se cumplen las invariantes. */
    public void enviarARevision() {
        if (estado != EstadoPregunta.BORRADOR) {
            throw new ReglaNegocioException("Solo una pregunta en BORRADOR puede enviarse a revision");
        }
        validarInvariantes();
        estado = EstadoPregunta.PENDIENTE_REVISION;
    }

    private void validarInvariantes() {
        if (opciones.size() != NUMERO_OPCIONES) {
            throw new ReglaNegocioException(
                    "La pregunta debe tener exactamente " + NUMERO_OPCIONES + " opciones");
        }
        long claves = opciones.stream().filter(OpcionRespuesta::esCorrecta).count();
        if (claves != 1) {
            throw new ReglaNegocioException("La pregunta debe tener exactamente 1 clave correcta");
        }
    }

    public PreguntaId id() { return id; }
    public Enunciado enunciado() { return enunciado; }
    public Justificacion justificacion() { return justificacion; }
    public List<OpcionRespuesta> opciones() { return opciones; }
    public EstadoPregunta estado() { return estado; }
}
