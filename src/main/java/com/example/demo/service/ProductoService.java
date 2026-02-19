package com.example.demo.service;

import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.entity.Producto;
import com.example.demo.core.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProductoMapper;
import com.example.demo.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(String id) {
        UUID uuid = UUID.fromString(id);
        Producto producto = productoRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + id));

        return productoMapper.toResponseDTO(producto);
    }

    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO requestDTO) {
        Producto producto = productoMapper.toEntity(requestDTO);
        producto.setActivo(requestDTO.activo() != null ? requestDTO.activo() : true);

        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.toResponseDTO(productoGuardado);
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
        return productoMapper.toResponseDTO(productoActualizado);
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
                .map(productoMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> buscarConFiltros(String nombre, Pageable pageable) {
        return productoRepository.buscarConFiltros(nombre, pageable)
                .map(productoMapper::toResponseDTO);
    }
}
