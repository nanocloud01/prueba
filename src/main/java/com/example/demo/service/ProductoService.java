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
        producto.setNombre(requestDTO.getNombre());
        producto.setDescripcion(requestDTO.getDescripcion());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setStock(requestDTO.getStock());
        producto.setActivo(requestDTO.getActivo() != null ? requestDTO.getActivo() : true);

        Producto productoGuardado = productoRepository.save(producto);
        return convertirAResponseDTO(productoGuardado);
    }

    @Transactional
    public ProductoResponseDTO actualizar(String id, ProductoRequestDTO requestDTO) {
        UUID uuid = UUID.fromString(id);
        Producto producto = productoRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + id));

        if (requestDTO.getNombre() != null) {
            producto.setNombre(requestDTO.getNombre());
        }
        if (requestDTO.getDescripcion() != null) {
            producto.setDescripcion(requestDTO.getDescripcion());
        }
        if (requestDTO.getPrecio() != null) {
            producto.setPrecio(requestDTO.getPrecio());
        }
        if (requestDTO.getStock() != null) {
            producto.setStock(requestDTO.getStock());
        }
        if (requestDTO.getActivo() != null) {
            producto.setActivo(requestDTO.getActivo());
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
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId() != null ? producto.getId().toString() : null);
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setActivo(producto.getActivo());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setFechaActualizacion(producto.getFechaActualizacion());
        return dto;
    }
}
