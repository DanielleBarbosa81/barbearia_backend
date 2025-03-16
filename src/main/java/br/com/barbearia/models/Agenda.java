package br.com.barbearia.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agendaId;

    @ManyToOne
    @JoinColumn(name = "barbeiroId")
    private Barbeiro barbeiro;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;


    private String dataHora;

    public Agenda(){

    }
    public Agenda(Long agendaId, Cliente cliente, Barbeiro barbeiro, String dataHora) {
        this.agendaId = agendaId;
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.dataHora = dataHora;
    }

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long id) {
        this.agendaId = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void
        setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}
