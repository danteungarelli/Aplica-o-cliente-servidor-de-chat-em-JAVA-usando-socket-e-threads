
import java.net.Socket;
import java.io.*;
import java.net.SocketAddress;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dumga
 */
public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    
    public ClientSocket(Socket socket) throws IOException{
        this.socket= socket;
        System.out.println("Cliente "+  socket.getRemoteSocketAddress());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//receber mensagens no servidor
        this.out = new PrintWriter(socket.getOutputStream(),true);
        //essa função serve como um fluxo de saída de dados, serve pra mandar informações para o servidor
    }
    
    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }
    
    public void close(){
        try{
        in.close();
        out.close();
        socket.close();
        }catch(IOException e){
            System.out.println("Erro ao fechar socket "+e.getMessage());
        }
    }
    
    public String getMessage(){
        try{
        return in.readLine();
        }catch(IOException e){
            return null;
        }
    }
    
    public boolean sendMsg(String msg){
        out.println(msg);
        return !out.checkError();
    }
    
}
