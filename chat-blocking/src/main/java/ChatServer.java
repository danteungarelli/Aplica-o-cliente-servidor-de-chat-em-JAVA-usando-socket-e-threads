
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dumga
 */
public class ChatServer {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clients= new LinkedList<>();
    
    public void start() throws IOException{
        System.out.println("Servidor iniciado na porta "+ PORT);
        serverSocket= new ServerSocket(PORT);
        clientConnectionLoop();
    }
    
    
      private void clientConnectionLoop() throws IOException {
        while(true){
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept()) ;
            clients.add(clientSocket);
            new Thread(() -> clientMessageLoop(clientSocket)).start();
           
         }
      }
      
      private void clientMessageLoop(ClientSocket clientSocket){
          String msg;
          try{
              while(( msg = clientSocket.getMessage())!=null){
                  
                System.out.printf("Msg recebida do cliente %s: %s\n",clientSocket.getRemoteSocketAddress(),msg );
                sendMsgtoAll(clientSocket, msg);
              }
          }finally{
              clientSocket.close();
          }
      }
      
      private void sendMsgtoAll(ClientSocket sender, String msg){ //metodo para encaminhar a mensagem a todos
          for(ClientSocket clientSocket: clients ){
              if(!sender.equals(clientSocket))
               clientSocket.sendMsg(msg);
          }
      }
     
    public static void main(String[] args) {
        
        try {
            ChatServer server = new ChatServer();
            server.start();
        }catch (IOException ex){
            System.out.println("Erro ao inicar servidor" + ex.getMessage());
        }
        System.out.println("Servidor finalizado"); 
    }

}  