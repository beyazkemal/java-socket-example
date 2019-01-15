package com.kemalbeyaz.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientExample {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String ip, int port){
        try {
            clientSocket = new Socket(ip, port);

            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException ex){
            System.out.println("Bağlanırken bir hata meydana geldi. ");
            ex.getMessage(); }
    }

    public void disConnect(){
        try {
            clientSocket.close();
        } catch (IOException ex) { ex.getMessage();}
    }

    public String sendMessage(String mesaj) {
        try {
            out.println(mesaj);
            return in.readLine();
        } catch (IOException ex) { ex.getMessage();}
        return null;
    }

    public static void main(String[] args) {
        ClientExample client = new ClientExample();
        client.connect("127.0.0.1",2323);
        String response = client.sendMessage("Hey server!");

        if(response != null)
            System.out.println(response);
        else
            System.out.println("Hata oldu galiba. :)");

        client.disConnect();
    }



}
