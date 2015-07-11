/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author almutasemalsayed
 */
public class ChatClientWithServer {
   private boolean inPrivateSession; // if yes, the InputThread will block
                                     // allowing another thread to handle the input
   Socket socket;
   static int serverPort = 8190;
   BufferedReader fromServer;
   PrintWriter  toServer;

   public ChatClientWithServer() {
      try {
         joinChatRoom();
         Thread input = new InputThread(this, toServer);
         input.start();
         startServer();
         printMessages();
      } catch (IOException ex) {
         System.out.println("Cannot establish connection with the server.\n"+ex.getMessage());
      }
   }

   public boolean isInPrivateSession() {
      return inPrivateSession;
   }

   public void joinChatRoom() throws IOException {
      socket = new Socket("192.168.1.100", serverPort); // the server ip
      fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      toServer = new PrintWriter(socket.getOutputStream(), true);
   }

   public void printMessages() throws IOException {
      String message = "";
      while (message != null) {

         if(message.contains("ip:")) {
            inPrivateSession = true;
            // means that i have requested an ip for a user
            System.out.println(message);
            String host = message.substring(message.indexOf("ip:")+3);
            String myName = message.substring(0, message.indexOf("ip:"));
            PrivateChatInput privateChat = new PrivateChatInput(myName, host);
            inPrivateSession = false;
            this.notifyAll();
            System.out.println("Back from private session!");
         }
         else {
            System.out.println(message);
         }
         
         message = fromServer.readLine();
      }
   }

   private void startServer() {
      new PrivateChatServer().start();
   }
}
