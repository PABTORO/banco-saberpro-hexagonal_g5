package co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.notification;

import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.NotificacionPort;

/** Adaptador de notificacion sencillo: escribe en el log. Podria ser correo, cola, etc. */
public class LogNotificacionAdapter implements NotificacionPort {

    private static final System.Logger LOG = System.getLogger(LogNotificacionAdapter.class.getName());

    @Override
    public void notificarEnvioARevision(PreguntaId id) {
        LOG.log(System.Logger.Level.INFO, "Pregunta {0} enviada a revision", id.valor());
    }
}
