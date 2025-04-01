package br.com.barbearia.dtos;

public class BarbeiroDto {

    private Long barbeiroId;
    private String barbeiroNome;
    private String barbeiroEspecialidade;

    public BarbeiroDto() {

    }

    public BarbeiroDto(Long barbeiroId, String barbeiroNome, String barbeiroEspecialidade) {
        this.barbeiroId = barbeiroId;
        this.barbeiroNome = barbeiroNome;
        this.barbeiroEspecialidade = barbeiroEspecialidade;
    }

    public Long getBarbeiroId() {
        return barbeiroId;
    }

    public void setBarbeiroId(Long barbeiroId) {
        this.barbeiroId = barbeiroId;
    }

    public String getBarbeiroNome() {
        return barbeiroNome;
    }

    public void setBarbeiroNome(String barbeiroNome) {
        this.barbeiroNome = barbeiroNome;
    }

    public String getBarbeiroEspecialidade() {
        return barbeiroEspecialidade;
    }

    public void setBarbeiroEspecialidade(String barbeiroEspecialidade) {
        this.barbeiroEspecialidade = barbeiroEspecialidade;
    }
}