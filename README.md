# CALCULADORA

## Funcionalidad
Programa de una calculadora con el protocolo TCP, y la biblioteca Swing en Java donde se lleva a cabo las operaciones matemáticas básicas que son: suma, resta, multiplicación, división.

## Clases
### Servidor : Consta de dos métodos, uno para operar los números y otro para enviar los datos.

> **Métodos:**
>- resultadoOperacion:
>- main:
   
### Cliente : Se encuentra la interfaz grafica y extiende de la clase JFrame. Consta de diferentes métodos para manejar los eventos de los botones y uno en donde se enviará la operación al Servidor.

> **Métodos:**
>- Cliente: Constructor donde incializamos los componenetes  para la ventana de la interfaz gráfica, llamandon también al método interfazCalculadora.
>- interfazCalculadora: Configuramos la ventana donde mostramos la calculadora con el titulo, la etiqueta (textoEtq) que funcionará como pantalla, botones. Llamamos aqui a los métodos de cada evento de los botones.
>- eventoBotonesNumeros: Aqui especificamos la lógica para los botones númericos de la calculadora, configurando para que se muestren en la etiqueta.
>- eventoBotonPuntoDecimal: Al hacer click en el punto decimal, debe agregarse solo una vez por número, y si se presiona al inicio se toma como "0." .
>- eventoBotonesOperadores: método donde indicamos los operadores ("+","-","*","/").Cuando se hace clic en un operador, se almacena el número actual y la operación seleccionada.
>- eventoBotonResultado: Cuando se hace clic al botón "RESULTADO", se envía la operación al servidor para calcular el resultado y se muestra en la pantalla.
>- eventoBotonLimpiar:Reinicia la pantalla y las variables de la calculadora al hacer click al boton "C".
>- enviarMensajeTCP: Aqui el cliente envia la operación al servidor a través del socket, esto mediante una conexión utilizando el host y puerto definidos al inicio de la clase.Espera la respuesta del servidor y luego confirma si es válida para mostrarla por la etiqueta, de lo contrario devuelve 0.
>- main: Crea una instancia de la clase Cliente y llama al método interfazCalculadora() para mostrar la ventana de la calculadora.

### Comprobamos la conexión entre cliente y servidor
Primero vamos  crear las clases de Cliente y Servidor con sus respectivas main's, como método para comprobar la correcta conexión. 
En la clase Cliente tendremos que crear un método donde se envie el mensaje como petición de la operación al Servidor. Pero como primero haremos una prueba, enviaremos un mensaje simple.

### Desarrollamos la Interfaz Gráfica
Luego crearemos la interfaz gráfica en la clase cliente con los métodos separados.


## ¿Por qué TCP?
Utilizamos este protocolo por la seguridad en su conexión y la confiabilidad en el envio completo de los datos.
