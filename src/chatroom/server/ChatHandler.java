/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroom.server;

import chatroom.server.ChatServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almutasemalsayed
 */
class ChatHandler extends Thread {

   private BufferedReader in;
   private PrintWriter out;
   private Socket toClient;
   private String name;

   ChatHandler(Socket client) {
      toClient = client;
   }

   public void run() {
      try {
         openStreams();
         out.println("******** Welcome *********");
         out.flush();
         out.println("Type the user's name followed by private to start a private conversation with the user.");
         out.flush();
         out.println("Type BYE to end or press Quit.");
         out.flush();
         out.println("What is your name? ");
         out.flush();
         name = in.readLine();

         // add the client's name and address to the server
         ChatServer.registerClient(name, toClient.getInetAddress());
         ChatServer.forward(name + " has joined the discussion.", "Announcement");

         while (true) {
            String s = in.readLine();
            if(s.startsWith("BYE")) {
               ChatServer.forward(name + " has left the discussion.", "Announcement");
               break;
            }
            if (s.contains("private")) {
               String privateName = s.substring(0, s.indexOf(" "));
               System.out.println("A client wants to start a private chat with "+privateName);
               if(ChatServer.containsName(privateName)) {
                  String ip = ChatServer.getClientIP(privateName);
                  out.println(name+"ip:"+ip);
                  out.flush();
               }
            } else {
               ChatServer.forward(s, name);
            }
         }

         ChatServer.remove(toClient);
         // remove the client's name and address
         ChatServer.removeClientData(name);
         toClient.close();
      } catch (IOException ex) {
         Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   private void openStreams() throws IOException {
      in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
      out = new PrintWriter(toClient.getOutputStream(), true);
   }

}
