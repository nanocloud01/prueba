package com.example.demo.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para recursos no encontrados.
 * <p>
 * Se lanza cuando se intenta acceder a un recurso (entidad) que no existe
 * en la base de datos. Por ejemplo, cuando se busca un producto por ID
 * y este no existe.
 *
 * @ResponseStatus: Indica el código HTTP que se devolverá cuando se lance
 * esta excepción. HttpStatus.NOT_FOUND = 404.
 * <p>
 * HERENCIA DE RuntimeException:
 * - Las excepciones que heredan de RuntimeException son unchecked exceptions
 * - No requieren declaración en la firma de los métodos (throws)
 * - Son adecuadas para errores recuperables o esperados
 * <p>
 * EJEMPLO DE USO:
 * throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     *
     * @param message Mensaje descriptivo del error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message Mensaje descriptivo del error
     * @param cause   Excepción original que causó este error
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
