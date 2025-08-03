/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudmascotas;

/**
 *
 * @author cirom
 */
import com.mycompany.crudmascotas.conexion.ConexionDB;
import com.mycompany.crudmascotas.gui.VentanaPrincipal;
import javax.swing.*;

/**
 * Clase principal de la aplicación CRUD de mascotas
 * Esta clase contiene el método main que inicia la aplicación
 */
public class Main {
    
    /**
     * Método principal - punto de entrada de la aplicación
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        
        // Mensaje de bienvenida en consola
        System.out.println("===========================================");
        System.out.println("🐾 INICIANDO SISTEMA CRUD DE MASCOTAS 🐾");
        System.out.println("===========================================");
        
        // Configurar el Look and Feel de la interfaz gráfica
        configurarLookAndFeel();
        
        // Verificar conexión a la base de datos antes de mostrar la interfaz
        if (verificarConexionBaseDatos()) {
            
            // Crear y mostrar la ventana principal en el hilo de eventos de Swing
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Crear la ventana principal
                        VentanaPrincipal ventana = new VentanaPrincipal();
                        
                        // Hacer la ventana visible
                        ventana.setVisible(true);
                        
                        System.out.println("✅ Interfaz gráfica iniciada correctamente");
                        
                    } catch (Exception e) {
                        System.err.println("❌ Error al iniciar la interfaz gráfica:");
                        e.printStackTrace();
                        
                        // Mostrar mensaje de error al usuario
                        JOptionPane.showMessageDialog(null, 
                            "Error al iniciar la aplicación:\n" + e.getMessage(),
                            "Error Fatal", 
                            JOptionPane.ERROR_MESSAGE);
                        
                        System.exit(1);  // Salir de la aplicación
                    }
                }
            });
            
        } else {
            // Si no hay conexión a la base de datos, mostrar error y salir
            String mensaje = "❌ No se pudo conectar a la base de datos MySQL.\n\n" +
                           "Verifica que:\n" +
                           "1. MySQL esté ejecutándose\n" +
                           "2. La base de datos 'crud_mascotas' exista\n" +
                           "3. Las credenciales en ConexionDB.java sean correctas\n" +
                           "4. El puerto 3306 esté disponible";
            
            System.err.println(mensaje);
            
            // Mostrar mensaje de error en ventana
            JOptionPane.showMessageDialog(null, 
                mensaje, 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);  // Salir de la aplicación
        }
    }
    
    /**
     * Configurar el aspecto visual de la interfaz gráfica
     * Esto hace que la aplicación se vea más moderna y nativa del sistema operativo
     */
    private static void configurarLookAndFeel() {
    try {
        // Usar el look and feel nativo del sistema
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        System.out.println("✅ Look and Feel configurado");
    } catch (Exception e) {
        System.err.println("⚠️ No se pudo configurar el Look and Feel");
    }
}
    
    /**
     * Verificar que la conexión a la base de datos funcione correctamente
     * @return true si la conexión es exitosa, false si falla
     */
    private static boolean verificarConexionBaseDatos() {
        System.out.println("🔄 Verificando conexión a la base de datos...");
        
        try {
            // Intentar conectar usando la clase ConexionDB
            boolean conexionExitosa = ConexionDB.probarConexion();
            
            if (conexionExitosa) {
                System.out.println("✅ Conexión a MySQL verificada correctamente");
                return true;
            } else {
                System.err.println("❌ Fallo en la verificación de conexión");
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error durante la verificación de conexión:");
            e.printStackTrace();
            return false;
        }
    }
}