# CALCULADORA

## Funcionalidad
Programa de una calculadora con el protocolo TCP, y la biblioteca Swing en Java donde se lleva a cabo las operaciones matemáticas básicas que son: suma, resta, multiplicación, división.

## Clases
### Servidor  
Consta de dos métodos, uno para realizar la operación mandada por el Cliente y otro para enviar los resultados de esa operación.

<details>
  <summary> Variables </summary>
  
   - ``puerto`` Es el puerto en el que el servidor escucharña al cliente.
   - ``resultado`` Guarda el resulktado de la operacion mátematica que se enviará al cliente.
</details>

**Métodos:**
 
- ``resultadoOperacion()`` Este método realiza la operación matemática según el operador que mande el cliente. Recibe tres parámetros: num1(primer operador), num2 (segundo operador) y el operador (operador matemático).

- ``main()`` En este método se maneja la conexión con el cliente, mediante un ServerSocket, se leerá la petición, es decir la operación que mande.

   
### Cliente  
Se encuentra la interfaz grafica por lo cual se extiende de la clase JFrame. Consta de diferentes métodos para manejar los eventos de los botones y uno en donde se enviará la operación al Servidor.

<details>
  <summary>Variables</summary>
  
- ``host`` Dirección IP a la que se conectará el cliente. 
- ``puerto`` Puerto al qeu se conectará con el servidor.
- ``panelCentral`` Panel donde estarán los componentes de la interfaz gráfica.
- ``textoEtq`` Es la etiqueta (JLabel) que funciona como pantalla, mostrando los números y el resultado.
- ``botones`` Es un array de botones (JButton) donde estan los números, operadores, resultado y el punto decimal.
- ``textoBotones`` Es un array de cadena de texto en el que ponemos los valores númericos, operadores y demás botones.
- ``xBotones`` Define la coordenada del eje x (horizontal) como se ubicará en la ventana.
- ``yBotones`` Define la coordenada del eje y (vertical) como se ubicará en la ventana.
- ``numerosBotones`` Array que indica el indice de los botones númericos.
- ``operadoracionesBotones`` Array que indica el indice de los botones de los operadores matematicos.
- ``nuevoNumero`` Booleano que ayuda a controlar cuando el número mostrado en pantalla es nuevo.
- ``puntoDecimal`` Booleano que ayuda a controlar cuando se escribe el punto decimal.
- ``operacion`` Guarda la operación matemática que se enviará al servidor.
- ``num1`` Guarda al operador 1.
- ``num2`` Guarda al operador 2
- ``resultado`` Guarda el resultado de la operacion en double.
</details>

**Métodos:**
 
- ``Cliente()`` Constructor donde incializamos los componenetes  para la ventana de la interfaz gráfica, llamandon también al método interfazCalculadora.

- ``interfazCalculadora()`` Configuramos la ventana donde mostramos la calculadora con el titulo, la etiqueta (textoEtq) que funcionará como pantalla, botones. Llamamos aqui a los métodos de cada evento de los botones.
 
- `` eventoBotonesNumeros()`` Aqui especificamos la lógica para los botones númericos de la calculadora, configurando para que se muestren en la etiqueta.

- ``eventoBotonPuntoDecimal()`` Al hacer click en el punto decimal, debe agregarse solo una vez por número, y si se presiona al inicio se toma como "0." .

- `` eventoBotonesOperadores()`` método donde indicamos los operadores ("+","-","*","/").Cuando se hace clic en un operador, se almacena el número actual y la operación seleccionada.

- `` eventoBotonResultado()`` Cuando se hace clic al botón "RESULTADO", se envía la operación al servidor para calcular el resultado y se muestra en la pantalla.

- `` eventoBotonLimpiar()`` Reinicia la pantalla y las variables de la calculadora al hacer click al boton "C".
 
- `` enviarMensajeTCP()`` Aqui el cliente envia la operación al servidor a través del socket, esto mediante una conexión utilizando el host y puerto definidos al inicio de la clase.Espera la respuesta del servidor y luego confirma si es válida para mostrarla por la etiqueta, de lo contrario devuelve 0.
 
- `` main()`` Crea una instancia de la clase Cliente y llama al método interfazCalculadora() para mostrar la ventana de la calculadora.

## Explicación del programa
El servidor inicia la conexión en el puerto asignado. El usuario interactua con la interfaz gráfica de la calculadora como el cliente. Al momento de que presiona los botones de los números, estos se mostrarán en pantalla. Cuando se presione el botón "Resultado", se enviará como una petición del cliente al servidor en un formato ``num1 operador num2``.
El servidor procesará la petición, realizando asi la operación matemática, para luego enviar el resultado al cliente.
El servidor cierra la conexión con el cliente y vuelve a esperar más conexiones ya que en el método tenemos un bucle (while). Cuando el usuario ya no desea realizar más operaciones, cierra la pantalla de la calculadora dando click a la (X) y se desconecta automaticamente el cliente del servidor.

![image](https://github.com/user-attachments/assets/2878bb84-a6d8-467a-a442-54348338851a)

### Manejo de errores

- Manejo de errores:
Si la conexión con el servidor falla o no hay una respuesta válida, se captura la excepción y se muestra un mensaje de "ERROR" en la pantalla y en la consola.
- División por cero:
Si el usuario intenta dividir por cero, se muestra un mensaje de "ERROR" en la pantalla.
- Puerto ocupado:
Maneja una excepción en la cuál si el puerto al qeu se intenta conectar el servidor está ocupado, se muestra un mensaje "ERROR de conexión" por consola y pantalla.

## Mejoras a futuro

**Servidor multihilo:** Esto ayudaria a manejar con multiples clientes. El programa por el momento es monohilo.

## ¿Por qué TCP?
Utilizamos este protocolo por la seguridad en su conexión y la confiabilidad en el envio completo de los datos.
