/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroom.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class ChatServer {

   private static LinkedList<Socket> clientList = new LinkedList<Socket>();
   private static HashMap<String, String> clientData = new HashMap<String, String>();

   public ChatServer() throws IOException {
      System.out.println("Initializing chat room ...");
      int port = 8190;
      ServerSocket listener = new ServerSocket(port);

      while(true) {

         Socket client = listener.accept();
         System.out.println("Client connected ...");
         ChatHandler handler = new ChatHandler(client);
         handler.start();

         clientList.add(client);
         listClients();
      }
   }

   static synchronized void forward(String message, String name) throws IOException {
      // sends the message to every client including the sender.
      Socket s;
      PrintWriter p;

      for (int i = 0; i < clientList.size(); i++) {
         s = clientList.get(i);
         p = new PrintWriter(s.getOutputStream(), true);
         p.println(name + ": " + message);
      }
   }

   // added by me
   static synchronized void registerClient(String name, InetAddress address) {
      String ip = address.getHostAddress();
      clientData.put(name, ip);
   }

   // added by me
   static synchronized String getClientIP(String name) {
      if(clientData.containsKey(name)) {
         return clientData.get(name);
      }
      return null;
   }

   // added by me
   static synchronized boolean containsName(String name) {
      return clientData.containsKey(name);
   }

   static synchronized void remove(Socket client) {
      System.out.println("Client disconnected.");
      clientList.remove(client);
   }

   // added by me
   static synchronized void removeClientData(String name) {
      if(clientData.containsKey(name))
         clientData.remove(name);
   }


   // for testing
   static void listClients() {
      if (clientData != null && !clientData.isEmpty()) {
         for (String clientName : clientData.keySet()) {
            System.out.println(clientName + " : " + clientData.get(clientName));
         }
      }
   }
}
