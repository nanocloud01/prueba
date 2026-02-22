package com.example.demo.controller;

import com.example.demo.core.response.PageSigep;
import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.core.response.PageResponse;
import com.example.demo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable String id) {
        ProductoResponseDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @Valid @RequestBody ProductoRequestDTO requestDTO) {
        ProductoResponseDTO productoCreado = productoService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ProductoRequestDTO requestDTO) {
        ProductoResponseDTO productoActualizado = productoService.actualizar(id, requestDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<ProductoResponseDTO>> obtenerConPaginacion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<ProductoResponseDTO> productosPage = productoService.obtenerConPaginacion(pageable);

        return ResponseEntity.ok(productosPage);
    }

    @GetMapping("/buscar")
    public ResponseEntity<PageResponse<ProductoResponseDTO>> buscarConFiltros(
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return PageSigep.ok(productoService.buscarConFiltros(nombre, pageable));
    }
}
