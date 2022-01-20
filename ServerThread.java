package AssignmentTwo.Last;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    int port;
    ServerSocket serverSocket =null;
    Socket client=null;
    ExecutorService pool = null;
    int clientCount =0;

    public static void main(String[] args) throws IOException {
        Server server=new Server(8080);
        server.startServer();
    }

    Server(int port){
        this.port=port;
        pool = Executors.newFixedThreadPool(5);
    }

    public void startServer() throws IOException {

        serverSocket =new ServerSocket(8080);
        System.out.println("Server Booted");
        System.out.println("Any client can stop the server by sending -1");

        while(true) {
            client= serverSocket.accept();
            clientCount++;
            ServerThread runnable= new ServerThread(client, clientCount,this);
            pool.execute(runnable);
        }

    }

    private static class ServerThread implements Runnable {

        Server server=null;
        Socket client=null;
        BufferedReader clientInput;
        PrintStream ClientOutput;
        Scanner sc=new Scanner(System.in);
        int id;
        String s;

        ServerThread(Socket client, int count ,Server server ) throws IOException {

            this.client=client;
            this.server=server;
            this.id=count;
            System.out.println("Client "+id+" is joined");

            clientInput=new BufferedReader(new InputStreamReader(client.getInputStream()));
            ClientOutput =new PrintStream(client.getOutputStream());

        }

        @Override
        public void run() {
            int x=1;
            try{
                while(true){
                    s=clientInput.readLine();

                   System. out.print("Client : "+id+" :"+s+"\n");
                    System.out.print("Server : ");

                    s=sc.nextLine();

                    if (s.equalsIgnoreCase("bye"))
                    {
                        ClientOutput.println("BYE");
                        x=0;
                        System.out.println("Connection ended by server");
                        break;
                    }
                    ClientOutput.println(s);
                }


                clientInput.close();
                client.close();
                ClientOutput.close();
                if(x==0) {
                    System.out.println( "Server cleaning up." );
                    System.exit(0);
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }


        }
    }

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {

    public static void main(String args[]) throws Exception {

        Socket socket=new Socket("127.0.0.1",8080);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream printStream=new PrintStream(socket.getOutputStream());
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));

        String Str;
        while (true) {
            System.out.print("Client : ");
            Str=reader.readLine();
            printStream.println(Str);

            if ( Str.equalsIgnoreCase("BYE") ) {
                System.out.println("Connection ended by client");
                break;
            }

            Str=bufferedReader.readLine();
            System.out.print("Server : "+Str+"\n");

        }

        socket.close();
        bufferedReader.close();
        printStream.close();
        reader.close();
    }

}