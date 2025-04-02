package br.com.barbearia.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public class AgendaDto {

    @NotNull(message = "O clienteId é obrigatório.")
    private Long clienteId;

    @NotNull(message = "O barbeiroId é obrigatório.")
    private Long barbeiroId;

    @NotNull(message = "A data e hora são obrigatórias.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataHora;

    private Long agendaId;

    private String clienteNome;

    private String barbeiroNome;

    public AgendaDto(){

    }



    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getBarbeiroNome() {
        return barbeiroNome;
    }

    public void setBarbeiroNome(String barbeiroNome) {
        this.barbeiroNome = barbeiroNome;
    }

    public Long getBarbeiroId() {
        return barbeiroId;
    }

    public void setBarbeiroId(Long barbeiroId) {
        this.barbeiroId = barbeiroId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
