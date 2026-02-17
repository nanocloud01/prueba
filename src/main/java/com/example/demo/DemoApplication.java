package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * 
 * @SpringBootApplication: Anotación compuesta que incluye:
 * - @SpringBootConfiguration: Indica que esta clase es una configuración de Spring
 * - @EnableAutoConfiguration: Habilita la configuración automática de Spring Boot
 * - @ComponentScan: Escanea componentes en el paquete base y subpaquetes
 * 
 * Esta clase debe estar en el paquete raíz para que @ComponentScan
 * detecte todos los componentes de la aplicación.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
