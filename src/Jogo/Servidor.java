package Jogo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    Map<Integer, Sessao> sessoes = new HashMap<>();
    Mao maojogador1;
    Mao maojogador2;
    Map<Integer, ArrayList<Carta>> baralhos = new HashMap<>();

    public static void main(String[] args) throws IOException {
        new Servidor(12345).executa();
    }

    private final int porta = 12345;
    private static int idCliente = 0;

    public Map<Integer, ClienteServidor> clientes;
    public Map<Integer, Socket> conexoes;
    public Map<Integer, TrataCliente> tratadores;

    public Servidor() {

    }

    public Servidor(int porta) {
        clientes = new HashMap<>();
        conexoes = new TreeMap<>();
        tratadores = new TreeMap<>();
    }

    public void executa() throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");
        System.out.println("Ip: " + InetAddress.getLocalHost().getHostAddress());

        GerenciaConexoes gerenciador = new GerenciaConexoes(conexoes, clientes, tratadores);
        gerenciador.start();

        while (true) {
            // aceita um cliente
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

            // adiciona saida do cliente � lista
            PrintStream impressorea_cliente = new PrintStream(cliente.getOutputStream());
            ClienteServidor novo_cliente = new ClienteServidor();
            novo_cliente.setLoginPS(impressorea_cliente);
            Servidor.idCliente++;
            novo_cliente.setLoginId(this.idCliente);

            //this.clientes.put(this.idCliente, novo_cliente);
            // cria tratador de cliente numa nova thread
            TrataCliente tc = new TrataCliente(cliente, cliente.getInputStream(), this, novo_cliente);

            gerenciador.adicionaTratador(Servidor.idCliente, tc);
            gerenciador.adicionaCliente(Servidor.idCliente, novo_cliente);
            gerenciador.adicionaConexao(Servidor.idCliente, cliente);

            tc.start();
        }
    }

//    boolean GetProximaSessaoLivre(ClienteServidor cliente) {
//        Set conjunto_de_chaves = this.sessoes.keySet();
//        Iterator iterador_chaves = conjunto_de_chaves.iterator();
//
//        while (iterador_chaves.hasNext()) {
//            Integer chave_atual = (Integer) iterador_chaves.next();
//            Sessao nova_sessao;
//            if ((nova_sessao = this.sessoes.get(chave_atual)).jogador_2 == null) {
//                Sessao sessao_selecionada = new Sessao(nova_sessao.getId_sessao(), nova_sessao.getJogador_1(), cliente);
//                this.sessoes.put(chave_atual, sessao_selecionada);
//                return true;
//            }
//        }
//        return false;
//    }
    public Sessao GetNovaSessao(Integer id_sessao, ClienteServidor player_um, ClienteServidor player_dois) {
        Sessao sessao_selecionada = new Sessao(id_sessao, player_um, player_dois);
        this.sessoes.put(id_sessao, sessao_selecionada);
        return sessao_selecionada;
    }

    public Sessao getSessao(String id_sessao) {
        return sessoes.get(Integer.parseInt(id_sessao));
    }

    public String getIPPc() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void removeSessaoJogo(String id_sessao) {
        sessoes.remove(Integer.parseInt(id_sessao));
    }

    public void removeClientes(int id_cliente1, int id_cliente2) {
        clientes.remove(id_cliente1);
        clientes.remove(id_cliente2);
    }

    public void distribuiMensagem(PrintStream cliente, String msg) {
        System.out.println(msg);
        cliente.println(msg);
    }

}
