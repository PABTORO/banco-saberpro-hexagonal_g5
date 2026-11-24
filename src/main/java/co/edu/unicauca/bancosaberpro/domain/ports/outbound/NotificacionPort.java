package co.edu.unicauca.bancosaberpro.domain.ports.outbound;

import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;

/** Puerto de salida (driven): notificaciones hacia el exterior. */
public interface NotificacionPort {
    void notificarEnvioARevision(PreguntaId id);
}
