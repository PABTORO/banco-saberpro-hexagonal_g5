package co.edu.unicauca.bancosaberpro.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OpcionJpa {

    @Column(nullable = false, length = 1000)
    private String texto;

    @Column(nullable = false)
    private boolean correcta;

    protected OpcionJpa() { }

    public OpcionJpa(String texto, boolean correcta) {
        this.texto = texto;
        this.correcta = correcta;
    }

    public String getTexto() { return texto; }
    public boolean isCorrecta() { return correcta; }
}
