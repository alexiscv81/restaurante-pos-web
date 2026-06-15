USE restaurante_pos_db;

INSERT INTO usuarios(nombre, correo, password, rol) VALUES
('Administrador', 'admin@restaurante.com', 'admin123', 'ADMIN'),
('Cajero Demo', 'cajero@restaurante.com', 'cajero123', 'CAJERO');

INSERT INTO categorias(nombre, descripcion) VALUES
('Bebidas', 'Bebidas frías y calientes'),
('Platillos', 'Comida principal del restaurante'),
('Postres', 'Postres y complementos');

INSERT INTO productos(id_categoria, nombre, descripcion, precio, stock) VALUES
(1, 'Agua natural', 'Botella 600 ml', 15.00, 50),
(1, 'Refresco', 'Lata 355 ml', 22.00, 40),
(2, 'Hamburguesa clásica', 'Hamburguesa con papas', 85.00, 25),
(2, 'Tacos de bistec', 'Orden de 3 tacos', 60.00, 30),
(3, 'Flan napolitano', 'Porción individual', 35.00, 15);
