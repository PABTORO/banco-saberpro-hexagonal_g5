package co.edu.unicauca.bancosaberpro.infrastructure;

import co.edu.unicauca.bancosaberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.persistence.JpaQuestionAdapter;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.persistence.SpringDataPreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class JpaQuestionAdapterTest extends PreguntaRepositoryContractTest {

    @Autowired
    private SpringDataPreguntaRepository springData;

    @Override
    protected PreguntaRepositoryPort repositorio() {
        return new JpaQuestionAdapter(springData);
    }
}
