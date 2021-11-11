package Jogo;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Carta {

    SolidosGeometricos solido; // o sólido que a carta se refere 
    private int propriedade; // a propriedade do sólido
    private ImageIcon imagem; // a imagem da carta de acordo com o solido e a propriedade


    public Carta(SolidosGeometricos solido, int numero) {
        this.solido = solido;
        this.propriedade = numero;
        setImagem();
    }

    public Carta() {

    }

    public Carta getCarta(String carta) {

        String[] NomeCarta = carta.split(" do ");

        int Numero = 0;
        SolidosGeometricos solidoGeometrico = null;

        switch (NomeCarta[0]) {
            case "Propriedade 0":
                Numero = 1;
                break;
            case "Propriedade 1":
                Numero = 2;
                break;
            case "Propriedade 2":
                Numero = 3;
                break;
            case "Propriedade 3":
                Numero = 4;
                break;
        }

        switch (NomeCarta[1]) {
            case "Cubo":
                solidoGeometrico = solidoGeometrico.CUBO;
                break;
            case "Paralelepipedo":
                solidoGeometrico = solidoGeometrico.PARALELEPIPEDO;
                break;
            case "Octaedro":
                solidoGeometrico = solidoGeometrico.OCTAEDRO;
                break;
            case "Dodecaedro":
                solidoGeometrico = solidoGeometrico.DODECAEDRO;
                break;
            case "Cilindro":
                solidoGeometrico = solidoGeometrico.CILINDRO;
                break;
            case "Cone":
                solidoGeometrico = solidoGeometrico.CONE;
                break;
            case "Esfera":
                solidoGeometrico = solidoGeometrico.ESFERA;
                break;
            case "Prisma_Triangular":
                solidoGeometrico = solidoGeometrico.PRISMA_TRIANGULAR;
                break;
            case "Prisma_Hexagonal":
                solidoGeometrico = solidoGeometrico.PRISMA_HEXAGONAL;
                break;
            case "Piramide_Triangular":
                solidoGeometrico = solidoGeometrico.PIRAMIDE_TRIANGULAR;
                break;
            case "Piramide_Quadrangular":
                solidoGeometrico = solidoGeometrico.PIRAMIDE_QUADRANGULAR;
                break;
            case "Piramide_Hexagonal":
                solidoGeometrico = solidoGeometrico.PIRAMIDE_HEXAGONAL;
                break;
            case "Prisma_Pentagonal":
                solidoGeometrico = solidoGeometrico.PRISMA_PENTAGONAL;
                break;
            case "Coringa":
                solidoGeometrico = solidoGeometrico.CORINGA;
                break;
        }
        Carta cartinha = new Carta(solidoGeometrico, Numero);
        return cartinha;
    }

    public SolidosGeometricos getSolido() {
        return solido;
    }

    public int getNumero() {
        return propriedade;
    }

    public String getNome() {
        String cardName = "";

        switch (this.getNumero()) {
            case 1:
                cardName += "Propriedade 0 do ";
                break;
            case 2:
                cardName += "Propriedade 1 do ";
                break;
            case 3:
                cardName += "Propriedade 2 do ";
                break;
            case 4:
                cardName += "Propriedade 3 do ";
                break;

        }

        switch (this.getSolido()) {
            case CUBO:
                cardName += "Cubo";
                break;
            case PARALELEPIPEDO:
                cardName += "Paralelepipedo";
                break;
            case OCTAEDRO:
                cardName += "Octaedro";
                break;
            case DODECAEDRO:
                cardName += "Dodecaedro";
                break;
            case CILINDRO:
                cardName += "Cilindro";
                break;
            case CONE:
                cardName += "Cone";
                break;
            case ESFERA:
                cardName += "Esfera";
                break;
            case PRISMA_TRIANGULAR:
                cardName += "Prisma_Triangular";
                break;
            case PRISMA_HEXAGONAL:
                cardName += "Prisma_Hexagonal";
                break;
            case PIRAMIDE_TRIANGULAR:
                cardName += "Piramide_Triangular";
                break;
            case PIRAMIDE_QUADRANGULAR:
                cardName += "Piramide_Quadrangular";
                break;
            case PIRAMIDE_HEXAGONAL:
                cardName += "Piramide_Hexagonal";
                break;
            case PRISMA_PENTAGONAL:
                cardName += "Prisma_Pentagonal";
                break;
            case CORINGA:
                cardName += "Coringa";
                break;
            default:
                break;
        }

        return cardName;
    }

    public ImageIcon getImagem() {
        return imagem;
    }

    private void setImagem() {

        String cardDef = "/Cartas/";

        if (null != solido) {
            switch (solido) {
                case CUBO:
                    cardDef += "Cubo";
                    break;
                case PARALELEPIPEDO:
                    cardDef += "Paralelepipedo";
                    break;
                case OCTAEDRO:
                    cardDef += "Octaedro";
                    break;
                case DODECAEDRO:
                    cardDef += "Dodecaedro";
                    break;
                case CILINDRO:
                    cardDef += "Cilindro";
                    break;
                case CONE:
                    cardDef += "Cone";
                    break;
                case ESFERA:
                    cardDef += "Esfera";
                    break;
                case PRISMA_TRIANGULAR:
                    cardDef += "Prisma_Triangular";
                    break;
                case PRISMA_HEXAGONAL:
                    cardDef += "Prisma_Hexagonal";
                    break;
                case PIRAMIDE_TRIANGULAR:
                    cardDef += "Piramide_Triangular";
                    break;
                case PIRAMIDE_QUADRANGULAR:
                    cardDef += "Piramide_Quadrangular";
                    break;
                case PIRAMIDE_HEXAGONAL:
                    cardDef += "Piramide_Hexagonal";
                    break;
                case PRISMA_PENTAGONAL:
                    cardDef += "Prisma_Pentagonal";
                    break;
                case CORINGA:
                    cardDef += "Coringa";
                    break;
                default:
                    break;
            }
        }
        switch (propriedade) {
            case 1:
                cardDef += "_propriedade0.png";
                break;
            case 2:
                cardDef += "_propriedade1.png";
                break;
            case 3:
                cardDef += "_propriedade2.png";
                break;
            case 4:
                cardDef += "_propriedade3.png";
                break;
            default:
                break;
        }

        Image img = null;

        try {
            img = ImageIO.read(getClass().getResource(cardDef));
        } catch (IOException ex) {
            Logger.getLogger(Carta.class.getName()).log(Level.SEVERE, null, ex);
        }

        imagem = new ImageIcon(img);
    }

    public String toString() {
        return getNome();
    }

}
