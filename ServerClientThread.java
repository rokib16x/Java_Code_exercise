//Server Class
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Server {

    public static void main(String[] args) {
        ArrayList<Socket> client = new ArrayList<>();
        Hashtable<Socket,String > clientList = new Hashtable<>();
        ServerSocket serverSocket;
        try {
            serverSocket=new ServerSocket(8584);
            System.out.println("The server has been established");
            while (true){
                Socket socket = serverSocket.accept();
                client.add(socket);
                ServerThread ThreadServer = new ServerThread(socket, client, clientList);
                ThreadServer.start();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


//ServerThread Class
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ServerThread extends Thread {

    private Socket socket;
    private ArrayList<Socket> client;
    private Hashtable<Socket, String> clientList;

    public ServerThread(Socket socket, ArrayList<Socket> client, Hashtable<Socket, String> clientList) {
        this.socket = socket;
        this.client = client;
        this.clientList = clientList;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String output = input.readLine();
                if (output.equals("exit")) {
                    throw new SocketException();
                }
                if (!clientList.containsKey(socket)) {
                    String[] st = output.split(":", 2);
                    clientList.put(socket, st[0]);
                    System.out.println(st[0] + st[1]);
                    printShow(socket, st[0] + st[1]);
                } else {
                    System.out.println(output);
                    printShow(socket, output);
                }
            }
        } catch (SocketException e) {
            String printMessage = clientList.get(socket) + " the server has been disconnected";
            System.out.println(printMessage);
            printShow(socket, printMessage);
            client.remove(socket);
            clientList.remove(socket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printShow(Socket send, String output) {
        Socket socket;
        PrintWriter writer;
        int i = 0;
        while (i < client.size()) {
            socket = client.get(i);
            i++;
            try {
                if (socket != send) {
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(output);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}


//Client class

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Type 'exit' to logout");
        String name;
        String reply;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Client Name: ");
        reply= sc.nextLine();
        name=reply;
        System.out.println("You joined the server as Client");
        try (Socket socket = new Socket("localhost",8584)){
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);

            ClientThread clientThread = new ClientThread(socket);
            new Thread(clientThread).start();
            printWriter.println(reply+" :has joined the Server.");
            do {
                String message = (name+" : ");
                reply = sc.nextLine();
                if (reply.equals("exit")){
                    printWriter.println("exit");
                    break;
                }
                printWriter.println(message+reply);
            }while (!reply.equals("exit"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

//ClientThread class
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable{
    private Socket socket;
    private BufferedReader bufferedReader;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
        this.bufferedReader = new BufferedReader(reader);
//        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            while (true){
                String message = bufferedReader.readLine();
                System.out.println(message);
            }
        }catch (SocketException e){
            System.out.println("You disconnected from the server.");
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
