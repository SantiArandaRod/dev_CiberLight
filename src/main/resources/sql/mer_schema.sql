SELECT u.id, u.nombre, t.especialidad, t.lotes_activos, u.activo
FROM usuario u
         JOIN tecnico t ON u.id = t.id;