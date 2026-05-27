CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE tecnico (
    id INT PRIMARY KEY,
    especialidad VARCHAR(100) NOT NULL,
    lotes_activos INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_tecnico_usuario
        FOREIGN KEY (id) REFERENCES usuario(id)
);

CREATE TABLE material (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE lote (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cliente VARCHAR(100) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NULL,
    estado VARCHAR(30) NOT NULL,
    tecnico_id INT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_lote_tecnico
        FOREIGN KEY (tecnico_id) REFERENCES tecnico(id)
);
