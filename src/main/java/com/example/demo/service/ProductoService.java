package com.example.demo.service;

import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.entity.Producto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(String id) {
        UUID uuid = UUID.fromString(id);
        Producto producto = productoRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + id));

        return convertirAResponseDTO(producto);
    }

    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO requestDTO) {
        Producto producto = new Producto();
        producto.setNombre(requestDTO.nombre());
        producto.setDescripcion(requestDTO.descripcion());
        producto.setPrecio(requestDTO.precio());
        producto.setStock(requestDTO.stock());
        producto.setActivo(requestDTO.activo() != null ? requestDTO.activo() : true);

        Producto productoGuardado = productoRepository.save(producto);
        return convertirAResponseDTO(productoGuardado);
    }

    @Transactional
    public ProductoResponseDTO actualizar(String id, ProductoRequestDTO requestDTO) {
        UUID uuid = UUID.fromString(id);
        Producto producto = productoRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + id));

        if (requestDTO.nombre() != null) {
            producto.setNombre(requestDTO.nombre());
        }
        if (requestDTO.descripcion() != null) {
            producto.setDescripcion(requestDTO.descripcion());
        }
        if (requestDTO.precio() != null) {
            producto.setPrecio(requestDTO.precio());
        }
        if (requestDTO.stock() != null) {
            producto.setStock(requestDTO.stock());
        }
        if (requestDTO.activo() != null) {
            producto.setActivo(requestDTO.activo());
        }

        Producto productoActualizado = productoRepository.saveAndFlush(producto);
        return convertirAResponseDTO(productoActualizado);
    }

    @Transactional
    public void eliminar(String id) {
        UUID uuid = UUID.fromString(id);
        if (!productoRepository.existsById(uuid)) {
            throw new ResourceNotFoundException(
                    "Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(uuid);
    }

    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> obtenerConPaginacion(Pageable pageable) {
        return productoRepository.findAll(pageable)
                .map(this::convertirAResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> buscarConFiltros(String nombre, Pageable pageable) {
        return productoRepository.buscarConFiltros(nombre, pageable)
                .map(this::convertirAResponseDTO);
    }

    private ProductoResponseDTO convertirAResponseDTO(Producto producto) {
        return new ProductoResponseDTO(
            producto.getId() != null ? producto.getId().toString() : null,
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getStock(),
            producto.getActivo(),
            producto.getFechaCreacion(),
            producto.getFechaActualizacion()
        );
    }
}
