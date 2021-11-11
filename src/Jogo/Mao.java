package Jogo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author Letícia
 */
public class Mao {

    private final ArrayList<Carta> mao; // arrayList de 9 cartas
    Carta carta = new Carta();

    public Mao() {
        mao = new ArrayList<>();
    }

    public void adiciona(Carta card) { //Adiciona um objeto carta na mão
        int i = mao.size();
        if (i < 10) {
            mao.add(i, card);
        }
    }

    public void adicionaNaPosicao(Carta card, int position) { //Adiciona um objeto carta na mão
        mao.add(position, card);
    }

    public void removeCartaMao(String nomeCarta, Mao mao) { //Remove uma carta especifica da mão
        for (int i = 0; i < mao.size(); i++) {
            if (mao.getCardAtPosition(i).getNome().equals(nomeCarta)) {
                mao.remove(i);
                i = mao.size();
            }
        }
    }

    public void trocaCoringaPorCarta(Carta carta, Mao mao) {
        for (int i = 0; i < mao.size(); i++) {
            if (mao.getCardAtPosition(i).getNome().equals("Propriedade 0 do Coringa") || mao.getCardAtPosition(i).getNome().equals("Propriedade 1 do Coringa")) {
                mao.adicionaNaPosicao(carta, i);
                i = mao.size();
            }
        }
    }

    public Carta remove(int position) { // remove uma carta específica na mão baseada na posição dela
        Carta returnCard = mao.get(position);
        mao.remove(returnCard);
        return returnCard;
    }

    public Carta getCardAtPosition(int position) {
        return mao.get(position);
    }

    public String toString() { //
        return mao.toString();
    }

    public boolean contains(Carta c) {
        for (Carta aHand : mao) {
            if (aHand.getNumero() == c.getNumero() && aHand.getSolido() == c.getSolido()) {
                return true;
            }
        }
        return false;
    }

    public int calculaPontos(Mao maoJogador) {
        int pontuacao = 0;
        for (int i = 0; i < maoJogador.size(); i++) {
            String[] DivideNomeCarta = maoJogador.getCardAtPosition(i).getNome().split(" do ");
            if (maoJogador.getCardAtPosition(i).getNome().equals("Propriedade 0 do Coringa") || maoJogador.getCardAtPosition(i).getNome().equals("Propriedade 1 do Coringa")) {
                pontuacao = pontuacao + 15;
            } else {
                if (DivideNomeCarta[0].equals("Propriedade 1") || DivideNomeCarta[0].equals("Propriedade 2") || DivideNomeCarta[0].equals("Propriedade 3")) {
                    pontuacao = pontuacao + 5;
                }
                if (DivideNomeCarta[0].equals("Propriedade 0")) {
                    pontuacao = pontuacao + 10;
                }
            }

        }
        return pontuacao;
    }

    public int size() {
        return mao.size();
    }

}
