package co.edu.unicauca.bancosaberpro.application;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/** Perfil "memory": InMemoryQuestionAdapter. Cero cambios en domain. */
@SpringBootTest
@ActiveProfiles("memory")
class FlujoConMemoriaTest extends FlujoPreguntaBaseTest { }
