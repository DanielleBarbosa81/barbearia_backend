package br.com.barbearia.dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public class AgendaDto {

    @NotNull
    private String clienteNome;
    private Long clienteId;

    @NotNull
    private String barbeiroNome;
    private Long barbeiroId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataHora;

    public AgendaDto(Long clienteId, Long barbeiroId, LocalDateTime dataHora) {
    }

    public AgendaDto(String clienteNome, Long clienteId, String barbeiroNome, Long barbeiroId, LocalDateTime dataHora) {
        this.clienteNome = clienteNome;
        this.clienteId = clienteId;
        this.barbeiroNome = barbeiroNome;
        this.barbeiroId = barbeiroId;
        this.dataHora = dataHora;
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

