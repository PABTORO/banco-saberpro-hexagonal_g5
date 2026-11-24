package co.edu.unicauca.bancosaberpro.domain;

import co.edu.unicauca.bancosaberpro.domain.exception.ReglaNegocioException;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.bancosaberpro.domain.valueobject.Justificacion;
import co.edu.unicauca.bancosaberpro.domain.valueobject.OpcionRespuesta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void enunciadoNoPuedeSerNuloNiVacio(String texto) {
        assertThrows(ReglaNegocioException.class, () -> new Enunciado(texto));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"muy corta", "exactamente14ch"})
    void justificacionMenorA15Caracteres(String texto) {
        assertThrows(ReglaNegocioException.class, () -> new Justificacion(texto));
    }

    @Test
    void justificacionConExactamente15CaracteresEsValida() {
        assertDoesNotThrow(() -> new Justificacion("123456789012345"));
    }

    @Test
    void opcionNoPuedeTenerTextoVacio() {
        assertThrows(ReglaNegocioException.class, () -> new OpcionRespuesta(" ", true));
    }

    @Test
    void valueObjectsSonIgualesPorValor() {
        assertEquals(new OpcionRespuesta("A", true), new OpcionRespuesta("A", true));
        assertEquals(new Enunciado("Hola"), new Enunciado("Hola"));
    }
}
