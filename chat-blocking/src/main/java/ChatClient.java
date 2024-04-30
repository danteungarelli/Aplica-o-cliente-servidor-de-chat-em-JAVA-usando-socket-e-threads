
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dumga
 */
public class ChatClient implements Runnable{
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private ClientSocket clientSocket;
    private Scanner scanner;
    private BufferedWriter out;
    
    
    public ChatClient(){
        scanner= new Scanner(System.in);
    }
    
    public void start() throws IOException{
        clientSocket = new ClientSocket(new Socket (SERVER_ADDRESS, ChatServer.PORT));
        
        System.out.println("Cliente conectado ao servidor em " +SERVER_ADDRESS+" Porta: " + ChatServer.PORT);
        new Thread(this).start();
        messageLoop();
    }
    
    @Override
    public void run(){
        String msg;
        while((msg = clientSocket.getMessage())!=null){
        System.out.printf("Msg recebida do servidor %s: \n",msg);
        }
    }
    
    public void messageLoop() throws IOException{
        String msg;
        do{
            System.out.print("Digite uma mensagem ou Sair para finalizar o processo: ");
            msg=scanner.nextLine();
            clientSocket.sendMsg(msg);
            clientSocket.getMessage();                        
            
        }while(!msg.equalsIgnoreCase("Sair"));
        
    }
    
    public static void main(String[] args){
        
        try {
            ChatClient client = new ChatClient();
            client.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar client "+ ex.getMessage());
        }
        System.out.println("Cliente finalizado");
}
}
