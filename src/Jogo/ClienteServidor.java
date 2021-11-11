package Jogo;


import java.io.PrintStream;

public class ClienteServidor {

    String login;
    int id_cliente;
    PrintStream minha_impressora;
    String nome_oponente;
    int id_oponente;
    PrintStream impressora_oponente;
    int ativo;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getLoginId() {
        return id_cliente;
    }

    public void setLoginId(int loginId) {
        this.id_cliente = loginId;
    }

    public PrintStream getLoginPS() {
        return minha_impressora;
    }

    public void setLoginPS(PrintStream loginPS) {
        this.minha_impressora = loginPS;
    }

    public String getOponente() {
        return nome_oponente;
    }

    public void setOponente(String oponente) {
        this.nome_oponente = oponente;
    }

    public int getOponenteId() {
        return id_oponente;
    }

    public void setOponenteId(int oponenteId) {
        this.id_oponente = oponenteId;
    }

    public PrintStream getOponentePS() {
        return impressora_oponente;
    }

    public void setOponentePS(PrintStream oponentePS) {
        this.impressora_oponente = oponentePS;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
}
