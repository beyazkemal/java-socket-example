package com.kemalbeyaz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {
    private ServerSocket serverSocket;
    private Socket clientScoket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(int port){
        try{
            serverSocket = new ServerSocket(port);
                System.out.println("Server hazır. Bağlantı bekleniliyor... Port: " + port);
            clientScoket = serverSocket.accept();
                System.out.println("Servera bir istemci bağlandı. ");
        } catch (IOException ex){ System.out.println("İstemci ile bağlantı kurulurken bir hata oluştu. " + ex.getMessage()); }

        try {
            // Clienta gönderilecek bilgiler için de PrintWriter ile sarmallanıyor. TRUE ile tamponun dolmasının beklenmeden gönderilmesi sağlanıyor.
            out = new PrintWriter(clientScoket.getOutputStream(), true);

            // Clientten gelecek olan bilgiler alınıp BufferedReader ile sarmallanıyor.
            in = new BufferedReader(new InputStreamReader(clientScoket.getInputStream()));
        } catch (IOException ex){ ex.getMessage(); }
    }

    public void disconnect() throws IOException{
        clientScoket.close();
        serverSocket.close();
    }

    public void readMessage(){
        try {
            String msg = in.readLine();

            while (msg != null) {
                if(msg.equals("exit") || msg.equals("stop"))
                    disconnect();

                System.out.println(msg);

                // İstemciden alınan mesaj istemciye geri gönderiliyor.
                out.println("Echo: " + msg);
                msg = in.readLine();
            }
        }catch (IOException ex){ ex.getMessage(); }
    }

    public static void main(String[] args) {
        ServerExample server = new ServerExample();
        server.connect(2323);
        server.readMessage();
    }
}
