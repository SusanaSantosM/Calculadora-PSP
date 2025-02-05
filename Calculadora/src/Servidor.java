import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    // Componentes para la conexión con el cliente
    private static int puerto = 5000;
    private static double resultado;

    public static double resultadoOperacion(double num1, double num2, String operador){
        // Según el operador, se cumple cada caso
        switch (operador){
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0){
                    try {
                        throw new Exception("No se puede dividir entre 0");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Operador no válido");
        }
    }

    public static void main(String[] arg){
        try{
            //Creamos el socket del servidor
            ServerSocket socketServidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while(true){
                //Aceptamos conexiones con el cliente
                Socket socket = socketServidor.accept();
                System.out.println("Cliente conectado "+socket.getInetAddress());

                //Creamos los buffers de entrada y salida
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                //Leemos la petición del cliente
                String peticion = in.readLine();
                System.out.println("Petición del cliente: "+peticion);

                //Enviamos la respuesta al cliente
                out.println("Hola, cliente");

                //Cerramos la conexión
                socket.close();
                System.out.println("Cliente desconectado");
            }
        }catch(Exception e){
            System.out.println("Error al iniciar el servidor"+e.getMessage());
        }
    }
}
