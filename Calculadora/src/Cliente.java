import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    //Atributos de la conexión con el servidor
    private String host = "localhost";
    private static int puerto = 5000;

    private String enviarMensajeTCP(String peticion) throws IOException {
        //Creamos la conexión con el servidor
        Socket socket = new Socket(host, puerto);

        //Buffers de entrada y salida para recibir mensajes
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        //Enviamos la petición al servidor
        out.println(peticion);

        //Recibimos la respuesta del servidor
        String respuesta = in.readLine();
        System.out.println("Respuesta del servidor: " + respuesta);

        //Cerramos la conexión
        socket.close();

        return respuesta;
    }

    public static void main (String[] args){
        Cliente cliente = new Cliente();
        try {
            cliente.enviarMensajeTCP("Hola, servidor, soy el Cliente");
        } catch (Exception e) {
            System.out.println("Error al enviar mensaje al servidor: " + e.getMessage());
        }
    }

}
