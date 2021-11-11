package Jogo;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class GerenciaConexoes extends Thread {
    private Map<Integer, Socket> conexoes;
    private Map<Integer, ClienteServidor> clientes;
    private Map<Integer, TrataCliente> tratadoresClientes;
    
    public GerenciaConexoes(Map<Integer, Socket> conexoes, Map<Integer, ClienteServidor> clientes, Map<Integer, TrataCliente> tratadoresClientes) {
        this.conexoes = conexoes;
        this.clientes = clientes;
        this.tratadoresClientes = tratadoresClientes;
    }
    
    public synchronized void adicionaConexao(Integer id, Socket conexao) {
        this.conexoes.put(id, conexao);
    }
    
    public synchronized Socket getConexao(Integer id) { 
        return this.conexoes.get(id);
    }
    
    private synchronized void removeConexao(Integer id) {
        this.conexoes.remove(id);
    }
    
    public synchronized void adicionaCliente(Integer id, ClienteServidor cliente) {
        this.clientes.put(id, cliente);
    }
    
    public synchronized ClienteServidor getCliente(Integer id) { 
        return this.clientes.get(id);
    }    
    
    private synchronized void removeCliente(Integer id) {
        this.clientes.remove(id);
    }
    
    public synchronized void adicionaTratador(Integer id, TrataCliente tratador) {
        this.tratadoresClientes.put(id, tratador);
    }
    
    public synchronized TrataCliente getTratador(Integer id) { 
        return this.tratadoresClientes.get(id);
    }
        
    private synchronized void removeTratador(Integer id) {
        this.tratadoresClientes.remove(id);
    }
    
    public void run() {
        
        while( true ) {
            Set<Integer> conjuntoConexoes = this.conexoes.keySet();
            Set<Integer> clientesFinalizados = new TreeSet<>();
            for(Integer id : conjuntoConexoes) {
                Socket conexao = this.getConexao(id);
                if(conexao.isClosed()) {
                    TrataCliente tratador = this.getTratador(id);
                    tratador.finalizaConexao(id);
                    
                    clientesFinalizados.add(id);
                    clientesFinalizados.add(this.getCliente(id).getOponenteId());
                }
            }
            
            for(Integer id : clientesFinalizados) {
                
                try {
                    this.getConexao(id).close();
                    this.removeConexao(id);

                    this.getTratador(id).finaliza();
                    this.removeTratador(id);

                    this.removeCliente(id);
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                }
                
            }
        }
        
    }

    
}
