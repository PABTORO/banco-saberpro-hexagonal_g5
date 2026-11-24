package co.edu.unicauca.bancosaberpro.infrastructure.config;

import co.edu.unicauca.bancosaberpro.application.service.PreguntaService;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaUseCase;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.NotificacionPort;
import co.edu.unicauca.bancosaberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.memory.InMemoryQuestionAdapter;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.notification.LogNotificacionAdapter;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.persistence.JpaQuestionAdapter;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.persistence.SpringDataPreguntaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Unico lugar donde se "cablea" el hexagono.
 * Cambiar de adaptador = cambiar de perfil (jpa por defecto, "memory" para HashMap).
 */
@Configuration
public class HexagonalConfiguration {

    @Bean
    @Profile("!memory")
    PreguntaRepositoryPort jpaQuestionAdapter(SpringDataPreguntaRepository repositorio) {
        return new JpaQuestionAdapter(repositorio);
    }

    @Bean
    @Profile("memory")
    PreguntaRepositoryPort inMemoryQuestionAdapter() {
        return new InMemoryQuestionAdapter();
    }

    @Bean
    NotificacionPort notificacionPort() {
        return new LogNotificacionAdapter();
    }

    @Bean
    CrearPreguntaUseCase crearPreguntaUseCase(PreguntaRepositoryPort repositorio,
                                              NotificacionPort notificaciones) {
        return new PreguntaService(repositorio, notificaciones);
    }
}
