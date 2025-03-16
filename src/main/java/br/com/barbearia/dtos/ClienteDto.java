package br.com.barbearia.dtos;

import jakarta.validation.constraints.NotNull;

public class ClienteDto {
    private Long clienteId;

    @NotNull(message = "Nome do cliente não pode ser nulo")
    private String nome;

    private String telefone;
    private String email;

    // Construtor Padrão
    public ClienteDto() {}

    // Getters e Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long id) {
        this.clienteId = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
