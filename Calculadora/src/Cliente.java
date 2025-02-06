import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
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
    private String textoBotones[] = {"RESULTADO","7","8","9","/","4","5","6","*","1","2","3","-","C","0",".","+"};
    //Posicionamos en el eje x e y cada botón con un array
    private int xBotones[] = {15, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210, 15, 80, 145, 210};
    private int yBotones[] = {90, 155, 155, 155, 155, 220, 220, 220, 220, 285, 285, 285, 285, 350, 350, 350, 350};
    //Array de los botones para los números y operadores según su posición
    private int numerosBotones[] = {1,2,3,5,6,7,9,10,11,14};
    private int operacionesBotones[] = {4,8,12,16};
    //Indicador que se termino de escribir un número
    private boolean nuevoNumero = true;
    //Indicador de uso del punto decimal
    private boolean puntoDecimal = false;
    // String para almacenar la operación
    public String operacion = "";       // Esto también se enviará al servidor para el resultado
    // Almacenar los números de las operaciones
    public double num1 = 0;
    public double num2 = 0;
    public double resultado = 0;

    public Cliente(){
        //Inicializamos los componentes
        panelCentral = new JPanel();
        add(panelCentral);
        interfazCalculadora();
    }

    /**
     * Método para crear la interfaz de la calculadora
     * con los botones y etiqueta como campo de texto
     */
    public void interfazCalculadora(){
        //Diseñamos la ventana
        setTitle("CALCULADORA");
        setSize(300, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false); //Para que no se pueda redimensionar
        getContentPane().setBackground(Color.BLACK); //Color de fondo
        setVisible(true);

        //Creamos el campo de texto
        textoEtq = new JLabel("0");
        textoEtq.setBounds(10,10,265,50);
        textoEtq.setHorizontalAlignment(SwingConstants.RIGHT); //Alineación a la derecha
        textoEtq.setOpaque(true); //Para que se vea el color de fondo
        textoEtq.setBackground(Color.BLACK); //Color de fondo
        textoEtq.setForeground(Color.WHITE); //Color de la letra
        textoEtq.setBorder(new LineBorder(Color.GRAY)); //Borde
        textoEtq.setFont(new Font("Arial", Font.BOLD, 30)); //Fuente
        add(textoEtq);

        //Creamos los botones de la calculadora
        botones = new JButton[numBotones];

        for (int i = 0; i < numBotones; i++){
            botones[i] = new JButton(textoBotones[i]);
            //Para el botón de resultado tenemos que especificar su dimensión
            int anchoBoton = (i==0) ? 257 : 62;
            //Posicionamos los botones
            botones[i].setBounds(xBotones[i], yBotones[i], anchoBoton, 62);
            botones[i].setBackground(Color.DARK_GRAY); //Cambiamos el fondo
            botones[i].setBorder(null); //Quitamos el borde
            botones[i].setForeground(Color.WHITE); //Cambiamos el color de la letra
            botones[i].setFont(new Font("Arial", Font.BOLD, 20)); //Cambiamos la fuente
            botones[i].setFocusPainted(false); //Quitamos el borde al hacer click
            add(botones[i]);  //Añadimos los botones al panel
        }

        //Añadimos los eventos a los botones
        eventoBotonesNumeros();
        eventoBotonesOperadores();
        eventoBotonLimpiar();
        eventoBotonPuntoDecimal();
        eventoBotonResultado();
    }

    /**
     * Método para añadir eventos a los botones de los números
     */
    public void eventoBotonesNumeros(){
        for (int i=0; i<10; i++){
            int numBoton = numerosBotones[i];
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    //Si el texto no es 0, sustituimos el texto por el valor del número
                    if (nuevoNumero){
                        if (!textoBotones[numBoton].equals("0")){
                            textoEtq.setText(textoBotones[numBoton]);
                            nuevoNumero = false; //Ya no es un nuevo número
                        }
                    } else {
                        // Si es 0, lo añadimos al texto
                        textoEtq.setText(textoEtq.getText() + textoBotones[numBoton]);
                    }
                }
            });
        }
    }

    /**
     * Método para añadir eventos al botón de punto decimal
     */
    public void eventoBotonPuntoDecimal(){
        botones[15].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si es un nuevo número, añadimos un 0 con .
                if (nuevoNumero){
                    textoEtq.setText("0.");
                    nuevoNumero = false;
                    puntoDecimal = true;
                } else {
                    // Si no hay punto decimal, lo añadimos
                    //Esto permite que no se puedan añadir más de un punto decimal
                    if (!puntoDecimal){
                        textoEtq.setText(textoEtq.getText() + ".");
                        puntoDecimal = true;
                    }
                }
            }
        });
    }

    /**
     * Método para añadir eventos al botón de limpiar (C)
     * Borra los números en la etiqueta y reinicia las variables
     */
    public void eventoBotonLimpiar(){
        botones[13].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoEtq.setText("0");
                nuevoNumero = true;
                puntoDecimal = false;
                operacion = "";
                num1 = 0;
                num2 = 0;
                resultado = 0;
            }
        });
    }

    /**
     * Método para añadir eventos a los botones de los operadores
     * +, -, *, /
     */
    public void eventoBotonesOperadores(){
        for (int i = 0; i < 4; i++) {
            int numBoton = operacionesBotones[i];
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String textoActual = textoEtq.getText();

                    // Si se presiona "-" al inicio o después de otro operador, es un número negativo
                    if (textoBotones[numBoton].equals("-")) {
                        if (nuevoNumero || textoActual.isEmpty() || textoActual.equals("0")) {
                            textoEtq.setText("-");
                            nuevoNumero = false;
                            return;
                        } else if (operacion.isEmpty()) {
                            // Si no hay operación pendiente, asigna normalmente
                            num1 = Double.parseDouble(textoActual);
                            operacion = "-";
                            nuevoNumero = true;
                            return;
                        }
                    }

                    // Si ya hay operación pendiente, calcula resultado parcial
                    if (!operacion.isEmpty()) {
                        try {
                            num2 = Double.parseDouble(textoActual);
                            String mensaje = num1 + " " + operacion + " " + num2;
                            resultado = Double.parseDouble(enviarMensajeTCP(mensaje));
                            textoEtq.setText(String.valueOf(resultado));
                            num1 = resultado;
                            nuevoNumero = true;
                            operacion = textoBotones[numBoton];
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        // Guardamos el primer número y el operador
                        num1 = Double.parseDouble(textoActual);
                        operacion = textoBotones[numBoton];
                        nuevoNumero = true;
                    }
                }
            });
        }
    }

    /**
     * Método para añadir eventos al botón de resultado
     */
    public void eventoBotonResultado(){
        botones[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si hay una operación pendiente
                if (!operacion.equals("")) {
                    try {
                        num2 = Double.parseDouble(textoEtq.getText());
                        resultado = Double.parseDouble(enviarMensajeTCP(num1+" "+operacion+" "+num2));
                        textoEtq.setText(String.valueOf(resultado));

                        // Validacion si divide con 0
                        if (operacion.equals("/") && num2 == 0) {
                            textoEtq.setText("Error");
                            return;
                        }

                        // Enviar la operación al servidor con espacios para que lea el split
                        String respuesta = enviarMensajeTCP(num1+ " " + operacion+ " " + num2);
                        textoEtq.setText(String.valueOf(respuesta));
                        // Actualizamos las variables
                        nuevoNumero = true;
                        puntoDecimal = false;
                        operacion = "";

                        // Verificar si la respuesta es válida
                        if (respuesta != null && !respuesta.trim().isEmpty()) {
                            try {
                                resultado = Double.parseDouble(respuesta);
                                textoEtq.setText(String.valueOf(resultado));
                            } catch (NumberFormatException ex) {
                                textoEtq.setText("Error, no es número válido");
                                System.err.println("ERROR: Respuesta del servidor no es un número válido: " + respuesta);
                            }
                        } else {
                            textoEtq.setText("Error, respuesta vacía");
                            System.err.println("ERROR: Respuesta del servidor es nula o vacía.");
                        }
                        // Se actualizan las variables
                        nuevoNumero = true;
                        puntoDecimal = false;
                        operacion = "";
                    } catch (Exception ex) {
                        textoEtq.setText("Error de conexión");
                        System.out.println("ERROR: No se pudo realizar la operación"+ ex.getMessage());
                    }
                }
            }
        });
    }


    /**
     * Método para enviar la petición al servidor
     * @param peticion mensaje a enviar al servidor
     * @return respuesta del servidor
     * @throws IOException excepción de entrada/salida
     */
    private String enviarMensajeTCP(String peticion) throws IOException {
        //Creamos la conexión con el servidor
        Socket socket = new Socket(host, puerto);
        System.out.println("Conectando con el servidor.");

        //Buffers de entrada y salida para recibir mensajes
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        //Enviamos la petición al servidor
        out.println(peticion);
        out.flush(); //Limpiamos el buffer
        System.out.println("Petición al servidor: " + peticion);

        //Recibimos la respuesta del servidor
        String respuesta = in.readLine();

        // Verificamos que la respuesta no sea nula o vacía
        if(respuesta == null || respuesta.trim().isEmpty()){
            System.out.println("ERROR: No se recibió respuesta del servidor");
            respuesta = "0";
        }
        System.out.println("Respuesta del servidor: " + respuesta);

        //Cerramos la conexión y los buffers
        in.close();
        out.close();
        socket.close();

        return respuesta;
    }

    public static void main (String[] args) throws Exception {
        Cliente cliente = new Cliente();
        cliente.interfazCalculadora();
    }

}
