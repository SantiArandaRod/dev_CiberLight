package com.ciber.dao;

import com.ciber.model.Rol;
import com.ciber.model.Tecnico;
import com.ciber.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {

    public List<Tecnico> obtenerTodos() {

        List<Tecnico> lista = new ArrayList<>();

        String sql = """
            SELECT u.id, u.nombre, u.activo,
                   t.especialidad, t.lotes_activos
            FROM usuario u
            JOIN tecnico t ON u.id = t.id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Tecnico t = new Tecnico(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        Rol.TECNICO,
                        true, rs.getString("especialidad")
                );

                t.setLotesActivos(rs.getInt("lotes_activos"));
                t.setActivo(rs.getBoolean("activo"));

                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertar(String nombre, String especialidad) {

        String sqlUsuario = "INSERT INTO usuario(nombre, rol, activo) VALUES (?, 'TECNICO', TRUE)";
        String sqlTecnico = "INSERT INTO tecnico(id, especialidad, lotes_activos) VALUES (?, ?, 0)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, nombre);
            psUsuario.executeUpdate();

            ResultSet rs = psUsuario.getGeneratedKeys();
            rs.next();
            int idGenerado = rs.getInt(1);

            PreparedStatement psTecnico = conn.prepareStatement(sqlTecnico);
            psTecnico.setInt(1, idGenerado);
            psTecnico.setString(2, especialidad);
            psTecnico.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inactivar(int id) {

        String sql = "UPDATE usuario SET activo = FALSE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}