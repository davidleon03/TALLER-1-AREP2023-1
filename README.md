# TALLER 1 | APLICACIONES DISTRIBUIDAS

El siguiente repositorio se crea con el fin de afianzar conocimientos frente a temas MVN, GIT, JAVA. También la interacción con API's externas mediante protocolos REST.


### Prerequisites

 - Maven
 - Java 
 - Git 

### Installing

Para descargar el proyecto y compilarlo de manera local puede clonar el repositorio con el siguiente comando:

    git clone https://github.com/davidleon03/TALLER-1-AREP2023-1.git
     

Ejecutar el programa mediante consola con el siguiente comando. También genera el ciclo de vida de este.

    mvn clean package exec:java -Dexec.mainClass="URI.HttpServer"
    
Para acceder a la navegacion web para vusualizar lo realizado

    http://localhost:35000/


## Running the tests

Para ejecutar las pruebas unitarias se ejecutan con el siguiente comando: 

    mvn test

## Documentation

Para crear la documentación del proyecto se hace con el siguiente comando:
    
    mvn javadoc:javadoc
    

## Authors

* **David Leon** 

