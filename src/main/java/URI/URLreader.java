package URI;

import java.io.*;
import java.net.*;

public class URLreader {
    public static void main(String[] args) throws Exception {
        URL myurl = new URL("https://david.escuelaing.edu.co:80/index.html?hola=mundo#2023-1");
        URL google = new URL("http://www.google.com/");
        System.out.println("Protocolo: " + myurl.getProtocol());
        System.out.println("Authority: " + myurl.getAuthority());
        System.out.println("Host: " + myurl.getHost());
        System.out.println("Port: " + myurl.getPort());
        System.out.println("Path: " + myurl.getPath());
        System.out.println("Query: " + myurl.getQuery());
        System.out.println("File: " + myurl.getFile());
        System.out.println("Ref: " + myurl.getRef());
        System.out.println("-------------------------------------------------------------------------");
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(google.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
