package com.ciber.dao;

import com.ciber.model.*;
import com.ciber.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    public List<Lote> listar() {

        List<Lote> lista = new ArrayList<>();

        String sql = """
            SELECT l.*, u.nombre as tecnico_nombre, t.id as tecnico_id
            FROM lote l
            JOIN tecnico t ON l.tecnico_id = t.id
            JOIN usuario u ON t.id = u.id
            WHERE l.activo = TRUE
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Tecnico t = new Tecnico(
                        rs.getInt("tecnico_id"),
                        rs.getString("tecnico_nombre"),
                        null,
                        true,
                        null
                );

                Lote l = new Lote(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cliente"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null,
                        EstadoLote.valueOf(rs.getString("estado")),
                        t,
                        rs.getBoolean("activo")
                );

                lista.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertar(Lote l) {

        String sql = """
            INSERT INTO lote(nombre, cliente, fecha_inicio, estado, tecnico_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNombre());
            stmt.setString(2, l.getCliente());
            stmt.setDate(3, Date.valueOf(l.getFechaInicio()));
            stmt.setString(4, l.getEstado().name());
            stmt.setInt(5, l.getTecnico().getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalizar(int id) {

        String sql = "UPDATE lote SET estado = ?, fecha_fin = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, EstadoLote.FINALIZADO.name());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inactivar(int id) {

        String sql = "UPDATE lote SET activo = FALSE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}