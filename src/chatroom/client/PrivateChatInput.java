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
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almutasemalsayed
 */
public class PrivateChatInput {

   String myName;
   Socket otherClient;
   private PrintWriter toClient;

   PrivateChatInput(String name, String otherClientHost) {
      
      try {
         this.otherClient = new Socket(otherClientHost, 9999);
         myName = name;
         startConversation();
      } catch (UnknownHostException ex) {
         Logger.getLogger(PrivateChatInput.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
         Logger.getLogger(PrivateChatInput.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   private void startConversation() {
      System.out.println("Starting a private chat session. To end, type END");
      InputStreamReader reader = new InputStreamReader(System.in);
      BufferedReader input = new BufferedReader(reader);
      try {
         String message = input.readLine();
         do {
            toClient.println(message);
            message = input.readLine();
            System.out.println("PrivateChatInput");
         } while (message != "END");

      } catch (IOException e) {
         System.out.println("Trouble contacting the chat room.");
      }
      //Scanner input = new Scanner(System.in);
      //String message = null;
//      while(input.hasNext()) {
//         System.out.println("startConversation()");
//         message = input.nextLine();
//         toClient.println("*"+myName+": "+message);
//         toClient.flush();
//         if(message.contains("END")) {
//            break;
//         }
//      }
   }
}
