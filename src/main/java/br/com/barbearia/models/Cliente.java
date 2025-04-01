package br.com.barbearia.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @NotNull
    @Column(name = "clienteNome", nullable = false) // Garante que o nome será persistido no banco
    private String clienteNome;
    private String clienteEmail;
    private String clienteTelefone;

    public Cliente() {
    }

    public Cliente(Long clienteId, String clienteNome, String clienteEmail, String clienteTelefone) {
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.clienteEmail = clienteEmail;
        this.clienteTelefone = clienteTelefone;
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

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getClienteTelefone() {
        return clienteTelefone;
    }

    public void setClienteTelefone(String clienteTelefone) {
        this.clienteTelefone = clienteTelefone;
    }

}