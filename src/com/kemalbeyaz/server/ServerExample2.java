package com.kemalbeyaz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2322);
        Socket clientSocket = null;
        System.out.println("Server ayakta... " + serverSocket);

        try {

            while (true) {
                // Sürekli bağlantı bekleniyor, clientSocket kapatılınca...
                clientSocket = serverSocket.accept();
                System.out.println("Yeni bir bağlantı kuruldu. " + clientSocket);

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String str = in.readLine();
                    while (str != null) {
                        System.out.println(str);

                        if(str.equals("close"))
                            clientSocket.close();

                        if(str.equals("stop")){
                            clientSocket.close();
                            serverSocket.close();
                        }

                        out.println("Echo: " + str);
                        str = in.readLine();
                    }

                } catch (IOException ex) {
                    System.out.println("Bir hata meydaan geldi. " + ex.getMessage());
                } finally {
                    clientSocket.close();
                }

            }
        } finally {
            serverSocket.close();
        }
    }
}
