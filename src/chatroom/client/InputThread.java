/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almutasemalsayed
 */
public class InputThread extends Thread{

   private ChatClientWithServer client;
   private PrintWriter toServer;

   public InputThread(ChatClientWithServer c, PrintWriter to) {
      toServer = to;
      client = c;
   }

   public void run() {
      InputStreamReader reader = new InputStreamReader(System.in);
      BufferedReader input = new BufferedReader(reader);
      try {
         String message = input.readLine();
         do {
            if (client != null) {
               while (client.isInPrivateSession()) {
                  try {
                     this.wait();
                  } catch (InterruptedException ex) {
                     Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
                  }
               }
            }
            toServer.println(message);
            message = input.readLine();
            System.out.println("InputThread");
         } while (message != "BYE");

      } catch (IOException e) {
         System.out.println("Trouble contacting the chat room.");
      }
   }

}
