package Jogo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

public class MesaJogador1 extends javax.swing.JFrame {

    Jogo jogo = new Jogo();
    boolean MousePressionado = false;
    boolean PodeDescartar = false;
    String nomeVariavel;
    Mao mao;
    String nomeJogador = "";
    String nomeOponente = "";
    private Cliente cliente;
    Carta card = new Carta();
    Carta ImgMao = new Carta();
    boolean habilitar;
    boolean tempo;
    int x;
    int y;
    int NumAnteriorCartasBaix = 0;
    boolean BaixarTrinca = false;
    boolean BaixarQuarteto = false;
    boolean PodeCompletar = false;
    boolean PodeCompletarTrinca = false;
    int VerificaTamTipos = 0;
    boolean pegouDescartada = false;
    boolean pegouCoringa = false;
    ArrayList<Carta> cartasDescartadas = new ArrayList<Carta>();
    Carta PegaDescartada = new Carta();
    int[][] GuardaPosicoesCartas = new int[4][2];
    JLabel[] GuardaJLabelCartas = new JLabel[4];
    String[] GuardaNomeCartas = new String[4];
    String[] GuardaPosicaoDosComponentes = new String[4];
    JLabel[] componentesFaltaCarta;
    boolean BaixouJogo = false;
    ArrayList<String> Trincas = new ArrayList<String>();
    ArrayList<String> CartasBaixadas = new ArrayList<String>();
    ArrayList<String> CartasBaixadas2 = new ArrayList<String>();
    JLabel[] componentes = new JLabel[24];
    JLabel[] versos = new JLabel[9];
    JLabel[] cartas = new JLabel[10];
    ArrayList<String> tipo = new ArrayList<String>();
    boolean PegarCoringa = false;
    int somadorGuardaPosicoes = 0;
    boolean pescouCartaMonte = false;
    String[] infCoringa = new String[10];
    int totalTrincas;
    String pontuacao[][] = new String[20][2];
    String id_sessao = null;
    Carta cartaBaralho = new Carta();

    public MesaJogador1(String nomeJogador, String nomeOponente, Cliente cliente, String id_sessao, Mao mao) {
        this.id_sessao = id_sessao;
        this.mao = mao;
        this.nomeJogador = nomeJogador;
        this.nomeOponente = nomeOponente;
        this.cliente = cliente;
        Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int width = d.width - (in.left + in.top);
        int height = d.height - (in.top + in.bottom);
        setSize(width, height);
        setLocation(in.left, in.top);
        initComponents();
        escondeBotoes();
        PanelPontuacao.setVisible(false);
        Button_cartaDescart.setVisible(false);
        dicasUsuario.setVisible(false);
        desabilitaBotoes();
        printaMao();
        this.componentes = new JLabel[]{lblQuarteto1, lblQuarteto2, lblQuarteto3, lblQuarteto4, lblQuarteto5, lblQuarteto6, lblQuarteto7, lblQuarteto8,
            lblQuarteto9, lblQuarteto10, lblQuarteto11, lblQuarteto12, lblQuarteto13, lblQuarteto14, lblQuarteto15, lblQuarteto16, lblQuarteto17, lblQuarteto18, lblQuarteto19, lblQuarteto20, lblQuarteto21, lblQuarteto22, lblQuarteto23, lblQuarteto24};
        this.versos = new JLabel[]{verso9, verso8, verso7, verso6, verso5, verso4, verso3, verso2, verso1};
        this.cartas = new JLabel[]{Carta1, Carta2, Carta3, Carta4, Carta5, Carta6, Carta7, Carta8, Carta9, Carta10};

    }

    public MesaJogador1() {

    }

    public void setCartaDescartada(String cartaDescartada) {
        Carta cartaDescarte = new Carta();
        cartaDescarte = cartaDescarte.getCarta(cartaDescartada);
        jogo.setCartaDescartada(cartaDescartada);
        CartaLixo.setIcon(cartaDescarte.getImagem());
        CartaLixo.setText(" ");
        //  habilitaBotoes();
    }

    public void avisaOponentePegouCoringa(String posicao, String carta) {
        int i = Integer.parseInt(posicao);

        ImageIcon ImgCarta = card.getCarta(carta).getImagem();
        ImgCarta.setImage(ImgCarta.getImage().getScaledInstance(100, 150, 120));
        componentes[i].setName(carta);
        componentes[i].setIcon(ImgCarta);
        Trincas.set(i, carta);
    }

    public void avisaOponenteNaoPegouCoringa(String NomeCoringa, int posicao) {
        componentes[posicao].setName(NomeCoringa);
        Trincas.set(posicao, NomeCoringa);
    }

    public void setCartaLixo(String carta) {
        jogo.CartaVoltaAoJogo();
        if (carta.equals("UnicaCarta")) {
            CartaLixo.setIcon(null);
        } else {
            card = card.getCarta(carta);
            ImageIcon img = card.getImagem();
            CartaLixo.setIcon(img);
        }
    }

    public void setCartaBaralho(Carta cartaMonte) {
        this.cartaBaralho = cartaMonte;
        mao.adiciona(cartaBaralho);
        ImageIcon img = cartaBaralho.getImagem();
        img.setImage(img.getImage().getScaledInstance(100, 150, 120));
        Carta10.setIcon(img);
        Carta10.setName(cartaBaralho.getNome());
        Carta10.setVisible(true);
        mostraBotoes();
        PodeDescartar = true;
    }

    Carta cartaAux = new Carta();
    int contVersos = 0;

    public void setJogos(ArrayList<String> JogosBaixados, ArrayList<String> TipoJogos) {
        int numCartasBaix = JogosBaixados.size() - Trincas.size();
        int NumAnteriorTrincas = Trincas.size();
        for (int i = 0; i < numCartasBaix; i++) {
            if (JogosBaixados.get(NumAnteriorTrincas + i).equals("Falta Carta") == false) {
                componentes[NumAnteriorTrincas + i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
                componentes[NumAnteriorTrincas + i].setName(JogosBaixados.get(NumAnteriorTrincas + i));
                componentes[NumAnteriorTrincas + i].setVisible(true);
                cartaAux = cartaAux.getCarta(JogosBaixados.get(NumAnteriorTrincas + i));
                ImageIcon img = cartaAux.getImagem();
                img.setImage(img.getImage().getScaledInstance(100, 150, 120));
                componentes[NumAnteriorTrincas + i].setIcon(img);
            }
        }
        this.Trincas = JogosBaixados;
        int tamTipos = this.tipo.size();
        this.tipo = TipoJogos;
        int diferenca = tipo.size() - tamTipos;
        for (int i = 0; i < diferenca; i++) {
            if (tipo.get(tamTipos + i).equals("Trinca")) {
                for (int conta = 0; conta < 3; conta++) {
                    if (contVersos < versos.length) {
                        versos[contVersos].setVisible(false);
                        contVersos++;
                    }
                }
            }
            if (tipo.get(tamTipos).equals("Quarteto")) {
                for (int conta = 0; conta < 4; conta++) {
                    if (contVersos < versos.length) {
                        versos[contVersos].setVisible(false);
                        contVersos++;
                    }
                }
            }
        }

    }

    public void DescartaCarta(String cartaDescartada) {
        desabilitaBotoes();
        tempo = false;
        try {
            cliente.enviaDados("jogar;" + cliente.getId() + ";" + cartaDescartada);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void EnviaJogos(ArrayList<String> Trincas, ArrayList<String> tipoJogos, String[] OrdemComponentes) {
        try {
            String trinca = Trincas.toString();
            String tipos = tipoJogos.toString();
            String ordem = Arrays.toString(OrdemComponentes);
            cliente.enviaDados("trinca;" + cliente.getId() + ";" + trinca + ";" + tipos + ";" + ordem);
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void CompletaJogos(String posicaoo, ArrayList<String> tipos, String nomeCarta) {
        int posicao = Integer.parseInt(posicaoo);
        componentes[posicao].setName(nomeCarta);
        Trincas.set(posicao, nomeCarta);
        for (int i = 0; i < tipo.size(); i++) {
            if (tipo.get(i).equals(tipos.get(i)) == false) {
                if (contVersos < versos.length) {
                    versos[contVersos].setVisible(false);
                    contVersos++;
                }
            }
        }
        this.tipo = tipos;
        cartaAux = cartaAux.getCarta(nomeCarta);
        ImageIcon img = cartaAux.getImagem();
        img.setImage(img.getImage().getScaledInstance(100, 150, 120));
        componentes[posicao].setIcon(img);
        componentes[posicao].setVisible(true);
    }

    public void calculaPontuacaoOponente() {
        desabilitaBotoes();
        int pontuacaoJogador = mao.calculaPontos(mao);
        String pontuacaoOponente = Integer.toString(0);
        try {
            cliente.enviaDados("avisaPontuacao;" + cliente.getId() + ";" + Integer.toString(pontuacaoJogador) + ";" + cliente.getIdOponente() + ";" + pontuacaoOponente + ";" + id_sessao + ";" + mao.toString());
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void viraCartasDerrotado(String mao) {
        String[] cartasMao = mao.split(", ");
        cartasMao[0] = cartasMao[0].replace("[", "");
        cartasMao[cartasMao.length - 1] = cartasMao[cartasMao.length - 1].replace("]", "");

        for (int i = 0; i < cartasMao.length; i++) {
            card = card.getCarta(cartasMao[i]);
            ImageIcon img = card.getImagem();
            img.setImage(img.getImage().getScaledInstance(100, 150, 120));
            versos[versos.length - i - 1].setIcon(img);
        }
    }

    public void AvisaPegouDescartada(String NomeCarta) {
        try {
            cliente.enviaDados("pescouDescartada;" + cliente.getId() + ";" + NomeCarta);
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void AvisaAcabouRodada() {
        try {
            cliente.enviaDados("acabouRodada;" + cliente.getId());
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void PescarCartaBaralho() {
        try {
            cliente.enviaDados("cartaMonte;" + cliente.getId() + ";" + id_sessao);
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void AvisaCompletou(String posicao, ArrayList<String> tipoJogos, String NomeCarta) {
        try {
            cliente.enviaDados("completouJogo;" + cliente.getId() + ";" + posicao + ";" + tipoJogos.toString() + ";" + NomeCarta);
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void AvisaPegouCoringa(String posicao, String NomeCarta) {
        try {
            cliente.enviaDados("pegouCoringa;" + cliente.getId() + ";" + posicao + ";" + NomeCarta);
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void AvisaNaoPegouCoringa(String NomeCoringa, String posicao) {
        try {
            cliente.enviaDados("naoPegouCoringa;" + cliente.getId() + ";" + NomeCoringa + ";" + posicao);
        } catch (IOException e) {
            System.out.println("" + e);
            e.printStackTrace();
        }
    }

    public void printaMao() {

        for (int i = 0; i < mao.size(); i++) {
            switch (i) {
                case 0:
                    Carta carta0 = mao.getCardAtPosition(0);
                    Carta1.setIcon(carta0.getImagem());
                    Carta1.setName(carta0.getNome());
                    break;
                case 1:
                    Carta carta1 = mao.getCardAtPosition(1);
                    Carta2.setIcon(carta1.getImagem());
                    Carta2.setName(carta1.getNome());
                    break;
                case 2:
                    Carta carta2 = mao.getCardAtPosition(2);
                    Carta3.setIcon(carta2.getImagem());
                    Carta3.setName(carta2.getNome());
                    break;
                case 3:
                    Carta carta3 = mao.getCardAtPosition(3);
                    Carta4.setIcon(carta3.getImagem());
                    Carta4.setName(carta3.getNome());
                    break;
                case 4:
                    Carta carta4 = mao.getCardAtPosition(4);
                    Carta5.setIcon(carta4.getImagem());
                    Carta5.setName(carta4.getNome());
                    break;
                case 5:
                    Carta carta5 = mao.getCardAtPosition(5);
                    Carta6.setIcon(carta5.getImagem());
                    Carta6.setName(carta5.getNome());
                    break;
                case 6:
                    Carta carta6 = mao.getCardAtPosition(6);
                    Carta7.setIcon(carta6.getImagem());
                    Carta7.setName(carta6.getNome());
                    break;
                case 7:
                    Carta carta7 = mao.getCardAtPosition(7);
                    Carta8.setIcon(carta7.getImagem());
                    Carta8.setName(carta7.getNome());
                    break;
                case 8:
                    Carta carta8 = mao.getCardAtPosition(8);
                    Carta9.setIcon(carta8.getImagem());
                    Carta9.setName(carta8.getNome());
                    break;
            }
        }
    }

    public void completaPontuacao(String nomeJogador, String pontuacaoJogador, String pontRodadaJogador, String NomeOponente, String pontuacaoOponente, String pontRodadaOponente) {
        nomeJog1.setText(nomeJogador);
        nomeJog2.setText(NomeOponente);

        int pontuacaoAnteriorJog1 = Integer.parseInt(pontuacaoJogador) - Integer.parseInt(pontRodadaJogador);
        int pontuacaoAnteriorJog2 = Integer.parseInt(pontuacaoOponente) - Integer.parseInt(pontRodadaOponente);

        pontAnteriorJog1.setText(Integer.toString(pontuacaoAnteriorJog1));
        pontAnteriorJog2.setText(Integer.toString(pontuacaoAnteriorJog2));

        pontRodadaJog1.setText(pontRodadaJogador);
        pontRodadaJog2.setText(pontRodadaOponente);

        TotalJog1.setText(pontuacaoJogador);
        TotalJog2.setText(pontuacaoOponente);
        
        if(Integer.parseInt(pontuacaoJogador) >=100){
            avisaVencedor.setText(NomeOponente + " venceu o jogo!");
        }else if(Integer.parseInt(pontuacaoOponente) >=100){
            avisaVencedor.setText(nomeJogador + " venceu o jogo!");
        }
        
        PanelPontuacao.setVisible(true);
    }

    public void desabilitaBotoes() {
        cartaMonte.setEnabled(false);
        Button_cartaDescart.setEnabled(false);
        btn_quarteto.setEnabled(false);
        btn_trinca.setEnabled(false);
        btn_completar.setEnabled(false);
        btn_coringa.setEnabled(false);
    }

    public void habilitaBotoes() {
        tempo = true;
        cartaMonte.setEnabled(
                true);
        if (CartaLixo.getIcon()
                != null) {
            Button_cartaDescart.setVisible(true);
            Button_cartaDescart.setEnabled(true);
        } else {
            Button_cartaDescart.setVisible(false);
        }
    }

    public void escondeBotoes() {
        btn_trinca.setVisible(false);
        btn_quarteto.setVisible(false);
        btn_completar.setVisible(false);
        btn_coringa.setVisible(false);
        if (BaixarTrinca == true || BaixarQuarteto == true || PegarCoringa == true || PodeCompletar == true) {
            btn_cancelar.setVisible(true);
        } else {
            btn_cancelar.setVisible(false);
        }

    }

    public void mostraBotoes() {
        cartaMonte.setEnabled(false);
        Button_cartaDescart.setEnabled(false);
        btn_cancelar.setVisible(false);

        if (tipo.contains("Trinca")) {
            btn_completar.setVisible(true);
            btn_completar.setEnabled(true);
        } else {
            btn_completar.setVisible(false);
        }

        if (mao.size() >= 3) {
            btn_trinca.setVisible(true);
            btn_trinca.setEnabled(true);
        } else {
            btn_trinca.setVisible(false);
        }
        if (mao.size() >= 4) {
            btn_quarteto.setVisible(true);
            btn_quarteto.setEnabled(true);
        } else {
            btn_quarteto.setVisible(false);
        }

        if (Trincas.contains("Propriedade 0 do Coringa") || Trincas.contains("Propriedade 1 do Coringa")) {
            if (pegouCoringa == false) {
                btn_coringa.setVisible(true);
                btn_coringa.setEnabled(true);
            } else {
                btn_coringa.setVisible(true);
                btn_coringa.setEnabled(false);
            }
        }
    }

    public void mouseReleased(String nomeVariavel, JLabel carta, MouseEvent e) {
        String NomeComponente = nomeVariavel;
        MousePressionado = false;
        habilitar = false;
        int x1 = carta.getX();
        int y2 = carta.getY();
        int auxiliar = 0;
        if (PodeDescartar == true && pegouDescartada == false && pegouCoringa == false) {
            if (x1 <= 210 && x1 >= 160 && y2 <= 275 && y2 >= 200) {
                if ((carta.getName()).equals("Propriedade 0 do Coringa") == false && (carta.getName()).equals("Propriedade 1 do Coringa") == false) {
                    PodeDescartar = false;
                    CartaLixo.setText("");
                    switch (NomeComponente) {
                        case "Carta1":
                            CartaLixo.setIcon(Carta1.getIcon());
                            mao.removeCartaMao(Carta1.getName(), mao);
                            DescartaCarta(Carta1.getName());
                            Carta1.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta1.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta1.setIcon(card.getImagem());
                                Carta1.setName(Carta10.getName());
                            } else {
                                Carta1.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta2":
                            CartaLixo.setIcon(Carta2.getIcon());
                            mao.removeCartaMao(Carta2.getName(), mao);
                            Carta2.setBounds(x, y, 140, 186);
                            DescartaCarta(Carta2.getName());
                            jogo.setCartaDescartada(Carta2.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta2.setIcon(card.getImagem());
                                Carta2.setName(Carta10.getName());
                            } else {
                                Carta2.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta3":
                            CartaLixo.setIcon(Carta3.getIcon());
                            mao.removeCartaMao(Carta3.getName(), mao);
                            Carta3.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta3.getName());
                            DescartaCarta(Carta3.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta3.setIcon(card.getImagem());
                                Carta3.setName(Carta10.getName());
                            } else {
                                Carta3.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta4":
                            CartaLixo.setIcon(Carta4.getIcon());
                            mao.removeCartaMao(Carta4.getName(), mao);
                            Carta4.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta4.getName());
                            DescartaCarta(Carta4.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta4.setIcon(card.getImagem());
                                Carta4.setName(Carta10.getName());
                            } else {
                                Carta4.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta5":
                            CartaLixo.setIcon(Carta5.getIcon());
                            mao.removeCartaMao(Carta5.getName(), mao);
                            Carta5.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta5.getName());
                            DescartaCarta(Carta5.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta5.setIcon(card.getImagem());
                                Carta5.setName(Carta10.getName());
                            } else {
                                Carta5.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta6":
                            CartaLixo.setIcon(Carta6.getIcon());
                            mao.removeCartaMao(Carta6.getName(), mao);
                            Carta6.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta6.getName());
                            DescartaCarta(Carta6.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta6.setIcon(card.getImagem());
                                Carta6.setName(Carta10.getName());
                            } else {
                                Carta6.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta7":
                            CartaLixo.setIcon(Carta7.getIcon());
                            mao.removeCartaMao(Carta7.getName(), mao);
                            Carta7.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta7.getName());
                            DescartaCarta(Carta7.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta7.setIcon(card.getImagem());
                                Carta7.setName(Carta10.getName());
                            } else {
                                Carta7.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta8":
                            CartaLixo.setIcon(Carta8.getIcon());
                            mao.removeCartaMao(Carta8.getName(), mao);
                            Carta8.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta8.getName());
                            DescartaCarta(Carta8.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta8.setIcon(card.getImagem());
                                Carta8.setName(Carta10.getName());
                            } else {
                                Carta8.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta9":
                            CartaLixo.setIcon(Carta9.getIcon());
                            mao.removeCartaMao(Carta9.getName(), mao);
                            Carta9.setBounds(x, y, 140, 186);
                            jogo.setCartaDescartada(Carta9.getName());
                            DescartaCarta(Carta9.getName());
                            if (Carta10.getIcon() != null) {
                                card = card.getCarta(Carta10.getName());
                                Carta9.setIcon(card.getImagem());
                                Carta9.setName(Carta10.getName());
                            } else {
                                Carta9.setVisible(false);
                            }
                            Carta10.setIcon(null);
                            break;
                        case "Carta10":
                            card = card.getCarta(Carta10.getName());
                            mao.removeCartaMao(Carta10.getName(), mao);
                            CartaLixo.setIcon(card.getImagem());
                            Carta10.setIcon(null);
                            Carta10.setBounds(x, y, 100, 150);
                            jogo.setCartaDescartada(Carta10.getName());
                            DescartaCarta(Carta10.getName());
                            break;
                    }
                    escondeBotoes();
                } else {
                    carta.setBounds(x, y, 140, 186);
                    dicasUsuario.setText("O coringa não pode ser descartado");
                    dicasUsuario.setVisible(true);
                    EscondeJLabel();
                }
            } else {
                carta.setBounds(x, y, 140, 186);
            }
        }

        if (PodeDescartar == true && pegouDescartada == true && pegouCoringa == false && BaixarTrinca == false && BaixarQuarteto == false && PodeCompletar == false) {
            if (x1 <= 210 && x1 >= 160 && y2 <= 275 && y2 >= 200) {
                if ((carta.getName()).equals("Propriedade 0 do Coringa") == false && (carta.getName()).equals("Propriedade 1 do Coringa") == false) {
                    pegouDescartada = false;
                    PodeDescartar = false;
                    boolean verifica = VerificaSeBaixouCarta(PegaDescartada);
                    if (verifica == true) {
                        CartaLixo.setIcon(carta.getIcon());
                        mao.removeCartaMao(carta.getName(), mao);
                        carta.setVisible(false);
                        jogo.setCartaDescartada(carta.getName());
                        DescartaCarta(carta.getName());
                        if (Carta10.getIcon() != null) {
                            carta.setBounds(x, y, 140, 186);
                            carta.setName(Carta10.getName());
                            card = card.getCarta(Carta10.getName());
                            carta.setIcon(card.getImagem());
                            carta.setVisible(true);
                            Carta10.setIcon(null);
                            Carta10.setVisible(false);
                        }
                    } else {
                        if (!carta.getName().equals(PegaDescartada.getNome())) {
                            dicasUsuario.setText("Você não baixou a carta pescada do monte de descartes");
                            dicasUsuario.setVisible(true);
                        }
                        Carta10.setIcon(null);
                        card = card.getCarta(PegaDescartada.getNome());
                        CartaLixo.setIcon(card.getImagem());
                        mao.removeCartaMao(card.getNome(), mao);
                        jogo.setCartaDescartada(card.getNome());
                        DescartaCarta(PegaDescartada.getNome());
                        carta.setBounds(x, y, 140, 186);
                        carta.setName(carta.getName());
                        EscondeJLabel();
                    }
                } else {
                    carta.setBounds(x, y, 140, 186);
                    dicasUsuario.setText("Movimento Inválido");
                    dicasUsuario.setVisible(true);
                    EscondeJLabel();
                }
                escondeBotoes();
            } else {
                carta.setBounds(x, y, 140, 186);
            }
        }
        if (pegouCoringa == true && PodeDescartar == true && BaixarTrinca == false && BaixarQuarteto == false && PodeCompletar == false) {
            if (x1 <= 210 && x1 >= 160 && y2 <= 275 && y2 >= 200) {
                if ((carta.getName()).equals("Propriedade 0 do Coringa") == false && (carta.getName()).equals("Propriedade 1 do Coringa") == false) {
                    pegouCoringa = false;
                    PodeDescartar = false;
                    boolean verificaBaixouCarta = false;
                    boolean verificaBaixouCartaNoCoringa = false;

                    boolean verifica = VerificaSeBaixouCoringa();

                    if (pegouDescartada == true) {
                        verificaBaixouCarta = VerificaSeBaixouCarta(PegaDescartada);
                        if (PegaDescartada.getNome().equals(Trincas.get(Integer.parseInt(infCoringa[0])))) { //verifica se baixou descartada no lugar do coringa
                            verificaBaixouCartaNoCoringa = true;
                            if (verifica == false) {
                                verificaBaixouCarta = false;
                            }
                        }
                    }

                    if (verifica == true && pegouDescartada == false || verifica == true && pegouDescartada == true && verificaBaixouCarta == true) { //Pode descartar Normalmente
                        //nao pega descartada e pega coringa com uma carta que já tem na mão
                        //pega descartada, pega coringa com descartada e baixa coringa
                        //pega descartada, pega coringa com uma carta que já tem na mão, baixa jogo com a descartada e baixa jogo com o coringa
                        System.out.println("Entrou no caso 1");
                        AvisaPegouCoringa(infCoringa[0], infCoringa[3]);
                        card = card.getCarta(carta.getName());
                        CartaLixo.setIcon(card.getImagem());
                        mao.removeCartaMao(carta.getName(), mao);
                        DescartaCarta(carta.getName());
                        carta.setBounds(x, y, 140, 186);
                        jogo.setCartaDescartada(carta.getName());
                        if (Carta10.getIcon() != null) {
                            card = card.getCarta(Carta10.getName());
                            carta.setIcon(card.getImagem());
                            carta.setName(Carta10.getName());
                            carta.setVisible(true);
                        } else {
                            carta.setVisible(false);
                        }
                        Carta10.setIcon(null);
                    } else if (verifica == false && pegouDescartada == false || verifica == false && pegouDescartada == true && verificaBaixouCarta == true) { // Coringa volta pro lugar, carta baixada no lugar do coringa volta pra mão 
                        //não pega descartada e rouba coringa com alguma carta da mão e não baixa o coringa
                        // pega descartada, baixa jogo com a descartada;rouba o coringa com uma carta que já tem na mão e não baixa o coringa
                        System.out.println("Entrou no caso 2");
                        AvisaNaoPegouCoringa(infCoringa[2], infCoringa[0]);
                        if (carta.equals(Carta10)) {
                            card = card.getCarta(Carta10.getName());
                            CartaLixo.setIcon(card.getImagem());
                        } else {
                            CartaLixo.setIcon(carta.getIcon());
                        }
                        dicasUsuario.setText("Quando você pega o coringa, deve baixar ele");
                        dicasUsuario.setVisible(true);
                        EscondeJLabel();
                        mao.removeCartaMao(carta.getName(), mao);
                        DescartaCarta(carta.getName());
                        carta.setBounds(x, y, 140, 186);
                        jogo.setCartaDescartada(carta.getName());
                        if (Carta10.getIcon() != null) {
                            card = card.getCarta(Carta10.getName());
                            carta.setIcon(card.getImagem());
                            carta.setName(Carta10.getName());
                            carta.setVisible(true);
                        } else {
                            carta.setVisible(false);
                        }
                        Carta10.setIcon(null);
                        String cartaJogador = Trincas.get(Integer.parseInt(infCoringa[0]));
                        card = card.getCarta(infCoringa[2]);
                        ImageIcon img = card.getImagem();
                        img.setImage(img.getImage().getScaledInstance(100, 150, 120));
                        componentes[Integer.parseInt(infCoringa[0])].setIcon(img);
                        componentes[Integer.parseInt(infCoringa[0])].setName(card.getNome());
                        Trincas.set(Integer.parseInt(infCoringa[0]), card.getNome());
                        card = card.getCarta(cartaJogador);
                        mao.removeCartaMao(infCoringa[2], mao);
                        mao.adicionaNaPosicao(card, mao.size());

                        switch (infCoringa[1]) {
                            case "Carta1":
                                Carta1.setVisible(true);
                                Carta1.setIcon(card.getImagem());
                                Carta1.setName(cartaJogador);
                                break;
                            case "Carta2":
                                Carta2.setVisible(true);
                                Carta2.setIcon(card.getImagem());
                                Carta2.setName(cartaJogador);
                                break;
                            case "Carta3":
                                Carta3.setVisible(true);
                                Carta3.setIcon(card.getImagem());
                                Carta3.setName(cartaJogador);
                                break;
                            case "Carta4":
                                Carta4.setVisible(true);
                                Carta4.setIcon(card.getImagem());
                                Carta4.setName(cartaJogador);
                                break;
                            case "Carta5":
                                Carta5.setVisible(true);
                                Carta5.setIcon(card.getImagem());
                                Carta5.setName(cartaJogador);
                                break;
                            case "Carta6":
                                Carta6.setVisible(true);
                                Carta6.setIcon(card.getImagem());
                                Carta6.setName(cartaJogador);
                                break;
                            case "Carta7":
                                Carta7.setVisible(true);
                                Carta7.setIcon(card.getImagem());
                                Carta7.setName(cartaJogador);
                                break;
                            case "Carta8":
                                Carta8.setVisible(true);
                                Carta8.setIcon(card.getImagem());
                                Carta8.setName(cartaJogador);
                                break;
                            case "Carta9":
                                Carta9.setVisible(true);
                                Carta9.setIcon(card.getImagem());
                                Carta9.setName(cartaJogador);
                                break;
                            case "Carta10":
                                carta.setIcon(card.getImagem());
                                carta.setName(card.getNome());
                                carta.setVisible(true);
                                break;
                            default:
                                break;
                        }
                    } else if (verifica == false && pegouDescartada == true && verificaBaixouCarta == false) {
                        //pega descartada rouba coringa com ela e não baixa o coringa
                        //pega descartada não baixa ela, pega o coringa com carta que já tem na mão e não baixa ele; 
                        System.out.println("Entrou no caso 3");
                        AvisaNaoPegouCoringa(infCoringa[2], infCoringa[0]);
                        //parte do código que descarta o PegaDescartada
                        if (verificaBaixouCartaNoCoringa == true) {
                            if (Carta10.getName().equals(PegaDescartada.getNome())) { //significa que jogador pegou coringa com carta igual a desc
                                verificaBaixouCartaNoCoringa = false;
                            }
                        }
                        card = card.getCarta(PegaDescartada.getNome());
                        CartaLixo.setIcon(card.getImagem());
                        mao.removeCartaMao(infCoringa[2], mao);
                        DescartaCarta(PegaDescartada.getNome());
                        jogo.setCartaDescartada(PegaDescartada.getNome());
                        carta.setBounds(x, y, 140, 186);
                        carta.setName(carta.getName());
                        Carta10.setIcon(null);

                        //parte do código que desfaz o movimento do PegaCoringa
                        String cartaJogador = Trincas.get(Integer.parseInt(infCoringa[0]));
                        card = card.getCarta(infCoringa[2]);
                        ImageIcon img = card.getImagem();
                        img.setImage(img.getImage().getScaledInstance(100, 150, 120));
                        componentes[Integer.parseInt(infCoringa[0])].setIcon(img);
                        componentes[Integer.parseInt(infCoringa[0])].setName(card.getNome());
                        Trincas.set(Integer.parseInt(infCoringa[0]), card.getNome());
                        card = card.getCarta(cartaJogador);

                        if (verificaBaixouCartaNoCoringa == false) {
                            mao.removeCartaMao(PegaDescartada.getNome(), mao);
                            mao.adicionaNaPosicao(card, mao.size());
                            switch (infCoringa[1]) {
                                case "Carta1":
                                    Carta1.setVisible(true);
                                    Carta1.setIcon(card.getImagem());
                                    Carta1.setName(cartaJogador);
                                    break;
                                case "Carta2":
                                    Carta2.setVisible(true);
                                    Carta2.setIcon(card.getImagem());
                                    Carta2.setName(cartaJogador);
                                    break;
                                case "Carta3":
                                    Carta3.setVisible(true);
                                    Carta3.setIcon(card.getImagem());
                                    Carta3.setName(cartaJogador);
                                    break;
                                case "Carta4":
                                    Carta4.setVisible(true);
                                    Carta4.setIcon(card.getImagem());
                                    Carta4.setName(cartaJogador);
                                    break;
                                case "Carta5":
                                    Carta5.setVisible(true);
                                    Carta5.setIcon(card.getImagem());
                                    Carta5.setName(cartaJogador);
                                    break;
                                case "Carta6":
                                    Carta6.setVisible(true);
                                    Carta6.setIcon(card.getImagem());
                                    Carta6.setName(cartaJogador);
                                    break;
                                case "Carta7":
                                    Carta7.setVisible(true);
                                    Carta7.setIcon(card.getImagem());
                                    Carta7.setName(cartaJogador);
                                    break;
                                case "Carta8":
                                    Carta8.setVisible(true);
                                    Carta8.setIcon(card.getImagem());
                                    Carta8.setName(cartaJogador);
                                    break;
                                case "Carta9":
                                    Carta9.setVisible(true);
                                    Carta9.setIcon(card.getImagem());
                                    Carta9.setName(cartaJogador);
                                    break;
                                case "Carta10":
                                    carta.setIcon(card.getImagem());
                                    carta.setName(Carta10.getName());
                                    carta.setVisible(true);
                                    break;
                                default:
                                    break;
                            }
                        }
                        dicasUsuario.setText("Quando você pega o coringa e uma carta descartada, deve baixar ambos");
                        dicasUsuario.setVisible(true);
                        EscondeJLabel();
                    } else if (verifica == true && pegouDescartada == true && verificaBaixouCarta == false) { //volta carta que o jogador tava tentando descartar pro lugar e descarta a PegaDescartada
                        //quando pega descartada, rouba um coringa com uma carta que já tem na mão e baixa ele e não baixa a descartada
                        System.out.println("Entrou no caso 4");
                        AvisaPegouCoringa(infCoringa[0], infCoringa[3]);
                        card = card.getCarta(PegaDescartada.getNome());
                        CartaLixo.setIcon(card.getImagem());
                        mao.removeCartaMao(PegaDescartada.getNome(), mao);
                        DescartaCarta(PegaDescartada.getNome());
                        jogo.setCartaDescartada(PegaDescartada.getNome());
                        Carta10.setIcon(null);
                        carta.setBounds(x, y, 140, 186);
                        carta.setName(carta.getName());
                        dicasUsuario.setText("Você pegou uma carta do monte de descartes e não a descartou");
                        dicasUsuario.setVisible(true);
                        EscondeJLabel();
                    }
                    escondeBotoes();
                } else {
                    carta.setBounds(x, y, 140, 186);
                }
            } else {
                carta.setBounds(x, y, 140, 186);
            }
            pegouDescartada = false;
        }

        if (BaixarTrinca == true) {
            for (int i = 0; i < 3; i++) {
                if (x1 <= componentes[NumAnteriorCartasBaix + i].getX() + 50 && x1 >= componentes[NumAnteriorCartasBaix + i].getX() - 50 && y2 <= componentes[NumAnteriorCartasBaix + i].getY() + 50 && y2 >= componentes[NumAnteriorCartasBaix + i].getY() - 50 && componentes[NumAnteriorCartasBaix + i].getIcon() == null) {
                    GuardaNomeCartas[i] = NomeComponente;
                    int posicao = NumAnteriorCartasBaix + i;
                    GuardaPosicaoDosComponentes[somadorGuardaPosicoes] = Integer.toString(posicao);
                    if (NomeComponente.equals("Carta10")) {
                        Carta10.setIcon(null);
                        Carta10.setBounds(x, y, 100, 150);
                    }
                    GuardaJLabelCartas[i] = carta;
                    componentes[NumAnteriorCartasBaix + i].setName(carta.getName());
                    card = card.getCarta(carta.getName());
                    ImageIcon img = card.getImagem();
                    RemoveCartasMao(nomeVariavel, carta.getName());
                    if (Trincas.size() <= NumAnteriorCartasBaix + i) {
                        for (int contador = Trincas.size(); contador <= NumAnteriorCartasBaix + i; contador++) {
                            Trincas.add(contador, "");
                        }
                    }
                    Trincas.set(NumAnteriorCartasBaix + i, carta.getName());

                    img.setImage(img.getImage().getScaledInstance(100, 150, 120));
                    componentes[NumAnteriorCartasBaix + i].setIcon(img);
                    GuardaPosicoesCartas[i][0] = x;
                    GuardaPosicoesCartas[i][1] = y;
                    TrataTrincaCompleta();
                } else {
                    carta.setBounds(x, y, 140, 186);
                }
            }
        }

        if (BaixarQuarteto == true) {
            for (int i = 0; i < 4; i++) {
                if (x1 <= componentes[NumAnteriorCartasBaix + i].getX() + 50 && x1 >= componentes[NumAnteriorCartasBaix + i].getX() - 50 && y2 <= componentes[NumAnteriorCartasBaix + i].getY() + 50 && y2 >= componentes[NumAnteriorCartasBaix + i].getY() - 50 && componentes[NumAnteriorCartasBaix + i].getIcon() == null) {
                    GuardaNomeCartas[i] = NomeComponente;
                    int posicao = NumAnteriorCartasBaix + i;
                    GuardaPosicaoDosComponentes[somadorGuardaPosicoes] = Integer.toString(posicao);
                    if (NomeComponente.equals("Carta10")) {
                        Carta10.setIcon(null);
                        Carta10.setBounds(x, y, 100, 150);
                    }
                    GuardaJLabelCartas[i] = carta;
                    componentes[NumAnteriorCartasBaix + i].setName(carta.getName());
                    card = card.getCarta(carta.getName());
                    ImageIcon img = card.getImagem();
                    RemoveCartasMao(nomeVariavel, carta.getName());
                    if (Trincas.size() <= NumAnteriorCartasBaix + i) {
                        for (int contador = Trincas.size(); contador <= NumAnteriorCartasBaix + i; contador++) {
                            Trincas.add(contador, "");
                        }
                    }
                    Trincas.set(NumAnteriorCartasBaix + i, carta.getName());
                    img.setImage(img.getImage().getScaledInstance(100, 150, 120));
                    componentes[NumAnteriorCartasBaix + i].setIcon(img);
                    GuardaPosicoesCartas[i][0] = x;
                    GuardaPosicoesCartas[i][1] = y;
                    TrataQuartetoCompleto();
                } else {
                    carta.setBounds(x, y, 140, 186);
                }
            }
        }

        if (PodeCompletar == true) {
            int confere = 3;

            while (confere < totalTrincas) {
                if (Trincas.get(confere).equals("Falta Carta")) {
                    if (x1 <= componentes[confere].getX() + 50 && x1 >= componentes[confere].getX() - 50 && y2 <= componentes[confere].getY() + 50 && y2 >= componentes[confere].getY() - 50) {
                        componentes[confere].setName(carta.getName());
                        boolean validacao = jogo.ChecaQuarteto(componentes[confere - 3].getName(), componentes[confere - 2].getName(), componentes[confere - 1].getName(), componentes[confere].getName());
                        if (validacao == true) {
                            int calcula = 0;
                            calcula = (confere + 1) / 4;
                            tipo.set(calcula - 1, "Quarteto");
                            Trincas.set(confere, componentes[confere].getName());
                            card = card.getCarta(carta.getName());
                            ImageIcon img = card.getImagem();
                            RemoveCartasMao(nomeVariavel, carta.getName());
                            img.setImage(img.getImage().getScaledInstance(100, 150, 120));
                            componentes[confere].setIcon(img);
                            mao.removeCartaMao(card.getNome(), mao);
                            if (NomeComponente.equals("Carta10")) {
                                Carta10.setIcon(null);
                                Carta10.setBounds(x, y, 100, 150);
                            }
                            AvisaCompletou(Integer.toString(confere), tipo, componentes[confere].getName());
                            confere = totalTrincas + 1;
                        }
                        if (validacao == false) {
                            componentes[confere].setName("Falta Carta");
                            carta.setLocation(x, y);
                            confere = totalTrincas + 1;
                        }
                        confere = confere + 4;

                    } else {
                        if (confere == totalTrincas - 1) {
                            carta.setLocation(x, y);
                        }
                        confere = confere + 4;
                    }

                } else {
                    confere = confere + 4;
                }
            }
            int verifica = 3;
            int cont = 0;
            while (verifica < totalTrincas) {
                if (Trincas.get(verifica).equals("Falta Carta")) {
                    componentes[verifica].setVisible(false);
                    cont++;
                }
                verifica = verifica + 4;
            }
            if (cont == 0) {
                PodeCompletarTrinca = false;
            }
            PodeCompletar = false;
            PodeDescartar = true;
            mostraBotoes();
        }

        if (PegarCoringa == true) {
            int posicao = 0;
            auxiliar = 0;
            boolean trinca = false;
            boolean confere = false;
            boolean pegouCoringaComCoringa = false;

            if (carta.getName().equals("Propriedade 0 do Coringa") || carta.getName().equals("Propriedade 1 do Coringa")) {
                carta.setLocation(x, y);
                pegouCoringaComCoringa = true;
            }
            if (pegouCoringaComCoringa == false) {
                for (int i = 0; i < tipo.size(); i++) {
                    if (tipo.get(i).equals("Quarteto")) {
                        auxiliar = auxiliar + 4;
                        for (int somador = auxiliar - 4; somador < auxiliar; somador++) {
                            if (Trincas.get(somador).equals("Propriedade 0 do Coringa") || Trincas.get(somador).equals("Propriedade 1 do Coringa")) {
                                if (x1 <= componentes[somador].getX() + 50 && x1 >= componentes[somador].getX() - 50 && y2 <= componentes[somador].getY() + 50 && y2 >= componentes[somador].getY() - 50) {
                                    posicao = somador;
                                    i = tipo.size() + 1;
                                    confere = true;
                                    infCoringa[0] = Integer.toString(posicao); //posicao 
                                    infCoringa[1] = NomeComponente;
                                    infCoringa[2] = Trincas.get(posicao);
                                    infCoringa[3] = carta.getName();
                                    Trincas.set(somador, carta.getName());
                                    somador = auxiliar + 1;
                                }
                            }
                        }
                        trinca = false;
                    } else if (tipo.get(i).equals("Trinca")) {
                        auxiliar = auxiliar + 4;
                        for (int somador = auxiliar - 4; somador < auxiliar; somador++) {
                            if (Trincas.get(somador).equals("Propriedade 0 do Coringa") || Trincas.get(somador).equals("Propriedade 1 do Coringa")) {
                                if (x1 <= componentes[somador].getX() + 50 && x1 >= componentes[somador].getX() - 50 && y2 <= componentes[somador].getY() + 50 && y2 >= componentes[somador].getY() - 50) {
                                    posicao = somador;
                                    i = tipo.size() + 1;
                                    confere = true;
                                    infCoringa[0] = Integer.toString(posicao);
                                    infCoringa[1] = NomeComponente;
                                    infCoringa[2] = Trincas.get(posicao);
                                    infCoringa[3] = carta.getName();
                                    Trincas.set(somador, carta.getName());
                                    somador = auxiliar + 1;
                                }
                            }
                        }
                        trinca = true;
                    }
                }

                if (confere == false) {
                    carta.setLocation(x, y);
                } else if (confere == true) {
                    if (trinca == true) {
                        boolean validaCoringa = jogo.ChecaTrinca(Trincas.get(auxiliar - 2), Trincas.get(auxiliar - 3), Trincas.get(auxiliar - 4));
                        if (validaCoringa == true) {
                            TrocaCoringaPorCarta(posicao, carta, NomeComponente);
                            pegouCoringa = true;
                        } else {
                            Trincas.set(posicao, componentes[posicao].getName());
                            carta.setLocation(x, y);
                        }
                    }

                    if (trinca == false) {
                        boolean validaCoringa = jogo.ChecaQuarteto(Trincas.get(auxiliar - 1), Trincas.get(auxiliar - 2), Trincas.get(auxiliar - 3), Trincas.get(auxiliar - 4));
                        if (validaCoringa == true) {
                            TrocaCoringaPorCarta(posicao, carta, NomeComponente);
                            pegouCoringa = true;
                        } else {
                            Trincas.set(posicao, componentes[posicao].getName());
                            carta.setLocation(x, y);
                        }
                    }
                }
            }
            PegarCoringa = false;
            PodeDescartar = true;
            mostraBotoes();
        }

        if (mao.size() == 0) { //ROdada Acabou e Esse jogador nao fez pontos
            desabilitaBotoes();
            AvisaAcabouRodada();
        }
    }

    public boolean VerificaSeBaixouCarta(Carta nomeCarta) {
        String NomeCarta = nomeCarta.getNome();

        for (int i = 0; i < CartasBaixadas2.size(); i++) {
            if (CartasBaixadas2.get(i).equals(NomeCarta) == false) {
                if (Trincas.get(i).equals(NomeCarta)) {
                    return true;
                }
            }
        }

        int NumCartasRodada = Trincas.size() - CartasBaixadas2.size();
        for (int i = 1; i <= NumCartasRodada; i++) {
            if (Trincas.get(Trincas.size() - i).equals(NomeCarta)) {
                return true;
            }
        }
        return false;

    }

    int counter = 0;

    private void EscondeJLabel() {
        Timer timer = new Timer();
        counter = 3;
        TimerTask task = new TimerTask() {
            public void run() {
                counter = counter - 1;
                if (counter == -1) {
                    timer.cancel();
                    dicasUsuario.setVisible(false);
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000); // =  1000 = a delay de 1 segundo no contador;
    }

    public void TrocaCoringaPorCarta(int i, JLabel carta, String NomeComponente) {
        String NomeCartaComponente = componentes[i].getName();
        String NomeCartaJogador = carta.getName();
        ImageIcon ImgCoringa = card.getCarta(NomeCartaComponente).getImagem();
        if (NomeComponente.equals("Carta10")) {
            carta.setBounds(x, y, 100, 150);
            carta.setVisible(true);
            ImgCoringa.setImage(ImgCoringa.getImage().getScaledInstance(100, 150, 120));
            carta.setName(NomeCartaComponente);
            carta.setIcon(ImgCoringa);
        } else {
            carta.setBounds(x, y, 140, 186);
            carta.setVisible(true);
            carta.setName(NomeCartaComponente);
            carta.setIcon(ImgCoringa);
        }
        ImgMao = ImgMao.getCarta(NomeCartaJogador);
        ImageIcon img = ImgMao.getImagem();
        img.setImage(img.getImage().getScaledInstance(100, 150, 120));
        componentes[i].setIcon(img);
        componentes[i].setName(NomeCartaJogador);
        Trincas.set(i, NomeCartaJogador);
        card = card.getCarta(infCoringa[2]);
        mao.adicionaNaPosicao(card, mao.size()); //adiciona coringa na mão;
        mao.removeCartaMao(NomeCartaJogador, mao); // remove da mão a carta que foi baixada no lugar do coringa
    }

    public boolean VerificaSeBaixouCoringa() {

        for (int i = 0; i < CartasBaixadas.size(); i++) {
            if (CartasBaixadas.get(i).equals(Trincas.get(i)) == false) {
                if (Trincas.get(i).equals("Propriedade 1 do Coringa") || Trincas.get(i).equals("Propriedade 0 do Coringa")) {
                    return true;
                }
            }
        }

        int NumCartasRodada = Trincas.size() - CartasBaixadas.size();
        for (int i = 1; i <= NumCartasRodada; i++) {
            if (Trincas.get(Trincas.size() - i).equals("Propriedade 1 do Coringa") || (Trincas.get(Trincas.size() - i)).equals("Propriedade 0 do Coringa")) {
                return true;
            }
        }
        return false;
    }

    public void TrataTrincaCompleta() {
        somadorGuardaPosicoes++;
        if (componentes[NumAnteriorCartasBaix].getIcon() != null && componentes[NumAnteriorCartasBaix + 1].getIcon() != null && componentes[NumAnteriorCartasBaix + 2].getIcon() != null) {
            boolean validacao = jogo.ChecaTrinca(componentes[NumAnteriorCartasBaix].getName(), componentes[NumAnteriorCartasBaix + 1].getName(), componentes[NumAnteriorCartasBaix + 2].getName());
            if (validacao == true) {
                PodeCompletarTrinca = true;
                Trincas.add(NumAnteriorCartasBaix + 3, "Falta Carta");
                for (int contador = 0; contador < 3; contador++) {
                    mao.removeCartaMao(componentes[NumAnteriorCartasBaix + contador].getName(), mao);
                }
                tipo.add(tipo.size(), "Trinca");
                EnviaJogos(Trincas, tipo, GuardaPosicaoDosComponentes);
                LimpaGuardaNomeVariaveisEOrdem(3);
                BaixarTrinca = false;
                PodeDescartar = true;
                mostraBotoes();
            } else {
                TrincaErrada();
                BaixarTrinca = false;
                PodeDescartar = true;
                mostraBotoes();
            }
        }
    }

    public void TrataQuartetoCompleto() {
        somadorGuardaPosicoes++;
        if (componentes[NumAnteriorCartasBaix].getIcon() != null && componentes[NumAnteriorCartasBaix + 1].getIcon() != null && componentes[NumAnteriorCartasBaix + 2].getIcon() != null && componentes[NumAnteriorCartasBaix + 3].getIcon() != null) {
            boolean validacao = jogo.ChecaQuarteto(componentes[NumAnteriorCartasBaix].getName(), componentes[NumAnteriorCartasBaix + 1].getName(), componentes[NumAnteriorCartasBaix + 2].getName(), componentes[NumAnteriorCartasBaix + 3].getName());
            if (validacao == true) {
                for (int contador = 0; contador < 4; contador++) {
                    mao.removeCartaMao(componentes[NumAnteriorCartasBaix + contador].getName(), mao);
                }
                tipo.add(tipo.size(), "Quarteto");
                EnviaJogos(Trincas, tipo, GuardaPosicaoDosComponentes);
                LimpaGuardaNomeVariaveisEOrdem(4);
            } else {
                QuartetoErrado();
            }
            BaixarQuarteto = false;
            PodeDescartar = true;
            mostraBotoes();
        }
    }

    public void TrincaErrada() {
        CancelaJogo(3);
        BaixarTrinca = false;
        PodeDescartar = true;
        dicasUsuario.setText("Trinca Incorreta");
        dicasUsuario.setVisible(true);
        EscondeJLabel();
        mostraBotoes();
    }

    public void QuartetoErrado() {
        CancelaJogo(4);
        BaixarQuarteto = false;
        PodeDescartar = true;
        mostraBotoes();
        dicasUsuario.setText("Quarteto Incorreto");
        dicasUsuario.setVisible(true);
        EscondeJLabel();
    }

    public void LimpaGuardaNomeVariaveisEOrdem(int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            GuardaNomeCartas[i] = null;
            GuardaPosicaoDosComponentes[i] = null;
            somadorGuardaPosicoes = 0;
        }
    }

    public void CancelaJogo(int tamanho) {
        Carta carta = new Carta();
        int NumCartasAddTrincas = Trincas.size() - NumAnteriorCartasBaix;
        for (int i = 0; i < tamanho; i++) {
            if (GuardaNomeCartas[i] != null) {
                if (GuardaNomeCartas[i].equals("Carta10")) {
                    GuardaJLabelCartas[i].setBounds(GuardaPosicoesCartas[i][0], GuardaPosicoesCartas[i][1], 100, 150);
                    carta = carta.getCarta(componentes[NumAnteriorCartasBaix + i].getName());
                    ImageIcon img = carta.getImagem();
                    img.setImage(img.getImage().getScaledInstance(100, 150, 120));
                    GuardaJLabelCartas[i].setIcon(img);
                } else {
                    GuardaJLabelCartas[i].setBounds(GuardaPosicoesCartas[i][0], GuardaPosicoesCartas[i][1], 140, 186);
                    carta = carta.getCarta(componentes[NumAnteriorCartasBaix + i].getName());
                    GuardaJLabelCartas[i].setIcon(carta.getImagem());
                }
            }
            componentes[NumAnteriorCartasBaix + i].setIcon(null);
            componentes[NumAnteriorCartasBaix + i].setVisible(false);
        }

        for (int i = NumCartasAddTrincas - 1; i >= 0; i--) {
            Trincas.remove(NumAnteriorCartasBaix + i);
        }
        LimpaGuardaNomeVariaveisEOrdem(tamanho);
    }

    public void RemoveCartasMao(String NomeVariavel, String nomeCarta) {

        switch (NomeVariavel) {
            case "Carta1":
                Carta1.setIcon(null);
                break;
            case "Carta2":
                Carta2.setIcon(null);
                break;
            case "Carta3":
                Carta3.setIcon(null);
                break;
            case "Carta4":
                Carta4.setIcon(null);
                break;
            case "Carta5":
                Carta5.setIcon(null);
                break;
            case "Carta6":
                Carta6.setIcon(null);
                break;
            case "Carta7":
                Carta7.setIcon(null);
                break;
            case "Carta8":
                Carta8.setIcon(null);
                break;
            case "Carta9":
                Carta9.setIcon(null);
                break;
            case "Carta10":
                Carta10.setIcon(null);
                break;
        }
    }

    public void mousePressed(MouseEvent e) {
        MousePressionado = true;
        habilitar = true;

    }

    void finalizaJogo() {

        JOptionPane.showMessageDialog(null, "Conexão perdida com o jogador remoto!");

        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.dispose();
        Runtime.getRuntime().exit(0);

    }

    public class Mover extends Thread {

        JLabel Carta = new JLabel();
        Point ponto = new Point();

        Mover(JLabel carta) {
            this.Carta = carta;
        }

        public void run() {
            while (habilitar == true) {
                try {
                    sleep(10);
                } catch (Exception erro) {
                    System.out.println("Erro" + erro);
                }
                if (MousePressionado) {
                    ponto = getMousePosition();
                    this.Carta.setBounds(ponto.x - 70, ponto.y - 93, 140, 186);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        DescartaCarta = new javax.swing.JButton();
        Bater = new javax.swing.JButton();
        BaixaCarta = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Quarteto = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        PanelPontuacao = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        avisaVencedor = new javax.swing.JLabel();
        nomeJog2 = new javax.swing.JLabel();
        pontAnteriorJog1 = new javax.swing.JLabel();
        pontAnteriorJog2 = new javax.swing.JLabel();
        pontRodadaJog1 = new javax.swing.JLabel();
        pontRodadaJog2 = new javax.swing.JLabel();
        TotalJog1 = new javax.swing.JLabel();
        TotalJog2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        nomeJog1 = new javax.swing.JLabel();
        Carta7 = new javax.swing.JLabel();
        Carta6 = new javax.swing.JLabel();
        Carta10 = new javax.swing.JLabel();
        Carta9 = new javax.swing.JLabel();
        Carta8 = new javax.swing.JLabel();
        Carta5 = new javax.swing.JLabel();
        Carta4 = new javax.swing.JLabel();
        Carta3 = new javax.swing.JLabel();
        Carta2 = new javax.swing.JLabel();
        Carta1 = new javax.swing.JLabel();
        verso9 = new javax.swing.JLabel();
        CartaLixo = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        verso2 = new javax.swing.JLabel();
        verso3 = new javax.swing.JLabel();
        verso4 = new javax.swing.JLabel();
        verso5 = new javax.swing.JLabel();
        verso6 = new javax.swing.JLabel();
        verso7 = new javax.swing.JLabel();
        verso8 = new javax.swing.JLabel();
        btn_coringa = new javax.swing.JButton();
        btn_quarteto = new javax.swing.JButton();
        lblQuarteto12 = new javax.swing.JLabel();
        lblQuarteto1 = new javax.swing.JLabel();
        lblQuarteto2 = new javax.swing.JLabel();
        verso1 = new javax.swing.JLabel();
        lblQuarteto4 = new javax.swing.JLabel();
        btn_trinca = new javax.swing.JButton();
        lblQuarteto5 = new javax.swing.JLabel();
        lblQuarteto6 = new javax.swing.JLabel();
        lblQuarteto7 = new javax.swing.JLabel();
        lblQuarteto8 = new javax.swing.JLabel();
        lblQuarteto9 = new javax.swing.JLabel();
        lblQuarteto10 = new javax.swing.JLabel();
        lblQuarteto11 = new javax.swing.JLabel();
        Button_cartaDescart = new javax.swing.JButton();
        cartaMonte = new javax.swing.JButton();
        lblQuarteto21 = new javax.swing.JLabel();
        lblQuarteto24 = new javax.swing.JLabel();
        lblQuarteto23 = new javax.swing.JLabel();
        lblQuarteto22 = new javax.swing.JLabel();
        lblQuarteto16 = new javax.swing.JLabel();
        lblQuarteto13 = new javax.swing.JLabel();
        lblQuarteto14 = new javax.swing.JLabel();
        lblQuarteto15 = new javax.swing.JLabel();
        lblQuarteto20 = new javax.swing.JLabel();
        lblQuarteto17 = new javax.swing.JLabel();
        lblQuarteto18 = new javax.swing.JLabel();
        lblQuarteto19 = new javax.swing.JLabel();
        btn_completar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        dicasUsuario = new javax.swing.JLabel();
        lblQuarteto3 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(0, 153, 51));
        jPanel2.setLayout(null);

        DescartaCarta.setText("Descartar Carta");
        DescartaCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescartaCartaActionPerformed(evt);
            }
        });
        jPanel2.add(DescartaCarta);
        DescartaCarta.setBounds(1120, 490, 180, 40);

        Bater.setText("Bater");
        jPanel2.add(Bater);
        Bater.setBounds(20, 500, 73, 30);

        BaixaCarta.setText("Completar Trinca");
        jPanel2.add(BaixaCarta);
        BaixaCarta.setBounds(10, 550, 110, 40);

        jButton3.setText("Trinca");
        jPanel2.add(jButton3);
        jButton3.setBounds(20, 610, 73, 40);

        Quarteto.setText("Quarteto");
        jPanel2.add(Quarteto);
        Quarteto.setBounds(10, 660, 77, 40);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(990, 612));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(10, 90, 10));
        jPanel1.setMaximumSize(new java.awt.Dimension(1366, 768));
        jPanel1.setMinimumSize(new java.awt.Dimension(1366, 768));
        jPanel1.setName(""); // NOI18N
        jPanel1.setLayout(null);

        PanelPontuacao.setBackground(new java.awt.Color(255, 255, 153));
        PanelPontuacao.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 51)));
        PanelPontuacao.setMaximumSize(new java.awt.Dimension(360, 250));
        PanelPontuacao.setMinimumSize(new java.awt.Dimension(360, 250));
        PanelPontuacao.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel2.setText("Pontuação Anterior");
        PanelPontuacao.add(jLabel2);
        jLabel2.setBounds(10, 60, 140, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel3.setText("Pontuação Rodada");
        PanelPontuacao.add(jLabel3);
        jLabel3.setBounds(10, 100, 130, 15);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel4.setText("Total");
        PanelPontuacao.add(jLabel4);
        jLabel4.setBounds(50, 140, 40, 15);

        avisaVencedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        avisaVencedor.setForeground(new java.awt.Color(255, 51, 0));
        avisaVencedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PanelPontuacao.add(avisaVencedor);
        avisaVencedor.setBounds(50, 170, 250, 20);

        nomeJog2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        nomeJog2.setForeground(new java.awt.Color(255, 51, 0));
        nomeJog2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeJog2.setText("Jogador2");
        PanelPontuacao.add(nomeJog2);
        nomeJog2.setBounds(230, 30, 90, 20);

        pontAnteriorJog1.setText("10");
        PanelPontuacao.add(pontAnteriorJog1);
        pontAnteriorJog1.setBounds(170, 70, 40, 14);

        pontAnteriorJog2.setText("5");
        PanelPontuacao.add(pontAnteriorJog2);
        pontAnteriorJog2.setBounds(270, 70, 30, 14);

        pontRodadaJog1.setText("20");
        PanelPontuacao.add(pontRodadaJog1);
        pontRodadaJog1.setBounds(170, 100, 40, 14);

        pontRodadaJog2.setText("0");
        PanelPontuacao.add(pontRodadaJog2);
        pontRodadaJog2.setBounds(270, 100, 30, 14);

        TotalJog1.setText("30");
        PanelPontuacao.add(TotalJog1);
        TotalJog1.setBounds(170, 140, 30, 10);

        TotalJog2.setText("5");
        PanelPontuacao.add(TotalJog2);
        TotalJog2.setBounds(270, 140, 30, 10);

        jSeparator1.setBackground(new java.awt.Color(255, 102, 0));
        PanelPontuacao.add(jSeparator1);
        jSeparator1.setBounds(20, 130, 300, 10);

        nomeJog1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        nomeJog1.setForeground(new java.awt.Color(255, 51, 0));
        nomeJog1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeJog1.setText("Jogador1");
        PanelPontuacao.add(nomeJog1);
        nomeJog1.setBounds(130, 30, 100, 20);

        jPanel1.add(PanelPontuacao);
        PanelPontuacao.setBounds(520, 280, 348, 210);

        Carta7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta7MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta7MouseReleased(evt);
            }
        });
        jPanel1.add(Carta7);
        Carta7.setBounds(920, 520, 134, 190);

        Carta6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta6MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta6MouseReleased(evt);
            }
        });
        jPanel1.add(Carta6);
        Carta6.setBounds(760, 520, 134, 190);

        Carta10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta10MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta10MouseReleased(evt);
            }
        });
        jPanel1.add(Carta10);
        Carta10.setBounds(570, 354, 100, 150);

        Carta9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta9MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta9MouseReleased(evt);
            }
        });
        jPanel1.add(Carta9);
        Carta9.setBounds(1220, 520, 134, 190);

        Carta8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta8MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta8MouseReleased(evt);
            }
        });
        jPanel1.add(Carta8);
        Carta8.setBounds(1070, 520, 134, 190);

        Carta5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta5MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta5MouseReleased(evt);
            }
        });
        jPanel1.add(Carta5);
        Carta5.setBounds(610, 520, 134, 190);

        Carta4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta4MouseReleased(evt);
            }
        });
        jPanel1.add(Carta4);
        Carta4.setBounds(460, 520, 134, 190);

        Carta3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta3MouseReleased(evt);
            }
        });
        jPanel1.add(Carta3);
        Carta3.setBounds(310, 520, 134, 190);

        Carta2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta2MouseReleased(evt);
            }
        });
        jPanel1.add(Carta2);
        Carta2.setBounds(160, 520, 134, 190);

        Carta1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Carta1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Carta1MouseReleased(evt);
            }
        });
        jPanel1.add(Carta1);
        Carta1.setBounds(10, 520, 134, 190);

        verso9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso9);
        verso9.setBounds(1100, 10, 100, 156);

        CartaLixo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CartaLixo.setText("Cartas Descartadas");
        CartaLixo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        CartaLixo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(CartaLixo);
        CartaLixo.setBounds(190, 250, 140, 186);

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/Monte de Cartas.png"))); // NOI18N
        jPanel1.add(jLabel31);
        jLabel31.setBounds(30, 250, 120, 180);

        verso2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso2);
        verso2.setBounds(190, 10, 100, 156);

        verso3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso3);
        verso3.setBounds(300, 10, 100, 156);

        verso4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso4);
        verso4.setBounds(480, 10, 100, 156);

        verso5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso5);
        verso5.setBounds(590, 10, 100, 156);

        verso6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso6);
        verso6.setBounds(700, 10, 100, 156);

        verso7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso7);
        verso7.setBounds(880, 10, 100, 156);

        verso8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso8);
        verso8.setBounds(990, 10, 100, 156);

        btn_coringa.setText("Pegar Coringa");
        btn_coringa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_coringaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_coringa);
        btn_coringa.setBounds(1210, 470, 150, 30);

        btn_quarteto.setText("Baixar Quarteto");
        btn_quarteto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quartetoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_quarteto);
        btn_quarteto.setBounds(1210, 430, 150, 30);
        jPanel1.add(lblQuarteto12);
        lblQuarteto12.setBounds(1070, 360, 100, 150);
        jPanel1.add(lblQuarteto1);
        lblQuarteto1.setBounds(410, 190, 100, 150);
        jPanel1.add(lblQuarteto2);
        lblQuarteto2.setBounds(510, 190, 100, 150);

        verso1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/verso.png"))); // NOI18N
        jPanel1.add(verso1);
        verso1.setBounds(80, 10, 100, 156);
        jPanel1.add(lblQuarteto4);
        lblQuarteto4.setBounds(710, 190, 100, 150);

        btn_trinca.setText("Baixar Trinca");
        btn_trinca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_trincaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_trinca);
        btn_trinca.setBounds(1210, 390, 150, 30);
        jPanel1.add(lblQuarteto5);
        lblQuarteto5.setBounds(860, 190, 100, 150);
        jPanel1.add(lblQuarteto6);
        lblQuarteto6.setBounds(960, 190, 100, 150);
        jPanel1.add(lblQuarteto7);
        lblQuarteto7.setBounds(1060, 190, 100, 150);
        jPanel1.add(lblQuarteto8);
        lblQuarteto8.setBounds(1160, 190, 100, 150);
        jPanel1.add(lblQuarteto9);
        lblQuarteto9.setBounds(770, 360, 100, 150);
        jPanel1.add(lblQuarteto10);
        lblQuarteto10.setBounds(870, 360, 100, 150);
        jPanel1.add(lblQuarteto11);
        lblQuarteto11.setBounds(970, 360, 100, 150);

        Button_cartaDescart.setText("Carta Descartada");
        Button_cartaDescart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_cartaDescartActionPerformed(evt);
            }
        });
        jPanel1.add(Button_cartaDescart);
        Button_cartaDescart.setBounds(180, 440, 160, 30);

        cartaMonte.setText("Carta do Monte");
        cartaMonte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartaMonteActionPerformed(evt);
            }
        });
        jPanel1.add(cartaMonte);
        cartaMonte.setBounds(20, 440, 140, 30);
        jPanel1.add(lblQuarteto21);
        lblQuarteto21.setBounds(1250, 530, 100, 150);
        jPanel1.add(lblQuarteto24);
        lblQuarteto24.setBounds(950, 530, 100, 150);
        jPanel1.add(lblQuarteto23);
        lblQuarteto23.setBounds(1050, 530, 100, 150);
        jPanel1.add(lblQuarteto22);
        lblQuarteto22.setBounds(1150, 530, 100, 150);
        jPanel1.add(lblQuarteto16);
        lblQuarteto16.setBounds(1160, 20, 100, 150);
        jPanel1.add(lblQuarteto13);
        lblQuarteto13.setBounds(860, 20, 100, 150);
        jPanel1.add(lblQuarteto14);
        lblQuarteto14.setBounds(960, 20, 100, 150);
        jPanel1.add(lblQuarteto15);
        lblQuarteto15.setBounds(1060, 20, 100, 150);
        jPanel1.add(lblQuarteto20);
        lblQuarteto20.setBounds(740, 20, 100, 150);
        jPanel1.add(lblQuarteto17);
        lblQuarteto17.setBounds(440, 20, 100, 150);
        jPanel1.add(lblQuarteto18);
        lblQuarteto18.setBounds(540, 20, 100, 150);
        jPanel1.add(lblQuarteto19);
        lblQuarteto19.setBounds(640, 20, 100, 150);

        btn_completar.setText("Completar Trinca");
        btn_completar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_completarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_completar);
        btn_completar.setBounds(1210, 350, 150, 30);

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cancelar);
        btn_cancelar.setBounds(1210, 390, 150, 30);

        dicasUsuario.setBackground(new java.awt.Color(231, 247, 197));
        dicasUsuario.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        dicasUsuario.setForeground(new java.awt.Color(0, 102, 0));
        dicasUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dicasUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dicasUsuario.setOpaque(true);
        jPanel1.add(dicasUsuario);
        dicasUsuario.setBounds(10, 480, 490, 30);
        jPanel1.add(lblQuarteto3);
        lblQuarteto3.setBounds(610, 190, 100, 150);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1420, 770);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DescartaCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescartaCartaActionPerformed

    }//GEN-LAST:event_DescartaCartaActionPerformed

    private void btn_completarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_completarActionPerformed
        totalTrincas = Trincas.size();
        int confere = 3;
        while (confere <= totalTrincas) {
            if (Trincas.get(confere).equals("Falta Carta")) {
                componentes[confere].setBorder(BorderFactory.createLineBorder(Color.black, 1));
                componentes[confere].setVisible(true);
            }
            confere = confere + 4;
        }
        PodeCompletar = true;
        PodeDescartar = false;
        escondeBotoes();
    }//GEN-LAST:event_btn_completarActionPerformed


    private void cartaMonteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartaMonteActionPerformed

        cartaMonte.setEnabled(false);
        Button_cartaDescart.setEnabled(false);
        cartaBaralho = null;
        PescarCartaBaralho();

    }//GEN-LAST:event_cartaMonteActionPerformed


    private void Button_cartaDescartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_cartaDescartActionPerformed
        dicasUsuario.setText("Quando a carta do monte de descartes for pega, a mesma deve ser baixada");
        dicasUsuario.setVisible(true);
        EscondeJLabel();
        CartasBaixadas2 = (ArrayList<String>) Trincas.clone();
        Carta cartaDeBaixo = new Carta();
        NumAnteriorCartasBaix = tipo.size();
        PegaDescartada = jogo.PegaUltimaCartaDescartada();
        mao.adiciona(PegaDescartada);
        ImageIcon img = PegaDescartada.getImagem();
        img.setImage(img.getImage().getScaledInstance(100, 150, 120));
        Carta10.setIcon(img);
        Carta10.setName(PegaDescartada.getNome());
        Carta10.setVisible(true);
        if (jogo.SizeCartasDescartadas() >= 1) {
            cartaDeBaixo = jogo.PegaCartaDebaixo();
            CartaLixo.setIcon(cartaDeBaixo.getImagem());
            AvisaPegouDescartada(cartaDeBaixo.getNome());
        } else {
            CartaLixo.setIcon(null);
            AvisaPegouDescartada("UnicaCarta");
        }
        mostraBotoes();
        pegouDescartada = true;
        PodeDescartar = true;
    }//GEN-LAST:event_Button_cartaDescartActionPerformed

    private void btn_trincaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_trincaActionPerformed
        dicasUsuario.setText("Arraste as cartas até o local demarcado");
        dicasUsuario.setVisible(true);
        EscondeJLabel();
        NumAnteriorCartasBaix = Trincas.size();

        for (int i = 0; i < 3; i++) {
            componentes[NumAnteriorCartasBaix + i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
            componentes[NumAnteriorCartasBaix + i].setVisible(true);
        }

        PodeDescartar = false;
        BaixarTrinca = true;
        escondeBotoes();

        //Imprimir uma mensagem pro usuario arrastar as cartas;
    }//GEN-LAST:event_btn_trincaActionPerformed

    private void Carta7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta7MouseReleased
        nomeVariavel = "Carta7";
        mouseReleased(nomeVariavel, Carta7, evt);
    }//GEN-LAST:event_Carta7MouseReleased

    private void Carta7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta7MousePressed
        x = Carta7.getX();
        y = Carta7.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta7).start();
        }
    }//GEN-LAST:event_Carta7MousePressed

    private void Carta10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta10MouseReleased
        nomeVariavel = "Carta10";
        mouseReleased(nomeVariavel, Carta10, evt);
    }//GEN-LAST:event_Carta10MouseReleased

    private void Carta10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta10MousePressed
        x = Carta10.getX();
        y = Carta10.getY();
        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta10).start();
        }
    }//GEN-LAST:event_Carta10MousePressed

    private void btn_quartetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quartetoActionPerformed
        dicasUsuario.setText("Arraste as cartas até o local demarcado");
        dicasUsuario.setVisible(true);
        EscondeJLabel();
        NumAnteriorCartasBaix = Trincas.size();

        for (int i = 0; i < 4; i++) {
            componentes[NumAnteriorCartasBaix + i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
            componentes[NumAnteriorCartasBaix + i].setVisible(true);
        }
        BaixarQuarteto = true;
        PodeDescartar = false;
        escondeBotoes();
    }//GEN-LAST:event_btn_quartetoActionPerformed

    private void btn_coringaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_coringaActionPerformed
        dicasUsuario.setText("Arraste a carta até o coringa que desejar");
        dicasUsuario.setVisible(true);
        EscondeJLabel();
        CartasBaixadas = (ArrayList<String>) Trincas.clone();
        PegarCoringa = true;
        PodeDescartar = false;
        escondeBotoes();
    }//GEN-LAST:event_btn_coringaActionPerformed

    private void Carta8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta8MouseReleased
        nomeVariavel = "Carta8";
        mouseReleased(nomeVariavel, Carta8, evt);
    }//GEN-LAST:event_Carta8MouseReleased

    private void Carta8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta8MousePressed
        x = Carta8.getX();
        y = Carta8.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta8).start();
        }

    }//GEN-LAST:event_Carta8MousePressed

    private void Carta4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta4MouseReleased
        nomeVariavel = "Carta4";
        mouseReleased(nomeVariavel, Carta4, evt);
    }//GEN-LAST:event_Carta4MouseReleased

    private void Carta4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta4MousePressed
        x = Carta4.getX();
        y = Carta4.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta4).start();
        }
    }//GEN-LAST:event_Carta4MousePressed

    private void Carta3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta3MouseReleased
            nomeVariavel = "Carta3";
            mouseReleased(nomeVariavel, Carta3, evt);
    }//GEN-LAST:event_Carta3MouseReleased

    private void Carta3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta3MousePressed
        x = Carta3.getX();
        y = Carta3.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta3).start();
        }

    }//GEN-LAST:event_Carta3MousePressed

    private void Carta6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta6MouseReleased
        nomeVariavel = "Carta6";
        mouseReleased(nomeVariavel, Carta6, evt);
    }//GEN-LAST:event_Carta6MouseReleased

    private void Carta6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta6MousePressed
        x = Carta6.getX();
        y = Carta6.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta6).start();
        }
    }//GEN-LAST:event_Carta6MousePressed

    private void Carta2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta2MouseReleased
        nomeVariavel = "Carta2";
        mouseReleased(nomeVariavel, Carta2, evt);
    }//GEN-LAST:event_Carta2MouseReleased

    private void Carta2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta2MousePressed
        x = Carta2.getX();
        y = Carta2.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta2).start();
        }
    }//GEN-LAST:event_Carta2MousePressed

    private void Carta1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta1MouseReleased
        nomeVariavel = "Carta1";
        mouseReleased(nomeVariavel, Carta1, evt);
    }//GEN-LAST:event_Carta1MouseReleased

    private void Carta1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta1MousePressed
        x = Carta1.getX();
        y = Carta1.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta1).start();
        }
    }//GEN-LAST:event_Carta1MousePressed

    private void Carta5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta5MouseReleased
        nomeVariavel = "Carta5";
        mouseReleased(nomeVariavel, Carta5, evt);
    }//GEN-LAST:event_Carta5MouseReleased

    private void Carta5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta5MousePressed
        x = Carta5.getX();
        y = Carta5.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta5).start();
        }
    }//GEN-LAST:event_Carta5MousePressed

    private void Carta9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta9MouseReleased
        nomeVariavel = "Carta9";
        mouseReleased(nomeVariavel, Carta9, evt);
    }//GEN-LAST:event_Carta9MouseReleased

    private void Carta9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta9MousePressed
        x = Carta9.getX();
        y = Carta9.getY();

        if (PodeDescartar == true || BaixarTrinca == true || BaixarQuarteto == true || PodeCompletar == true || PegarCoringa == true || pegouDescartada == true) {
            mousePressed(evt);
            new Mover(Carta9).start();
        }
    }//GEN-LAST:event_Carta9MousePressed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        dicasUsuario.setVisible(false);
        if (BaixarTrinca == true) {
            CancelaJogo(3);
            BaixarTrinca = false;
            PodeDescartar = true;
            mostraBotoes();
        }

        if (BaixarQuarteto == true) {
            CancelaJogo(4);
            BaixarQuarteto = false;
            PodeDescartar = true;
            mostraBotoes();
        }

        if (PodeCompletar == true) {
            int verifica = 3;
            while (verifica < Trincas.size()) {
                if (Trincas.get(verifica).equals("Falta Carta")) {
                    componentes[verifica].setVisible(false);
                }
                verifica = verifica + 4;
            }
            mostraBotoes();
            PodeDescartar = true;
            PodeCompletar = false;
        }

        if (PegarCoringa == true) {
            PegarCoringa = false;
            PodeDescartar = true;
            mostraBotoes();
        }

    }//GEN-LAST:event_btn_cancelarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MesaJogador1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MesaJogador1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MesaJogador1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MesaJogador1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MesaJogador1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BaixaCarta;
    private javax.swing.JButton Bater;
    private javax.swing.JButton Button_cartaDescart;
    private javax.swing.JLabel Carta1;
    private javax.swing.JLabel Carta10;
    private javax.swing.JLabel Carta2;
    private javax.swing.JLabel Carta3;
    private javax.swing.JLabel Carta4;
    private javax.swing.JLabel Carta5;
    private javax.swing.JLabel Carta6;
    private javax.swing.JLabel Carta7;
    private javax.swing.JLabel Carta8;
    private javax.swing.JLabel Carta9;
    private javax.swing.JLabel CartaLixo;
    private javax.swing.JButton DescartaCarta;
    private javax.swing.JPanel PanelPontuacao;
    private javax.swing.JButton Quarteto;
    private javax.swing.JLabel TotalJog1;
    private javax.swing.JLabel TotalJog2;
    private javax.swing.JLabel avisaVencedor;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_completar;
    private javax.swing.JButton btn_coringa;
    private javax.swing.JButton btn_quarteto;
    private javax.swing.JButton btn_trinca;
    private javax.swing.JButton cartaMonte;
    private javax.swing.JLabel dicasUsuario;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblQuarteto1;
    private javax.swing.JLabel lblQuarteto10;
    private javax.swing.JLabel lblQuarteto11;
    private javax.swing.JLabel lblQuarteto12;
    private javax.swing.JLabel lblQuarteto13;
    private javax.swing.JLabel lblQuarteto14;
    private javax.swing.JLabel lblQuarteto15;
    private javax.swing.JLabel lblQuarteto16;
    private javax.swing.JLabel lblQuarteto17;
    private javax.swing.JLabel lblQuarteto18;
    private javax.swing.JLabel lblQuarteto19;
    private javax.swing.JLabel lblQuarteto2;
    private javax.swing.JLabel lblQuarteto20;
    private javax.swing.JLabel lblQuarteto21;
    private javax.swing.JLabel lblQuarteto22;
    private javax.swing.JLabel lblQuarteto23;
    private javax.swing.JLabel lblQuarteto24;
    private javax.swing.JLabel lblQuarteto3;
    private javax.swing.JLabel lblQuarteto4;
    private javax.swing.JLabel lblQuarteto5;
    private javax.swing.JLabel lblQuarteto6;
    private javax.swing.JLabel lblQuarteto7;
    private javax.swing.JLabel lblQuarteto8;
    private javax.swing.JLabel lblQuarteto9;
    private javax.swing.JLabel nomeJog1;
    private javax.swing.JLabel nomeJog2;
    private javax.swing.JLabel pontAnteriorJog1;
    private javax.swing.JLabel pontAnteriorJog2;
    private javax.swing.JLabel pontRodadaJog1;
    private javax.swing.JLabel pontRodadaJog2;
    private javax.swing.JLabel verso1;
    private javax.swing.JLabel verso2;
    private javax.swing.JLabel verso3;
    private javax.swing.JLabel verso4;
    private javax.swing.JLabel verso5;
    private javax.swing.JLabel verso6;
    private javax.swing.JLabel verso7;
    private javax.swing.JLabel verso8;
    private javax.swing.JLabel verso9;
    // End of variables declaration//GEN-END:variables
}
