import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente extends JFrame {
    // Componenete para la conexión con el servidor
    private String host = "localhost";
    private static int puerto = 5000;

    // Componentes para la interfaz
    private JPanel panelCentral;
    private JLabel textoEtq;
    private JButton[] botones;
    private int numBotones = 17;        // Número total de botones en el panel
    //Array de los botones para los números y operaciones
    private String textoBotones[] = {"=","7","8","9","/","4","5","6","*","1","2","3","-","C","0",".","+"};
    //Posicionamos en el eje x e y cada botón con un array
    private int xBotones[] = {15, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210};
    private int yBotones[] = {90, 155, 155, 155, 155, 220, 220, 220, 220, 285, 285, 285, 285, 350, 350, 350, 350};
    //Array de los botones para los números
    private int numerosBotones[] = {1,2,3,5,6,7,9,10,11,14};
    //Array de los botones para las operaciones
    private String operacionesBotones[] = {"/","*","-","+","="};
    //Indicador que se termino de escribir un número
    private boolean nuevoNumero = true;
    //Indicador de uso del punto decimal
    private boolean puntoDecimal = false;
    // String para almacenar la operación
    // Esto también se enviará al servidor para el resultado
    public String operacion = "";
    // Almacenar los números de las operaciones
    public double num1 = 0;
    public double num2 = 0;
    public double resultado = 0;

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
