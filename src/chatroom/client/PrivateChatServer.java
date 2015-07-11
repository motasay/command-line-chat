/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will listen for clients, if one is found it will keep listening
 * for inputs through the input stream of the client's socket.
 * @author almutasemalsayed
 */
public class PrivateChatServer extends Thread {

   private Socket client;
   private BufferedReader fromClient;

   public PrivateChatServer() {
      
   }

   public void run() {
      try {
         ServerSocket listener = new ServerSocket(9999);
         while(true) {
            client = listener.accept();
            System.out.println("Incoming private chat started.");
            System.out.println("To reply in private, type the user's name then type private.");
            fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            listenForinput();
         }
      } catch (IOException ex) {
         Logger.getLogger(PrivateChatServer.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   private void listenForinput() {
      while (true) {
         try {
            String message = fromClient.readLine();
            if (message.contains("END")) {
               System.out.println(message.substring(1, message.indexOf(":")) +
                       " has ended the private chat.");
               break;
            } else {
               System.out.println("message");
            }
         } catch (IOException ex) {
            Logger.getLogger(PrivateChatServer.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
}
