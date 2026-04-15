CREATE TABLE lote (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      equipo VARCHAR(100),
                      cliente VARCHAR(100),
                      tecnico_id INT,
                      estado VARCHAR(20),
                      tiempo_estimado INT,
                      tiempo_real INT,
                      activo BOOLEAN DEFAULT TRUE,

                      FOREIGN KEY (tecnico_id) REFERENCES tecnico(id)
);

ALTER TABLE lote
    ADD cliente VARCHAR(100),
    ADD activo BOOLEAN DEFAULT TRUE;