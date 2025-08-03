/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudmascotas.dao;

/**
 *
 * @author cirom
 */
import com.mycompany.crudmascotas.conexion.ConexionDB;
import com.mycompany.crudmascotas.Mascota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para manejar las operaciones CRUD de Mascota
 * Esta clase se encarga de interactuar directamente con la base de datos
 */
public class MascotaDAO {
    
    // Referencia a la conexión de base de datos
    private Connection conexion;
    
    // Constructor que obtiene la conexión
    public MascotaDAO() {
        this.conexion = ConexionDB.getConexion();
    }
    
    /**
     * CREATE - Insertar una nueva mascota en la base de datos
     * @param mascota - objeto Mascota a insertar
     * @return boolean - true si se insertó correctamente, false si falló
     */
    public boolean insertar(Mascota mascota) {
        // SQL para insertar una nueva mascota (sin ID porque es auto-incrementable)
        String sql = "INSERT INTO mascotas (nombre, raza, edad, codigo, vacunas) VALUES (?, ?, ?, ?, ?)";
        
        try {
            // Preparar la consulta SQL
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            // Asignar valores a los parámetros (los ? se reemplazan por estos valores)
            stmt.setString(1, mascota.getNombre());    // Primer ? = nombre
            stmt.setString(2, mascota.getRaza());      // Segundo ? = raza
            stmt.setInt(3, mascota.getEdad());         // Tercer ? = edad
            stmt.setString(4, mascota.getCodigo());    // Cuarto ? = codigo
            stmt.setString(5, mascota.getVacunas());   // Quinto ? = vacunas
            
            // Ejecutar la consulta y obtener el número de filas afectadas
            int filasAfectadas = stmt.executeUpdate();
            
            System.out.println("✅ Mascota insertada correctamente");
            return filasAfectadas > 0;  // Retorna true si se insertó al menos una fila
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar mascota:");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * READ - Obtener todas las mascotas de la base de datos
     * @return List<Mascota> - lista con todas las mascotas
     */
    public List<Mascota> obtenerTodas() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas ORDER BY id";
        
        try {
            // Crear statement para ejecutar la consulta
            Statement stmt = conexion.createStatement();
            
            // Ejecutar la consulta y obtener los resultados
            ResultSet rs = stmt.executeQuery(sql);
            
            // Iterar sobre cada fila del resultado
            while (rs.next()) {
                // Crear objeto Mascota con los datos de la fila actual
                Mascota mascota = new Mascota(
                    rs.getInt("id"),                    // Obtener el ID
                    rs.getString("nombre"),             // Obtener el nombre
                    rs.getString("raza"),               // Obtener la raza
                    rs.getInt("edad"),                  // Obtener la edad
                    rs.getString("codigo"),             // Obtener el código
                    rs.getString("vacunas"),            // Obtener las vacunas
                    rs.getTimestamp("fecha_registro")   // Obtener la fecha de registro
                );
                
                // Agregar la mascota a la lista
                mascotas.add(mascota);
            }
            
            System.out.println("✅ Se obtuvieron " + mascotas.size() + " mascotas");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener mascotas:");
            e.printStackTrace();
        }
        
        return mascotas;
    }
    
    /**
     * READ - Buscar una mascota por su ID
     * @param id - ID de la mascota a buscar
     * @return Mascota - objeto mascota encontrado o null si no existe
     */
    public Mascota buscarPorId(int id) {
        String sql = "SELECT * FROM mascotas WHERE id = ?";
        
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);  // Asignar el ID al primer parámetro
            
            ResultSet rs = stmt.executeQuery();
            
            // Si se encontró una mascota con ese ID
            if (rs.next()) {
                return new Mascota(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("raza"),
                    rs.getInt("edad"),
                    rs.getString("codigo"),
                    rs.getString("vacunas"),
                    rs.getTimestamp("fecha_registro")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar mascota por ID:");
            e.printStackTrace();
        }
        
        return null;  // No se encontró la mascota
    }
    
    /**
     * UPDATE - Actualizar los datos de una mascota existente
     * @param mascota - objeto Mascota con los nuevos datos
     * @return boolean - true si se actualizó correctamente
     */
    public boolean actualizar(Mascota mascota) {
        String sql = "UPDATE mascotas SET nombre = ?, raza = ?, edad = ?, codigo = ?, vacunas = ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            // Asignar los nuevos valores
            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getRaza());
            stmt.setInt(3, mascota.getEdad());
            stmt.setString(4, mascota.getCodigo());
            stmt.setString(5, mascota.getVacunas());
            stmt.setInt(6, mascota.getId());  // ID para identificar qué registro actualizar
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Mascota actualizada correctamente");
                return true;
            } else {
                System.out.println("⚠️ No se encontró mascota con ID: " + mascota.getId());
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar mascota:");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * DELETE - Eliminar una mascota por su ID
     * @param id - ID de la mascota a eliminar
     * @return boolean - true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mascotas WHERE id = ?";
        
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Mascota eliminada correctamente");
                return true;
            } else {
                System.out.println("⚠️ No se encontró mascota con ID: " + id);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar mascota:");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método para buscar mascotas por nombre (búsqueda parcial)
     * @param nombre - nombre o parte del nombre a buscar
     * @return List<Mascota> - lista de mascotas que coinciden
     */
    public List<Mascota> buscarPorNombre(String nombre) {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE nombre LIKE ? ORDER BY nombre";
        
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, "%" + nombre + "%");  // % permite búsqueda parcial
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Mascota mascota = new Mascota(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("raza"),
                    rs.getInt("edad"),
                    rs.getString("codigo"),
                    rs.getString("vacunas"),
                    rs.getTimestamp("fecha_registro")
                );
                mascotas.add(mascota);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar por nombre:");
            e.printStackTrace();
        }
        
        return mascotas;
    }
}
