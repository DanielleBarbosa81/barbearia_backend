package br.com.barbearia.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_barbeiro")
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long barbeiroId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especialidade;

    public Barbeiro(){

    }

    public Barbeiro(Long barbeiroId, String nome, String especialidade) {
        this.barbeiroId = barbeiroId;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Long getBarbeiroId() {
        return barbeiroId;
    }

    public void setBarbeiroId(Long id) {
        this.barbeiroId = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
