package co.edu.unicauca.bancosaberpro.domain.exception;

/** Se lanza cuando se viola una regla de negocio del dominio. */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
