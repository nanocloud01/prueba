package com.example.demo.repository;

import com.example.demo.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la entidad Producto.
 * 
 * Esta interfaz extiende JpaRepository que proporciona métodos CRUD básicos:
 * - save(), findById(), findAll(), deleteById(), count(), etc.
 * 
 * Además, definimos métodos de consulta personalizados usando:
 * - Spring Data JPA Query Methods (nombres de métodos)
 * - @Query para JPQL personalizado
 * 
 * @Repository: Anotación estereotípica que indica que esta clase es un componente
 *              de acceso a datos. También activa la traducción excepciones
 *              específicas de de JDBC a excepciones de Spring.
 * 
 * @Param: Se usa para vincular parámetros de métodos de consulta JPQL
 *         a los parámetros del método del repositorio.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    /**
     * Busca productos por nombre que contengan el texto especificado (ignorando mayúsculas).
     * 
     * Spring Data JPA interpreta automáticamente este nombre de método:
     * "findBy" + "Nombre" + "Containing" = WHERE nombre LIKE %?%
     * 
     * @param nombre Texto a buscar en el nombre
     * @return Lista de productos que contienen el nombre
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca productos activos (where activo = true).
     * 
     * @return Lista de productos activos
     */
    List<Producto> findByActivoTrue();

    /**
     * Busca productos con stock menor al umbral especificado.
     * Útil para identificar productos con bajo inventario.
     * 
     * @param stock Umbral de stock
     * @return Lista de productos con stock menor al umbral
     */
    List<Producto> findByStockLessThan(Integer stock);

    /**
     * Busca productos por rango de precio.
     * 
     * @param precioMin Precio mínimo
     * @param precioMax Precio máximo
     * @return Lista de productos en el rango de precio
     */
    List<Producto> findByPrecioBetween(java.math.BigDecimal precioMin, java.math.BigDecimal precioMax);

    /**
     * Busca un producto activo por su ID.
     * 
     * @param id Identificador del producto
     * @return Optional con el producto si existe y está activo, vacío si no
     */
    Optional<Producto> findByIdAndActivoTrue(UUID id);

    /**
     * Consulta JPQL personalizada para buscar productos con bajo stock.
     * 
     * Esta consulta es más flexible que los query methods y permite:
     * - Usar JPQL en lugar de SQL puro
     * - Definir consultas complejas
     * - Optimizar el rendimiento
     * 
     * @param umbralStock Cantidad mínima para considerar "bajo stock"
     * @return Lista de productos con stock menor al umbral
     */
    @Query("SELECT p FROM Producto p WHERE p.stock < :umbralStock AND p.activo = true")
    List<Producto> buscarProductosConBajoStock(@Param("umbralStock") Integer umbralStock);

    /**
     * Consulta JPQL para contar productos activos.
     * 
     * @return Número de productos activos
     */
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.activo = true")
    long countByActivoTrue();

    /**
     * Página de productos con paginación y filtro opcional por nombre.
     * 
     * Pageable es una interfaz de Spring Data que encapsula:
     * - page: número de página (0-based)
     * - size: tamaño de página
     * - sort: criterios de ordenamiento
     * 
     * @param nombre Filtro opcional por nombre (ignora mayúsculas/minúsculas)
     * @param pageable Configuración de paginación
     * @return Página de productos que coinciden con el filtro
     */
    @Query("SELECT p FROM Producto p WHERE " +
           "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    Page<Producto> buscarConFiltros(@Param("nombre") String nombre, Pageable pageable);
}
