package URI;
import java.io.*;
import java.net.*;

public class URLreader {
	public static void main(String[] args) throws Exception {
		URL myurl = new URL("http://david.escuelaing.edu.co:80/");
		System.out.println("Protocolo: " + myurl.getProtocol());
	}
}
