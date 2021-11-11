/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jogo;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class Jogo {

   
    Carta CartaDescartada = new Carta();
    ArrayList<Carta> CartasDescartadas = new ArrayList<Carta>();
    Carta UltimaDescartada = new Carta();
   
    public void setCartaDescartada(String Nomecarta) {

        int i = CartasDescartadas.size();
        CartaDescartada = CartaDescartada.getCarta(Nomecarta);
        CartasDescartadas.add(i, CartaDescartada);
    }

    public ArrayList<Carta> getCartasDescartadas() {
        return (CartasDescartadas);
    }

    public int SizeCartasDescartadas() {
        return CartasDescartadas.size();
    }

    public void CartaVoltaAoJogo() {
        CartasDescartadas.remove(CartasDescartadas.size() - 1);
    }

    public Carta PegaUltimaCartaDescartada() {
        UltimaDescartada = CartasDescartadas.get(CartasDescartadas.size() - 1);
        CartasDescartadas.remove(CartasDescartadas.size() - 1);
        return UltimaDescartada;
    }

    public Carta PegaCartaDebaixo() {
        return CartasDescartadas.get(CartasDescartadas.size() - 1);
    }

    public boolean ChecaTrinca(String Carta1, String Carta2, String Carta3) {
      
        String[] carta_um = Carta1.split(" do ");
        String[] carta_dois = Carta2.split(" do ");
        String[] carta_tres = Carta3.split(" do ");

        if ((carta_um[1]).equals(carta_dois[1]) && carta_dois[1].equals(carta_tres[1])) {
            if (carta_um[0].equals(carta_dois[0]) == false && carta_um[0].equals(carta_tres[0]) == false && carta_dois[0].equals(carta_tres[0]) == false) {
                return true;
            }
        }

        if (carta_um[1].equals("Coringa") && carta_dois[1].equals(carta_tres[1]) && carta_dois[0].equals(carta_tres[0]) == false
                || carta_dois[1].equals("Coringa") && carta_um[1].equals(carta_tres[1]) && carta_um[0].equals(carta_tres[0]) == false
                || carta_tres[1].equals("Coringa") && carta_um[1].equals(carta_dois[1]) && carta_dois[0].equals(carta_um[0]) == false) {
            return true;
        }
        return false;
    }

    public boolean ChecaQuarteto(String Carta1, String Carta2, String Carta3, String Carta4) {

        String[] carta_um = Carta1.split(" do ");
        String[] carta_dois = Carta2.split(" do ");
        String[] carta_tres = Carta3.split(" do ");
        String[] carta_quatro = Carta4.split(" do ");

        if ((carta_um[1]).equals(carta_dois[1]) && carta_dois[1].equals(carta_tres[1]) && carta_tres[1].equals(carta_quatro[1])) {
            if (carta_um[0].equals(carta_dois[0]) == false && carta_um[0].equals(carta_tres[0]) == false && carta_dois[0].equals(carta_tres[0]) == false && carta_um[0].equals(carta_quatro[0]) == false && carta_dois[0].equals(carta_quatro[0]) == false && carta_tres[0].equals(carta_quatro[0]) == false) {
                return true;
            }
        }

        if (carta_um[1].equals("Coringa") && carta_dois[1].equals(carta_tres[1]) && carta_tres[1].equals(carta_quatro[1]) && carta_dois[0].equals(carta_tres[0]) == false && carta_dois[0].equals(carta_quatro[0]) == false && carta_tres[0].equals(carta_quatro[0]) == false
                || carta_dois[1].equals("Coringa") && carta_um[1].equals(carta_tres[1]) && carta_tres[1].equals(carta_quatro[1]) && carta_um[0].equals(carta_tres[0]) == false && carta_tres[0].equals(carta_quatro[0]) == false && carta_um[0].equals(carta_quatro[0]) == false
                || carta_tres[1].equals("Coringa") && carta_um[1].equals(carta_dois[1]) && carta_dois[1].equals(carta_quatro[1]) && carta_dois[0].equals(carta_um[0]) == false && carta_um[0].equals(carta_quatro[0]) == false && carta_dois[0].equals(carta_quatro[0]) == false
                || carta_quatro[1].equals("Coringa") && carta_um[1].equals(carta_dois[1]) && carta_dois[1].equals(carta_tres[1]) && carta_dois[0].equals(carta_um[0]) == false && carta_um[0].equals(carta_tres[0]) == false && carta_dois[0].equals(carta_tres[0]) == false) {
            return true;
        }
        return false;
    }

    public boolean CompletaJogo(String Carta1, String Carta2, String Carta3, String Carta4) {
        boolean checaQuarteto = ChecaQuarteto(Carta1, Carta2, Carta3, Carta4);
        return checaQuarteto;
    }

}
