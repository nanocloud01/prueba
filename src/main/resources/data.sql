-- ==========================================
-- DATOS INICIALES PARA LA BASE DE DATOS H2
-- ==========================================

-- Tabla de productos (se crea automáticamente por JPA)
-- Este script se ejecuta al iniciar la aplicación

-- Insertar productos de ejemplo
INSERT INTO productos (id, nombre, descripcion, precio, stock, activo, fecha_creacion, fecha_actualizacion)
VALUES
(gen_random_uuid(), 'Laptop Dell XPS 15', 'Computadora portátil de alta gama con pantalla 4K', 1299.99, 25, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Mouse Inalámbrico', 'Mouse ergonómico wireless con precisión óptica', 29.99, 150, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Teclado Mecánico', 'Teclado gaming con switches azules', 89.99, 75, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Monitor 27 pulgadas', 'Monitor Full HD IPS con HDMI y DisplayPort', 249.99, 40, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Auriculares Bluetooth', 'Audífonos wireless con cancelación de ruido', 159.99, 60, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Webcam HD', 'Cámara web 1080p para videollamadas', 79.99, 100, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Disco SSD 1TB', 'Unidad de estado sólido NVMe rápida', 99.99, 200, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'USB-C Hub', 'Adaptador multiport para laptops modernas', 49.99, 120, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Alfombrilla Gaming', 'Mouse pad extendido XL de tela', 19.99, 300, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Webcam 4K', 'Cámara profesional para streaming', 199.99, 30, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Confirmar inserciones
SELECT * FROM productos;
