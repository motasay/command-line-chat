/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroom.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almutasemalsayed
 */
public class Main {
   public static void main(String[] args) {
      try {
         new ChatServer();
      } catch (IOException ex) {
         Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}
