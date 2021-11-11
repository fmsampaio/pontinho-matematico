package Jogo;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class TrataCliente extends Thread {

    private Socket conexao = null;
    private InputStream cliente = null;
    private Servidor servidor = null;
    private ClienteServidor clienteServidor = null;
    String cartaMonte = null;

    public TrataCliente(Socket conexao, InputStream cliente, Servidor servidor, ClienteServidor clienteServidor) {
        this.conexao = conexao;
        this.cliente = cliente;
        this.servidor = servidor;
        this.clienteServidor = clienteServidor;
    }

    public void run() {
        // quando chegar uma msg, distribui pra todos
        Scanner s = new Scanner(this.cliente);

        while (s.hasNextLine()) {
            String entrada = s.nextLine();
            this.trataEntrada(entrada);

            //servidor.distribuiMensagem(s.nextLine());
        }
        s.close();
    }

    public void trataEntrada(String entrada) {
        String[] array = entrada.split(";");
        for (int i = 0; i < array.length; i++) {
            System.out.println("Array: " + array[i]);
        }

        switch (array[0]) {
            case "logar":
                logar(array[1]);
                break;

            case "jogar":
                Jogar(array[1], array[2]);
                break;

            case "cartaMonte":
                pescaCartaMonte(array[1], array[2]);

                break;

            case "trinca":
                baixaJogo(array[1], array[2], array[3], array[4]);
                break;

            case "completouJogo":
                completaJogo(array[1], array[2], array[3], array[4]);
                break;

            case "pescouDescartada":
                pegouDescartada(array[1], array[2]);
                break;

            case "pegouCoringa":
                pegouCoringa(array[1], array[2], array[3]);
                break;

            case "naoPegouCoringa":
                naoPegouCoringa(array[1], array[2], array[3]);
                break;

            case "acabouRodada":
                acabouRodada(array[1]);
                break;
            case "avisaPontuacao":
                avisaPontuacao(array[1], array[2], array[3], array[4], array[5], array[6]);
                break;
            case "acabouJogo":
                acabouJogo(array[1]);
                break;
            default:
                break;
        }
    }

    public void logar(String login) {

        this.clienteServidor.setLogin(login);
        this.clienteServidor.setAtivo(1);

        Map<Integer, ClienteServidor> clientes = this.servidor.clientes;

        for (int key : clientes.keySet()) {
            if (clientes.get(key).getOponente() == null && !clientes.get(key).getLogin().equals(login) && this.clienteServidor.getAtivo() == 1) {

                //Inclui Oponente player dois
                this.clienteServidor.setOponente(clientes.get(key).getLogin());
                this.clienteServidor.setOponentePS(clientes.get(key).getLoginPS());
                this.clienteServidor.setOponenteId(key);

                //Inclui Oponente player um que estava a espera e foi achado na lista				
                clientes.get(key).setOponente(this.clienteServidor.getLogin());
                clientes.get(key).setOponentePS(this.clienteServidor.getLoginPS());
                clientes.get(key).setOponenteId(this.clienteServidor.getLoginId());
                Sessao sessao_selecionada = servidor.GetNovaSessao(key, clientes.get(key), clienteServidor);

                Mao maoJogador1 = sessao_selecionada.NovaMao(key);
                Mao maoJogador2 = sessao_selecionada.NovaMao(key);
                //Retorna Oponente de cada Login
                servidor.distribuiMensagem(this.clienteServidor.getLoginPS(), "logar;" + this.clienteServidor.getLoginId() + ";" + clientes.get(key).getLogin() + ";" + clientes.get(key).getLoginId() + ";" + sessao_selecionada.getId_sessao() + ";" + maoJogador2.toString());
                servidor.distribuiMensagem(clientes.get(key).getLoginPS(), "logar;" + clientes.get(key).getLoginId() + ";" + this.clienteServidor.getLogin() + ";" + this.clienteServidor.getLoginId() + ";" + sessao_selecionada.getId_sessao() + ";" + maoJogador1.toString());
            }
        }
    }

    public void Jogar(String id, String cartaDescartada) {
        System.out.println("Jogar Servidor: " + id + "  -  " + cartaDescartada);
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "jogar;" + cartaDescartada);

    }

    public void pescaCartaMonte(String id, String id_sessao) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        Sessao sessaoJogo = servidor.getSessao(id_sessao);
        cartaMonte = sessaoJogo.CompraCartaBaralho(Integer.parseInt(id_sessao));
        servidor.distribuiMensagem(cs.getLoginPS(), "cartaMonte;" + cartaMonte);
    }

    public void baixaJogo(String id, String trincas, String tipos, String ordem) {
        System.out.println("Jogar Servidor: " + id + "  -  " + trincas + "-" + tipos);
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "trinca;" + trincas + ";" + tipos + ";" + ordem);

    }

    public void pegouDescartada(String id, String carta) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "pescouDescartada;" + carta);

    }

    public void pegouCoringa(String id, String posicao, String carta) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "pegouCoringa;" + posicao + ";" + carta);
    }

    public void naoPegouCoringa(String id, String nomeCoringa, String posicao) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "naoPegouCoringa;" + nomeCoringa + ";" + posicao);
    }

    public void acabouRodada(String id) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "acabouRodada;");
    }

    public void acabouJogo(String id) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "acabouJogo;");

    }

    public void avisaPontuacao(String idDerrotado, String pontuacaoDerrotado, String id_vencedor, String pontuacaoVencedor, String id_sessao, String maoDerrotado) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(idDerrotado));
        int id_jogador1 = 0;
        int id_jogador2 = 0;
        int pontuacaoRodadaJogador1 = 0;
        int pontuacaoRodadaJogador2 = 0;

        Sessao sessaoJogo = servidor.getSessao(id_sessao);
        if (Integer.parseInt(idDerrotado) < Integer.parseInt(id_vencedor)) {
            id_jogador1 = Integer.parseInt(idDerrotado);
            pontuacaoRodadaJogador1 = Integer.parseInt(pontuacaoDerrotado);
            id_jogador2 = Integer.parseInt(id_vencedor);
            pontuacaoRodadaJogador2 = 0;
        } else {
            id_jogador2 = Integer.parseInt(idDerrotado);
            id_jogador1 = Integer.parseInt(id_vencedor);
            pontuacaoRodadaJogador2 = Integer.parseInt(pontuacaoDerrotado);
            pontuacaoRodadaJogador1 = 0;
        }

        String[] pontuacaoJogadores = sessaoJogo.GetPontuacaoRodada(Integer.parseInt(id_sessao), id_jogador1, pontuacaoRodadaJogador1, id_jogador2, pontuacaoRodadaJogador2);

        if (Integer.parseInt(pontuacaoJogadores[0]) < 100 && Integer.parseInt(pontuacaoJogadores[1]) < 100) {
            sessaoJogo.getNovoBaralho(Integer.parseInt(id_sessao));
            Mao NovaMaoJogador1 = sessaoJogo.NovaMao(Integer.parseInt(id_sessao));
            Mao NovaMaoJogador2 = sessaoJogo.NovaMao(Integer.parseInt(id_sessao));
            if (id_jogador1 == Integer.parseInt(idDerrotado)) { //pos 0 = vencedor //pos 1 = derrotado
                servidor.distribuiMensagem(cs.getLoginPS(), "avisaPontuacaoDerrotado;" + cs.getLogin() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoDerrotado + ";" + cs.getOponente() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoVencedor + ";" + NovaMaoJogador2.toString() + ";" + id_sessao + ";" + cs.getLoginId() + ";" + cs.getOponenteId());
                servidor.distribuiMensagem(cs.getOponentePS(), "avisaPontuacaoVencedor;" + cs.getOponente() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoVencedor + ";" + cs.getLogin() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoDerrotado + ";" + maoDerrotado + ";" + NovaMaoJogador1.toString() + ";" + id_sessao + ";" + cs.getOponenteId() + ";" + cs.getLoginId());
            } else {
                servidor.distribuiMensagem(cs.getLoginPS(), "avisaPontuacaoDerrotado;" + cs.getLogin() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoDerrotado + ";" + cs.getOponente() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoVencedor + ";" + NovaMaoJogador1.toString() + ";" + id_sessao + ";" + cs.getLoginId() + ";" + cs.getOponenteId());
                servidor.distribuiMensagem(cs.getOponentePS(), "avisaPontuacaoVencedor;" + cs.getOponente() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoVencedor + ";" + cs.getLogin() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoDerrotado + ";" + maoDerrotado + ";" + NovaMaoJogador2.toString() + ";" + id_sessao + ";" + cs.getOponenteId() + ";" + cs.getLoginId());
            }
        } else {
            if (id_jogador1 == Integer.parseInt(idDerrotado)) {
                servidor.distribuiMensagem(cs.getLoginPS(), "acabouJogo_derrotado;" + cs.getLogin() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoDerrotado + ";" + cs.getOponente() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoVencedor);
                servidor.distribuiMensagem(cs.getOponentePS(), "acabouJogo_vencedor;" + cs.getOponente() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoVencedor + ";" + cs.getLogin() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoDerrotado + ";" + maoDerrotado);
            } else {
                servidor.distribuiMensagem(cs.getLoginPS(), "acabouJogo_derrotado;" + cs.getLogin() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoDerrotado+ ";" + cs.getOponente() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoVencedor);
                servidor.distribuiMensagem(cs.getOponentePS(), "acabouJogo_vencedor;" + cs.getOponente() + ";" + pontuacaoJogadores[0] + ";" + pontuacaoVencedor+ ";" + cs.getLogin() + ";" + pontuacaoJogadores[1] + ";" + pontuacaoDerrotado + ";" +maoDerrotado);
            }
            servidor.removeSessaoJogo(id_sessao);
            servidor.removeClientes(id_jogador1, id_jogador2);
        }
    }

    public void completaJogo(String id, String posicao, String tipos, String NomeCarta) {
        ClienteServidor cs = this.servidor.clientes.get(Integer.parseInt(id));
        servidor.distribuiMensagem(cs.getOponentePS(), "completaJogo;" + posicao + ";" + tipos + ";" + NomeCarta);

    }

    public void finalizaConexao(Integer id) {
        ClienteServidor cs = this.servidor.clientes.get(id);
        this.servidor.distribuiMensagem(cs.getOponentePS(), "finalizaConexao;");
    }

    public void finaliza() {
        this.stop();
    }

}
