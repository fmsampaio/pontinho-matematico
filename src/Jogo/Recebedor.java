package Jogo;

import GUI.TelaAcesso;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Recebedor implements Runnable {

    MesaJogador1 mesaJogador;
    boolean rodadaAcabou = false;
    Carta card = new Carta();
    private InputStream servidor;

    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }

    public void run() {
        Scanner s = new Scanner(servidor);

        while (s.hasNextLine()) {
            String entrada = s.nextLine();
            this.trataEntrada(entrada);
            //servidor.distribuiMensagem(s.nextLine());
        }
    }

    public void trataEntrada(String entrada) {
        System.out.println("" + entrada);
        String[] array = entrada.split(";");
        switch (array[0]) {
            case "logar":
                //Id, loginOponente, idOponente 
                logar(array[1], array[2], array[3], array[4], array[5]);
                break;

            case "jogar":
                jogar(array[1]);
                break;
            case "cartaMonte":
                pescaCartaBaralho(array[1]);
                break;

            case "trinca":
                baixarJogos(array[1], array[2]);
                break;
            case "completaJogo":
                completarJogos(array[1], array[2], array[3]);
                break;

            case "pegouCoringa":
                pegouCoringa(array[1], array[2]);
                break;

            case "naoPegouCoringa":
                naoPegouCoringa(array[1], array[2]);
                break;

            case "pescouDescartada":
                pegouDescartada(array[1]);
                break;
            case "acabouRodada":
                acabouRodada();
                break;
            case "avisaPontuacaoDerrotado":
                AvisaPontuacaoDerrotado(array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9], array[10]);
                break;
            case "avisaPontuacaoVencedor":
                AvisaPontuacaoVencedor(array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9], array[10], array[11]);
                break;
            case "acabouJogo_derrotado":
                AcabouJogoDerrotado(array[1], array[2], array[3], array[4], array[5], array[6]);
                break;
            case "acabouJogo_vencedor":
                AcabouJogoVencedor(array[1], array[2], array[3], array[4], array[5], array[6], array[7]);
                break;
            case "finalizaConexao":
                finalizaJogo();
            default:
                break;
        }

    }

    public void logar(String id, String loginOponente, String idOponente, String id_sessao, String maoJogador) {
        Cliente cliente = Cliente.getInstance();
        cliente.setId(id);
        cliente.setIdOponente(idOponente);
        System.out.println("ClienteLogin: " + loginOponente);
        System.out.println("MeuloginRecebedor: " + cliente.getLogin());

        String[] MaoJogador = maoJogador.split(", ");
        MaoJogador[0] = MaoJogador[0].replace("[", "");
        MaoJogador[MaoJogador.length - 1] = MaoJogador[MaoJogador.length - 1].replace("]", "");

        Mao mao = new Mao();
        for (int i = 0; i < MaoJogador.length; i++) {
            mao.adicionaNaPosicao(card.getCarta(MaoJogador[i]), mao.size());
        }

        mesaJogador = new MesaJogador1(cliente.getLogin(), loginOponente, cliente, id_sessao, mao);
        mesaJogador.setVisible(true);
        mesaJogador.setExtendedState(MAXIMIZED_BOTH);
        int idJogador = Integer.parseInt(cliente.getId());
        int idOpoonente = Integer.parseInt(cliente.getIdOponente());
        if (idJogador < idOpoonente) {
            this.mesaJogador.habilitaBotoes();
        }
    }

    public void jogar(String cartaDescartada) {
        mesaJogador.setCartaDescartada(cartaDescartada);
        this.mesaJogador.habilitaBotoes();
    }

    public void pescaCartaBaralho(String cartaMonte) {
        Carta card = new Carta();
        card = card.getCarta(cartaMonte);
        mesaJogador.setCartaBaralho(card);
    }

    public void baixarJogos(String trincas, String tipos) {
        String[] string = trincas.split(", ");
        String[] tipoJogo = tipos.split(", ");
        string[0] = string[0].replace("[", "");
        string[string.length - 1] = string[string.length - 1].replace("]", "");
        tipoJogo[0] = tipoJogo[0].replace("[", "");
        tipoJogo[tipoJogo.length - 1] = tipoJogo[tipoJogo.length - 1].replace("]", "");

        ArrayList<String> TipoJogos = new ArrayList<String>();
        ArrayList<String> Trincas = new ArrayList<String>();

        for (int i = 0; i < string.length; i++) {
            Trincas.add(i, string[i]);
        }
        for (int i = 0; i < tipoJogo.length; i++) {
            TipoJogos.add(i, tipoJogo[i]);
        }

        mesaJogador.setJogos(Trincas, TipoJogos);
    }

    public void completarJogos(String posicao, String tipos, String NomeCarta) {
        String[] tipoJogo = tipos.split(", ");
        tipoJogo[0] = tipoJogo[0].replace("[", "");
        tipoJogo[tipoJogo.length - 1] = tipoJogo[tipoJogo.length - 1].replace("]", "");
        ArrayList<String> TipoJogos = new ArrayList<String>();
        for (int i = 0; i < tipoJogo.length; i++) {
            TipoJogos.add(i, tipoJogo[i]);
        }

        mesaJogador.CompletaJogos(posicao, TipoJogos, NomeCarta);
    }

    public void pegouDescartada(String carta) {
        mesaJogador.setCartaLixo(carta);
    }

    public void pegouCoringa(String posicao, String carta) {
        mesaJogador.avisaOponentePegouCoringa(posicao, carta);
    }

    public void naoPegouCoringa(String coringa, String posicao) {
        mesaJogador.avisaOponenteNaoPegouCoringa(coringa, Integer.parseInt(posicao));
    }

    public void acabouRodada() {
        rodadaAcabou = true;
        mesaJogador.calculaPontuacaoOponente();
    }

    public void AvisaPontuacaoDerrotado(String nomeJogador, String pontuacaoJogador, String pontRodadaVencedor, String NomeOponente, String pontuacaoOponente, String pontRodadaDerrotado, String novaMao, String id_sessao, String id_jogador, String id_oponente) {
        mesaJogador.desabilitaBotoes();
        mesaJogador.completaPontuacao(nomeJogador, pontuacaoJogador, pontRodadaVencedor, NomeOponente, pontuacaoOponente, pontRodadaDerrotado);
        EsperaUmPouco();
        Cliente cliente = Cliente.getInstance();
        cliente.setId(id_jogador);
        cliente.setIdOponente(id_oponente);
        String[] MaoJogador = novaMao.split(", ");
        MaoJogador[0] = MaoJogador[0].replace("[", "");
        MaoJogador[MaoJogador.length - 1] = MaoJogador[MaoJogador.length - 1].replace("]", "");
        Mao mao = new Mao();
        for (int i = 0; i < MaoJogador.length; i++) {
            mao.adicionaNaPosicao(card.getCarta(MaoJogador[i]), mao.size());
        }
        mesaJogador.dispose();
        mesaJogador = new MesaJogador1(nomeJogador, NomeOponente, cliente, id_sessao, mao);
        mesaJogador.setVisible(true);
        this.mesaJogador.habilitaBotoes();
    }

    public void AvisaPontuacaoVencedor(String nomeJogador, String pontuacaoJogador, String pontRodadaVencedor, String NomeOponente, String pontuacaoOponente, String pontRodadaDerrotado, String maoDerrotado, String novaMao, String id_sessao, String id_jogador, String id_oponente) {
        mesaJogador.desabilitaBotoes();
        mesaJogador.viraCartasDerrotado(maoDerrotado);
        mesaJogador.completaPontuacao(nomeJogador, pontuacaoJogador, pontRodadaVencedor, NomeOponente, pontuacaoOponente, pontRodadaDerrotado);
        EsperaUmPouco();
        Cliente cliente = Cliente.getInstance();
        cliente.setId(id_jogador);
        cliente.setIdOponente(id_oponente);
        mesaJogador.dispose();
        String[] MaoJogador = novaMao.split(", ");
        MaoJogador[0] = MaoJogador[0].replace("[", "");
        MaoJogador[MaoJogador.length - 1] = MaoJogador[MaoJogador.length - 1].replace("]", "");
        Mao mao = new Mao();
        for (int i = 0; i < MaoJogador.length; i++) {
            mao.adicionaNaPosicao(card.getCarta(MaoJogador[i]), mao.size());
        }
        mesaJogador = new MesaJogador1(nomeJogador, NomeOponente, cliente, id_sessao, mao);
        mesaJogador.setVisible(true);
        mesaJogador.desabilitaBotoes();
    }

    public void AcabouJogoVencedor(String nomeJogador, String pontuacaoJogador, String pontRodadaDerrotado, String NomeOponente, String pontuacaoOponente, String pontRodadaVencedor, String maoDerrotado) {
        mesaJogador.desabilitaBotoes();
        mesaJogador.viraCartasDerrotado(maoDerrotado);
        mesaJogador.completaPontuacao(nomeJogador, pontuacaoJogador, pontRodadaDerrotado, NomeOponente, pontuacaoOponente, pontRodadaVencedor);
        EsperaUmPouco();
        mesaJogador.dispose();
        TelaAcesso login = new TelaAcesso();
        login.setVisible(true);
    }

    public void AcabouJogoDerrotado(String nomeJogador, String pontuacaoJogador, String pontRodadaDerrotado, String NomeOponente, String pontuacaoOponente, String pontRodadaVencedor) {
        mesaJogador.desabilitaBotoes();
        mesaJogador.completaPontuacao(nomeJogador, pontuacaoJogador, pontRodadaDerrotado, NomeOponente, pontuacaoOponente, pontRodadaVencedor);
        EsperaUmPouco();
        mesaJogador.dispose();
        TelaAcesso login = new TelaAcesso();
        login.setVisible(true);
    }

    private void finalizaJogo() {
        mesaJogador.finalizaJogo();
    }

    public void EsperaUmPouco() {
        try {
            Thread.sleep(14000);
        } catch (InterruptedException ex) {
            System.out.println("Erro");
        }
    }
}
