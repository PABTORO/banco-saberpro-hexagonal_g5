package co.edu.unicauca.bancosaberpro.infrastructure;

import co.edu.unicauca.bancosaberpro.domain.ports.outbound.PreguntaRepositoryPort;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.memory.InMemoryQuestionAdapter;

class InMemoryQuestionAdapterTest extends PreguntaRepositoryContractTest {
    @Override
    protected PreguntaRepositoryPort repositorio() {
        return new InMemoryQuestionAdapter();
    }
}
