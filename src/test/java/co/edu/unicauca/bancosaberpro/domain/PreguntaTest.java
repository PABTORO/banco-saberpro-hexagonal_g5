package co.edu.unicauca.bancosaberpro.domain;

import co.edu.unicauca.bancosaberpro.domain.exception.ReglaNegocioException;
import co.edu.unicauca.bancosaberpro.domain.model.EstadoPregunta;
import co.edu.unicauca.bancosaberpro.domain.model.Pregunta;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Justificacion;
import co.edu.unicauca.bancosaberpro.domain.valueobject.OpcionRespuesta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Pruebas unitarias puras del dominio: sin Spring, sin base de datos. */
class PreguntaTest {

    static final String JUSTIFICACION_OK = "La opcion B es correcta por la definicion formal.";

    static List<OpcionRespuesta> opcionesValidas() {
        return List.of(new OpcionRespuesta("A", false), new OpcionRespuesta("B", true),
                new OpcionRespuesta("C", false), new OpcionRespuesta("D", false));
    }

    static Pregunta preguntaCon(List<OpcionRespuesta> opciones) {
        return Pregunta.crear(new Enunciado("¿Cual es la capital de Colombia?"),
                new Justificacion(JUSTIFICACION_OK), opciones);
    }

    @Test
    @DisplayName("Una pregunta nueva inicia en BORRADOR")
    void iniciaEnBorrador() {
        assertEquals(EstadoPregunta.BORRADOR, preguntaCon(opcionesValidas()).estado());
    }

    @Test
    @DisplayName("Con 4 opciones y 1 clave pasa a PENDIENTE_REVISION")
    void pasaAPendienteRevision() {
        Pregunta p = preguntaCon(opcionesValidas());
        p.enviarARevision();
        assertEquals(EstadoPregunta.PENDIENTE_REVISION, p.estado());
    }

    @Test
    @DisplayName("Rechaza menos de 4 opciones")
    void rechazaMenosDeCuatroOpciones() {
        Pregunta p = preguntaCon(List.of(new OpcionRespuesta("A", true), new OpcionRespuesta("B", false),
                new OpcionRespuesta("C", false)));
        assertThrows(ReglaNegocioException.class, p::enviarARevision);
        assertEquals(EstadoPregunta.BORRADOR, p.estado());
    }

    @Test
    @DisplayName("Rechaza mas de 4 opciones")
    void rechazaMasDeCuatroOpciones() {
        Pregunta p = preguntaCon(List.of(new OpcionRespuesta("A", true), new OpcionRespuesta("B", false),
                new OpcionRespuesta("C", false), new OpcionRespuesta("D", false),
                new OpcionRespuesta("E", false)));
        assertThrows(ReglaNegocioException.class, p::enviarARevision);
    }

    @Test
    @DisplayName("Rechaza cero claves correctas")
    void rechazaSinClave() {
        Pregunta p = preguntaCon(List.of(new OpcionRespuesta("A", false), new OpcionRespuesta("B", false),
                new OpcionRespuesta("C", false), new OpcionRespuesta("D", false)));
        assertThrows(ReglaNegocioException.class, p::enviarARevision);
    }

    @Test
    @DisplayName("Rechaza mas de una clave correcta")
    void rechazaDosClaves() {
        Pregunta p = preguntaCon(List.of(new OpcionRespuesta("A", true), new OpcionRespuesta("B", true),
                new OpcionRespuesta("C", false), new OpcionRespuesta("D", false)));
        assertThrows(ReglaNegocioException.class, p::enviarARevision);
    }

    @Test
    @DisplayName("No se puede enviar a revision dos veces")
    void noSeEnviaDosVeces() {
        Pregunta p = preguntaCon(opcionesValidas());
        p.enviarARevision();
        assertThrows(ReglaNegocioException.class, p::enviarARevision);
    }

    @Test
    @DisplayName("La lista de opciones es inmutable (encapsulamiento)")
    void opcionesInmutables() {
        Pregunta p = preguntaCon(opcionesValidas());
        assertThrows(UnsupportedOperationException.class,
                () -> p.opciones().add(new OpcionRespuesta("E", false)));
    }
}
