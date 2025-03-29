package br.com.barbearia.dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AgendaDto {


        @NotNull
        private Long clienteId;

        @NotNull
        private Long barbeiroId;

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime dataHora;

    public AgendaDto(){

    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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
