# PROTOCOLO CALCULADORA
Protocolo a utilizar será TCP.
La aplicación consiste en la realización de cálculos matemáticos básicos. Para ellos el protocolo de nivel de aplicación que definimos identificaran los mensajes entre el cliente y servidor.


**Mensaje 1:** Inicio de operación. 
Aquí el cliente realiza una consulta enviando una operación a solucionar.

- **¿Quién lo envía?** El cliente.
- **¿Cuándo se envía?** En cualquier momento. El cliente puede abrir la interfaz y realizar la consulta de una operación cuando quiera.
- **¿Qué contiene?** Una consulta, es decir la operación. Esta es de tipo double, por ejemplo: 2025-1967.


**Mensaje 2:** El servidor envía el resultado de la operación al cliente. Este será de tipo double. 

- **¿Quién lo envía?** El servidor
- **¿Cuándo se envía?** Justo después del mensaje de consulta del cliente. Esta será la respuesta de la operación.
- **¿Qué contiene?** Un número tipo double como resultado de la operación. Ejemplo: 58.