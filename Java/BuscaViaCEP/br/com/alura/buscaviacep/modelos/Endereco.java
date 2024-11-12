package br.com.alura.buscaviacep.modelos;

public class Endereco {
    private final String cep;
    private final String logradouro;
    private final String complemento;
    private final String bairro;
    private final String cidade;
    private final String uf;
    private final String ibge;
    private final String gia;
    private final String ddd;
    private final String siafi;
    private final Boolean erro;

    public Endereco(EnderecoJson meuEnderecoJson) {

        this.cep = meuEnderecoJson.cep();
        this.logradouro = meuEnderecoJson.logradouro();
        this.complemento = meuEnderecoJson.complemento();
        this.bairro = meuEnderecoJson.bairro();
        this.cidade = meuEnderecoJson.localidade();
        this.uf = meuEnderecoJson.uf();
        this.ibge = meuEnderecoJson.ibge();
        this.gia = meuEnderecoJson.gia();
        this.ddd = meuEnderecoJson.ddd();
        this.siafi = meuEnderecoJson.siafi();
        if (meuEnderecoJson.erro() == null) {
            this.erro = false;
        } else {
            this.erro = meuEnderecoJson.erro();
        }
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public String getBairro() {
        return this.bairro;
    }

    public String getLocalidade() {
        return this.cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public String getIbge() {
        return ibge;
    }

    public String getGia() {
        return gia;
    }

    public String getDdd() {
        return ddd;
    }

    public String getSiafi() {
        return siafi;
    }

    public Boolean getErro() {
        return erro;
    }

    public String toString() {
        return "\nCEP: " + this.getCep() +
                "\nLogradouro: " + this.getLogradouro() +
                "\nComplemento: " + this.getComplemento() +
                "\nBairro: " + getBairro() +
                "\nCidade: " + getLocalidade() +
                "\nUF: " + getUf();

    }
}