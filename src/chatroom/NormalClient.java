package chatroom;

import chatroom.client.InputThread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * This class is for testing only. Because running the ChatClientWithServer
 * twice raises an exception due to two servers listening on the same port.
 * @author almutasemalsayed
 */
public class NormalClient {
   Socket socket;
   static int serverPort = 8190;
   BufferedReader fromServer;
   PrintWriter  toServer;

   public NormalClient() {
      try {
         joinChatRoom();
         Thread input = new InputThread(null, toServer);
         input.start();
         printMessages();
      } catch (IOException ex) {
         System.out.println("Cannot establish connection with the server.\n"+ex.getMessage());
      }
   }

   public void joinChatRoom() throws IOException {
      socket = new Socket("192.168.1.100", serverPort); // the server ip
      fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      toServer = new PrintWriter(socket.getOutputStream(), true);
   }

   public void printMessages() throws IOException {
      String message = "";
      while (message != null) {
         System.out.println(message);
         message = fromServer.readLine();
      }
   }

   public static void main(String[] args) {
      new NormalClient();
   }
}
