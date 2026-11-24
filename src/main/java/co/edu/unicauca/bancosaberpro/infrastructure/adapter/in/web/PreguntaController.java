package co.edu.unicauca.bancosaberpro.infrastructure.adapter.in.web;

import co.edu.unicauca.bancosaberpro.domain.model.PreguntaId;
import co.edu.unicauca.bancosaberpro.domain.ports.inbound.CrearPreguntaUseCase;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.in.web.PreguntaDtos.CrearPreguntaRequest;
import co.edu.unicauca.bancosaberpro.infrastructure.adapter.in.web.PreguntaDtos.PreguntaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/** Adaptador de entrada (driving): traduce HTTP a llamadas al puerto de entrada. */
@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {

    private final CrearPreguntaUseCase useCase;

    public PreguntaController(CrearPreguntaUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<PreguntaResponse> crear(@RequestBody CrearPreguntaRequest request) {
        PreguntaId id = useCase.crearPregunta(request.aComando());
        PreguntaResponse cuerpo = PreguntaResponse.desde(useCase.buscarPorId(id));
        return ResponseEntity.created(URI.create("/api/preguntas/" + id.valor())).body(cuerpo);
    }

    @PostMapping("/{id}/enviar-revision")
    public PreguntaResponse enviarARevision(@PathVariable String id) {
        return PreguntaResponse.desde(useCase.enviarARevision(PreguntaId.de(id)));
    }

    @GetMapping("/{id}")
    public PreguntaResponse obtener(@PathVariable String id) {
        return PreguntaResponse.desde(useCase.buscarPorId(PreguntaId.de(id)));
    }
}
