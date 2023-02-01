package URI;

import java.io.*;
import java.net.*;

public class URLreader {
    /**
     * Funcion generada para traer los datos de la API proporcionando el valor de busqueda
     * @param busqueda pelicula que se buscara
     * @return inputLine JSON con la respuesta
     * @throws MalformedURLException Exception
     */
    public static String getAPI(String busqueda) throws MalformedURLException {
        String uri = "https://www.omdbapi.com/?t=" + busqueda +"&apikey=ec2a8fe1";
        URL api = new URL(uri);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(api.openStream()))) {
            String inputLine = reader.readLine();
            return inputLine;
        } catch (IOException x) {
            System.err.println(x);
        }
        return null;
    }
}
