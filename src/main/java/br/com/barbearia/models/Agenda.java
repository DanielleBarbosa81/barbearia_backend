package br.com.barbearia.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agendaId;

    @ManyToOne
    @JoinColumn(name = "barbeiroId" , nullable = false)
    private Barbeiro barbeiro;

    @ManyToOne
    @JoinColumn(name = "clienteId" , nullable = false)
    private Cliente cliente;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    public Agenda(){

    }
    public Agenda(Long agendaId, Cliente cliente, Barbeiro barbeiro, LocalDateTime dataHora) {
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
