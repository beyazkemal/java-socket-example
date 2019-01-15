package com.kemalbeyaz.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample3 extends Thread {
    public static final int PORT = 9090;
    public Socket socket = null;

    public ServerExample3(Socket s){
        this.socket = s;
    }

    public void run(){
        try{
            System.out.println("Bağlantı alan thread: " + this.getName() + " " + socket);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

            String msg = in.readLine();
            while (msg != null){
                System.out.println(msg);
                out.println("Echo: "  + msg);
                msg = in.readLine();
            }
        } catch (IOException ex){
            System.out.println("Hata meydana geldi. " + this.getName() + " " + ex.getMessage() );
        } finally {
            System.out.println("Bağlantı kapatılıyor...");
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Bağlantı kapatılamadı. " + this.getName() );
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Sunucu başladı. " + serverSocket);

        try {
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Bağlantı kuruldu. " + clientSocket);
                clientSocket.setSoTimeout(5000); // Bağlantıda bir hareket olmazsa kapat...
                new ServerExample3(clientSocket).start(); // İş parçağına aktarılıyor.

            }
        } catch (Exception ex){
            System.out.println("Main metodunda hata oldu. " + ex.getMessage());
        }finally {
            serverSocket.close();
        }

    }
}
