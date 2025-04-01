package br.com.barbearia.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_barbeiro")
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long barbeiroId;

    @Column(nullable = false)
    private String barbeiroNome;

    @Column(nullable = false)
    private String barbeiroEspecialidade;

    public Barbeiro(){

    }

    public Barbeiro(Long barbeiroId, String barbeiroNome, String barbeiroEspecialidade) {
        this.barbeiroId = barbeiroId;
        this.barbeiroNome = barbeiroNome;
        this.barbeiroEspecialidade = barbeiroEspecialidade;
    }

    public Long getBarbeiroId() {
        return barbeiroId;
    }

    public void setBarbeiroId(Long barbeiroId) {
        this.barbeiroId = barbeiroId;
    }

    public String getBarbeiroNome() {
        return barbeiroNome;
    }

    public void setBarbeiroNome(String barbeiroNome) {
        this.barbeiroNome = barbeiroNome;
    }

    public String getBarbeiroEspecialidade() {
        return barbeiroEspecialidade;
    }

    public void setBarbeiroEspecialidade(String barbeiroEspecialidade) {
        this.barbeiroEspecialidade = barbeiroEspecialidade;
    }
}
