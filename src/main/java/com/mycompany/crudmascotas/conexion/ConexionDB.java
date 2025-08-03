/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudmascotas.conexion;

/**
 *
 * @author cirom
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión con la base de datos MySQL
 * Implementa el patrón Singleton para una sola instancia de conexión
 */
public class ConexionDB {
    
    // Configuración de la base de datos - CAMBIA ESTOS VALORES SEGÚN TU CONFIGURACIÓN
    private static final String URL = "jdbc:mysql://localhost:3306/crud_mascotas";
    private static final String USUARIO = "root";        // Tu usuario de MySQL
    private static final String PASSWORD = "80480403248cM&";           // Tu contraseña de MySQL (déjala vacía si no tienes)
    
    // Variable para almacenar la conexión única
    private static Connection conexion = null;
    
    // Constructor privado para evitar instanciación externa (patrón Singleton)
    private ConexionDB() {
    }
    
    /**
     * Método para obtener la conexión a la base de datos
     * Si no existe conexión, la crea; si existe, la reutiliza
     * @return Connection - objeto de conexión a la base de datos
     */
    public static Connection getConexion() {
        try {
            // Verificar si la conexión no existe o está cerrada
            if (conexion == null || conexion.isClosed()) {
                
                // Cargar el driver de MySQL (opcional en versiones nuevas de Java)
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establecer la conexión con la base de datos
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                
                System.out.println("✅ Conexión exitosa a la base de datos MySQL");
                
            }
        } catch (ClassNotFoundException e) {
            // Error al cargar el driver
            System.err.println("❌ Error: No se encontró el driver de MySQL");
            System.err.println("Asegúrate de tener mysql-connector-java en el pom.xml");
            e.printStackTrace();
            
        } catch (SQLException e) {
            // Error de conexión SQL
            System.err.println("❌ Error al conectar con la base de datos:");
            System.err.println("Verifica que:");
            System.err.println("1. MySQL esté ejecutándose");
            System.err.println("2. La base de datos 'crud_mascotas' exista");
            System.err.println("3. El usuario y contraseña sean correctos");
            System.err.println("4. El puerto 3306 esté disponible");
            e.printStackTrace();
        }
        
        return conexion;
    }
    
    /**
     * Método para cerrar la conexión a la base de datos
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar la conexión:");
            e.printStackTrace();
        }
    }
    
    /**
     * Método para probar la conexión
     * @return boolean - true si la conexión es exitosa, false si falla
     */
    public static boolean probarConexion() {
        Connection conn = getConexion();
        return conn != null;
    }
}
