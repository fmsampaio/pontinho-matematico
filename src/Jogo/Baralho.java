package Jogo;

import java.util.ArrayList;
import java.util.Collections;

public class Baralho {

    private final ArrayList<Carta> baralho = new ArrayList<>(); //ArrayList de 108 cartas

    
    /**
     * Baralho Construtor preenche o ArrayList com as cartas do baralho
     */
    public Baralho() {

        
        for (int cont = 0; cont < 2; cont++) {
            for (int i = 1; i <= 4; i++) {
                baralho.add(new Carta(SolidosGeometricos.CUBO, i));
            }
            for (int i = 1; i <= 2; i++) {
                baralho.add(new Carta(SolidosGeometricos.CORINGA, i));
            }
            for (int i = 1; i <= 4; i++) {
                baralho.add(new Carta(SolidosGeometricos.PARALELEPIPEDO, i));
            }
            for (int i = 1; i <= 4; i++) {
                baralho.add(new Carta(SolidosGeometricos.OCTAEDRO, i));
            }
            for (int i = 1; i <= 4; i++) {
                baralho.add(new Carta(SolidosGeometricos.DODECAEDRO, i));
            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.CILINDRO, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.CONE, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.ESFERA, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.PRISMA_TRIANGULAR, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.PRISMA_HEXAGONAL, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.PIRAMIDE_TRIANGULAR, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.PIRAMIDE_QUADRANGULAR, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.PIRAMIDE_HEXAGONAL, i));
//            }
//            for (int i = 1; i <= 4; i++) {
//                baralho.add(new Carta(SolidosGeometricos.PRISMA_PENTAGONAL, i));
//            }
           
        }

        embaralha();
    }

    /* embaralha o vetor de cartas */
    private void embaralha() {
        Collections.shuffle(baralho);
    }

    public String toString() {
        return baralho.toString();
    }

     public ArrayList<Carta> getBaralho() {
        return baralho;
    }

    public int size() {
        return baralho.size();
    }
    
    public ArrayList<Carta> limpaBaralho(Baralho baralho, ArrayList<Carta> deck){
        this.baralho.clear();
        deck.clear();
        return deck;
    }

    /**
     * Cria uma mão com 9 cartas
     */
    public Mao novaMao() {
        Mao minhaMao = new Mao();
        int index = 0;

        while (index < 9) {
            minhaMao.adiciona(baralho.remove(0));
            index++;
        }

        return minhaMao;
    }
}
