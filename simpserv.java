import java.util.* ;
import java.io.*;
import java.net.* ;


public class simpserv {
    ArrayList<PrintWriter> socklist;

    public static void main (String[] args) {
        simpserv ss = new simpserv();
        ss.go();
    }

    public class sockhand implements Runnable {
        Socket sock;
        BufferedReader reader;

        sockhand (Socket sockc) {
            try {
            sock = sockc;
            InputStreamReader sreader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(sreader);
            } catch (Exception ex) {}
        }

        public void run() {
            String message;
            try {
            while ((message = reader.readLine()) != null) {
                System.out.println("reading mes: "+message);
                tellevery(message);
            }} catch (Exception ex){}
        }
    }

    public void tellevery (String mes) {
        Iterator it = socklist.iterator();
        while (it.hasNext()) { 
            try{
            PrintWriter writer = (PrintWriter) it.next();
            writer.println(mes);
            writer.flush();
            System.out.println("otpravleno");
            System.out.println(socklist.size());
            } catch (Exception ex) {}
            }
    }

    public void go() {
        socklist = new ArrayList<PrintWriter>();
        try {
        ServerSocket ssock = new ServerSocket(4999);
        System.out.println("zapusk");
        while (true) {
            Socket sock = ssock.accept();
            PrintWriter writer = new PrintWriter(sock.getOutputStream());
            socklist.add(writer);
            System.out.println("new cl");

            Thread t = new Thread(new sockhand(sock));
            t.start();
            System.out.println("got a connection");
        } }catch (Exception ex) {}
    }

}