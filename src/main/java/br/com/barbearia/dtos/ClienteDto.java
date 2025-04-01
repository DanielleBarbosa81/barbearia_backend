package br.com.barbearia.dtos;

import jakarta.validation.constraints.NotNull;

public class ClienteDto {
    private Long clienteId;

    @NotNull(message = "Nome do cliente não pode ser nulo")
    private String clienteNome;

    private String clienteTelefone;
    private String clienteEmail;


    public ClienteDto() {}

    public ClienteDto(Long clienteId, String clienteNome, String clienteTelefone, String clienteEmail) {
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.clienteTelefone = clienteTelefone;
        this.clienteEmail = clienteEmail;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteTelefone() {
        return clienteTelefone;
    }

    public void setClienteTelefone(String clienteTelefone) {
        this.clienteTelefone = clienteTelefone;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }
}
