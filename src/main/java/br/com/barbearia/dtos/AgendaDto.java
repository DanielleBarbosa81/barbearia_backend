package br.com.barbearia.dtos;

public class AgendaDto {

    private Long agendaId;
    private String clienteNome;
    private String barbeiroNome;
    private String dataHora;

    public AgendaDto(){

    }


    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long id) {
        this.agendaId = id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getBarbeiroNome() {
        return barbeiroNome;
    }

    public void setBarbeiroNome(String barbeiroNome) {
        this.barbeiroNome = barbeiroNome;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}
