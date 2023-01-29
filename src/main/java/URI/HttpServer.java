package URI;

import java.net.*;
import java.io.*;
import java.security.PublicKey;

public class HttpServer {
    public static URLreader busquedaAPI;
    public static CacheAPI cache;
    public static void main(String[] args) throws IOException {
        cache = new CacheAPI<>(1000,100,500);
        Socket clientSocket = null;
        busquedaAPI = new URLreader();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        int cont = 0;
        while(running) {
            try {
                System.out.println("RECIBIENDO PETICIONES");
                clientSocket = serverSocket.accept();
                if(cont == 0){
                    front(clientSocket);
                    cont +=1;
                }
                busqueda(clientSocket);
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
    }
    public static void front(Socket clientSocket) throws IOException {
        String outputLine;
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type:  text/html\r\n" + "\r\n" + htmlForm();
        out.println(outputLine);
    }
    public static void busqueda(Socket clientSocket) throws IOException {
        String res;
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String[] datos = in.readLine().split(" ");
        if(datos[0].equals("POST")){
            if(cache.get(datos[1].substring(1)) != null){
                res = "Sacado de cache --> " + (String) cache.get(datos[1].substring(1));
                System.out.println(res);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type:  application/json\r\n" + "\r\n" + res;
                out.println(outputLine);
            }
            else {
                res = busquedaAPI.getAPI(datos[1].substring(1));
                System.out.println(res);
                cache.put(datos[1].substring(1), res);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type:  application/json\r\n" + "\r\n" + res;
                out.println(outputLine);
            }
        }

        in.close();
    }
    public static String htmlForm(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "\n" +
                "        <h1>INGRESE EL NOMBRE DE LA PELICULA</h1>\n" +
                "        <form action=\"/hellopost\">\n" +
                "            <label for=\"postname\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n" +
                "        </form>\n" +
                "        \n" +
                "        <div id=\"postrespmsg\"></div>\n" +
                "        \n" +
                "        <script>\n" +
                "            function loadPostMsg(name){\n" +
                "                let url = \"\" + name.value;\n" +
                "\n" +
                "                fetch (url, {method: 'POST'})\n" +
                "                    .then(x => x.text())\n" +
                "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }
}
