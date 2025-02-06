import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    // Componentes para la conexión con el cliente
    private static int puerto = 5000;
    private static double resultado;

    /**
     * Método que realiza la operación según el operador
     * @param num1 primer operador de tipo double
     * @param num2 segundo operador de tipo double
     * @param operador
     * @return resultado de la operación de tipo double
     */
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
                    throw new IllegalArgumentException("No se puede dividir entre 0");
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

                //Leemos la petición del cliente (mensajes)
                String peticion = in.readLine();
                System.out.println("Petición del cliente: "+peticion);

                // Realizamos la operación del cliente
                String[] partes = peticion.split("(?<=[-+*/])|(?=[-+*/])");
                // Si el formato no es correcto, se envía un mensaje de error
                if (partes.length != 3) {
                    out.println("Error: Formato incorrecto. Usa num1 operador num2");
                    continue; // Sigue esperando nuevas peticiones
                }

                double num1 = Double.parseDouble(partes[0].trim());
                double num2 = Double.parseDouble(partes[2].trim());
                String operador = partes[1].trim();

                //Enviamos la respuesta al cliente
                resultado = resultadoOperacion(num1, num2, operador);
                out.println(resultado);
                out.flush();    //Limpiamos el buffer
                System.out.println("Resultado: "+resultado+" enviado al cliente");

                //Cerramos la conexión y los buffers
                in.close();
                out.close();
                socket.close();
                System.out.println("Servidor desconectado");
            }
        }catch(Exception e){
            System.out.println("Error al iniciar el servidor"+e.getMessage());
        }
    }
}
