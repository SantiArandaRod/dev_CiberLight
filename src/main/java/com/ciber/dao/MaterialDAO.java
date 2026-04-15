package com.ciber.dao;

import com.ciber.model.Material;
import com.ciber.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    public List<Material> listar() {
        List<Material> lista = new ArrayList<>();

        String sql = "SELECT * FROM material WHERE activo = TRUE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Material m = new Material(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getInt("stock_minimo")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public void insertar(Material material) {
        String sql = "INSERT INTO material(nombre, stock, stock_minimo) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, material.getNombre());
            stmt.setInt(2, material.getStock());
            stmt.setInt(3, material.getStockMinimo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void aumentarStock(int id, int cantidad) {
        String sql = "UPDATE material SET stock = stock + ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean disminuirStock(int id, int cantidad) {
        String sql = "UPDATE material SET stock = stock - ? WHERE id = ? AND stock >= ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, id);
            stmt.setInt(3, cantidad);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public void inactivar(int id) {
        String sql = "UPDATE material SET activo = FALSE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}