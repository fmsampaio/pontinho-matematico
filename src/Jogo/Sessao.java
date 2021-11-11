package Jogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Letícia
 */
public class Sessao {

    Integer id_sessao;
    String[] id_jogadores;
    ArrayList<Mao> maos;
    Servidor meu_servidor;
    ClienteServidor jogador_1;
    ClienteServidor jogador_2;
    Mao maoJogador1;
    Mao maoJogador2;
    Baralho baralho;
    ArrayList<Carta> deck = new ArrayList<Carta>();
    Map<Integer, ArrayList<Carta>> baralhos = new HashMap<Integer, ArrayList<Carta>>();
    Map<Integer, int[][]> pontuacaoJogos = new HashMap<Integer, int[][]>();

    Sessao() {

    }

    Sessao(Integer id_sessao, ClienteServidor cliente_1, ClienteServidor cliente_2) {
        this.id_sessao = id_sessao;
        this.jogador_1 = cliente_1;
        this.jogador_2 = cliente_2;
        baralho = new Baralho();
        System.out.println("Baralho Jogo: " + baralho.toString());
        deck = baralho.getBaralho();
        baralhos.put(id_sessao, deck);
    }

    public Mao NovaMao(Integer id_sessao) {
        Mao maoJogador = baralho.novaMao();
        ArrayList<Carta> baralhoJogo = baralhos.get(id_sessao);
        for (int i = 0; i < maoJogador.size(); i++) {
            baralhoJogo.remove(maoJogador.getCardAtPosition(i));
        }
        baralhos.put(id_sessao, deck);
        System.out.println("Baralho depois de gerar mao: " + baralhos.get(id_sessao));
        return maoJogador;
    }

    public String CompraCartaBaralho(int id_sessao) {
        ArrayList<Carta> baralhoJogo = baralhos.get(id_sessao);
        Carta cartaSorteada = new Carta();
        if (baralhoJogo.isEmpty() == true) { //verifica se acabou o baralho
            baralho = new Baralho();
            deck = baralho.getBaralho();
            baralhos.put(id_sessao, deck);
            baralhoJogo = baralhos.get(id_sessao);
        }
        cartaSorteada = baralhoJogo.get(0);
        baralhoJogo.remove(0);
        baralhos.put(id_sessao, baralhoJogo);
        return cartaSorteada.getNome();
    }

    void SetNovaMao(Mao nova_mao) {
        maos.add(nova_mao);
    }

    public String[] GetPontuacaoRodada(Integer id_sessao, int id_jogador1, int pontuacaoJogador1, int id_jogador2, int pontuacaoJogador2) {
        int pontuacaoJogador_um = 0;
        int pontuacaoJogador_dois = 0;
        if (pontuacaoJogos.get(id_sessao) != null) {
            int[][] pontuacaoAnteriorJogadores = pontuacaoJogos.get(id_sessao);
            for (int i = 0; i < 2; i++) {
                if (pontuacaoAnteriorJogadores[i][0] == id_jogador1) {
                    pontuacaoJogador_um = pontuacaoAnteriorJogadores[i][1] + pontuacaoJogador1;
                    pontuacaoAnteriorJogadores[i][1] = pontuacaoJogador_um;
                } else {
                    pontuacaoJogador_dois = pontuacaoAnteriorJogadores[i][1] + pontuacaoJogador2;
                    pontuacaoAnteriorJogadores[i][1] = pontuacaoJogador_dois;
                }
            }
            pontuacaoJogos.put(id_sessao, pontuacaoAnteriorJogadores);
        } else {
            int[][] pontuacaoJogadores = new int[2][2];
            pontuacaoJogadores[0][0] = id_jogador1;
            pontuacaoJogadores[0][1] = pontuacaoJogador1;
            pontuacaoJogadores[1][0] = id_jogador2;
            pontuacaoJogadores[1][1] = pontuacaoJogador2;
            pontuacaoJogador_um = pontuacaoJogador1;
            pontuacaoJogador_dois = pontuacaoJogador2;
            pontuacaoJogos.put(id_sessao, pontuacaoJogadores);
        }
        String[] pontuacaoTotalJogadores = new String[2];
        pontuacaoTotalJogadores[0] = Integer.toString(pontuacaoJogador_um);
        pontuacaoTotalJogadores[1] = Integer.toString(pontuacaoJogador_dois);
        return pontuacaoTotalJogadores;
    }

    public String getId_sessao() {
        return Integer.toString(id_sessao);
    }

    public void getNovoBaralho(int id_sessao) {
        deck = baralho.limpaBaralho(baralho, deck);
        baralho = null;
        baralhos.put(id_sessao, deck);
        baralho = new Baralho();
        deck = baralho.getBaralho();
        baralhos.put(id_sessao, deck);
    }

    public void setId_sessao(Integer id_sessao) {
        this.id_sessao = id_sessao;
    }

    public String[] getId_jogadores() {
        return id_jogadores;
    }

    public void setId_jogadores(String[] id_jogadores) {
        this.id_jogadores = id_jogadores;
    }

    public ArrayList<Mao> getMaos() {
        return maos;
    }

    public void setMaos(ArrayList<Mao> maos) {
        this.maos = maos;
    }

    public ClienteServidor getJogador_1() {
        return jogador_1;
    }

    public void setJogador_1(ClienteServidor jogador_1) {
        this.jogador_1 = jogador_1;
    }

    public ClienteServidor getJogador_2() {
        return jogador_2;
    }

    public void setJogador_2(ClienteServidor jogador_2) {
        this.jogador_2 = jogador_2;
    }

}
