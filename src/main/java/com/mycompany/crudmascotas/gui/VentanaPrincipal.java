/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudmascotas.gui;

/**
 *
 * @author cirom
 */
import com.mycompany.crudmascotas.dao.MascotaDAO;
import com.mycompany.crudmascotas.Mascota;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Ventana principal de la aplicación CRUD de mascotas
 * Esta clase crea y maneja toda la interfaz gráfica de usuario
 */
public class VentanaPrincipal extends JFrame {
    
    // Componentes de la interfaz gráfica
    private JTable tablaMascotas;           // Tabla para mostrar las mascotas
    private DefaultTableModel modeloTabla;  // Modelo de datos para la tabla
    private JTextField txtNombre;           // Campo de texto para nombre
    private JTextField txtRaza;             // Campo de texto para raza
    private JTextField txtEdad;             // Campo de texto para edad
    private JTextField txtCodigo;           // Campo de texto para código
    private JTextArea txtVacunas;           // Area de texto para vacunas
    private JTextField txtBuscar;           // Campo para búsquedas
    
    // Botones de la interfaz
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    
    // DAO para operaciones de base de datos
    private MascotaDAO mascotaDAO;
    
    // Variable para almacenar el ID de la mascota seleccionada
    private int idSeleccionado = -1;
    
    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        // Inicializar el DAO
        mascotaDAO = new MascotaDAO();
        
        // Configurar la ventana
        initComponents();
        
        // Cargar datos iniciales en la tabla
        cargarDatos();
    }
    
    /**
     * Método para inicializar y configurar todos los componentes de la interfaz
     */
    private void initComponents() {
        
        // Configuración básica de la ventana
        setTitle("🐾 Sistema CRUD de Mascotas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);  // Centrar la ventana
        
        // Crear el layout principal
        setLayout(new BorderLayout());
        
        // Panel superior: título
        JPanel panelTitulo = crearPanelTitulo();
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central: tabla de mascotas
        JPanel panelTabla = crearPanelTabla();
        add(panelTabla, BorderLayout.CENTER);
        
        // Panel inferior: formulario y botones
        JPanel panelFormulario = crearPanelFormulario();
        add(panelFormulario, BorderLayout.SOUTH);
        
        // Configurar eventos de los botones
        configurarEventos();
    }
    
    /**
     * Crear el panel del título
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 152, 219));  // Color azul
        panel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titulo = new JLabel("🐾 SISTEMA DE GESTIÓN DE MASCOTAS 🐾");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titulo);
        return panel;
    }
    
    /**
     * Crear el panel que contiene la tabla de mascotas
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBuscar = new JLabel("🔍 Buscar por nombre:");
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        JButton btnMostrarTodos = new JButton("Mostrar Todos");
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);
        
        // Configurar la tabla
        String[] columnas = {"ID", "Nombre", "Raza", "Edad", "Código", "Vacunas", "Fecha Registro"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Hacer la tabla no editable directamente
            }
        };
        
        tablaMascotas = new JTable(modeloTabla);
        tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Solo una fila seleccionable
        tablaMascotas.setRowHeight(25);  // Altura de las filas
        
        // Configurar anchos de columnas
        tablaMascotas.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tablaMascotas.getColumnModel().getColumn(1).setPreferredWidth(100);  // Nombre
        tablaMascotas.getColumnModel().getColumn(2).setPreferredWidth(100);  // Raza
        tablaMascotas.getColumnModel().getColumn(3).setPreferredWidth(60);   // Edad
        tablaMascotas.getColumnModel().getColumn(4).setPreferredWidth(80);   // Código
        tablaMascotas.getColumnModel().getColumn(5).setPreferredWidth(200);  // Vacunas
        tablaMascotas.getColumnModel().getColumn(6).setPreferredWidth(150);  // Fecha
        
        // Scroll para la tabla
        JScrollPane scrollTabla = new JScrollPane(tablaMascotas);
        scrollTabla.setPreferredSize(new Dimension(0, 300));
        
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        
        // Event listener para selección de filas en la tabla
        tablaMascotas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaMascotas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    // Cargar datos de la mascota seleccionada en el formulario
                    cargarMascotaEnFormulario(filaSeleccionada);
                }
            }
        });
        
        // Event listener para búsqueda en tiempo real
        btnBuscar.addActionListener(e -> buscarMascotas());
        btnMostrarTodos.addActionListener(e -> cargarDatos());
        
        return panel;
    }
    
    /**
     * Crear el panel del formulario de entrada de datos
     */
    private JPanel crearPanelFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createTitledBorder("📋 Datos de la Mascota"));
        panelPrincipal.setPreferredSize(new Dimension(0, 250));
        
        // Panel de campos de entrada
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Espaciado entre componentes
        
        // Fila 1: Nombre y Raza
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panelCampos.add(new JLabel("🏷️ Nombre:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNombre = new JTextField(15);
        panelCampos.add(txtNombre, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("🐕 Raza:"), gbc);
        
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtRaza = new JTextField(15);
        panelCampos.add(txtRaza, gbc);
        
        // Fila 2: Edad y Código
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("🎂 Edad:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtEdad = new JTextField(15);
        panelCampos.add(txtEdad, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("🔢 Código:"), gbc);
        
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtCodigo = new JTextField(15);
        panelCampos.add(txtCodigo, gbc);
        
        // Fila 3: Vacunas (área de texto más grande)
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("💉 Vacunas:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.BOTH; 
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtVacunas = new JTextArea(3, 40);
        txtVacunas.setLineWrap(true);  // Ajuste automático de línea
        txtVacunas.setWrapStyleWord(true);  // Cortar por palabras
        JScrollPane scrollVacunas = new JScrollPane(txtVacunas);
        panelCampos.add(scrollVacunas, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnAgregar = new JButton("➕ Agregar");
        btnAgregar.setBackground(new Color(46, 204, 113));  // Verde
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnActualizar = new JButton("✏️ Actualizar");
        btnActualizar.setBackground(new Color(52, 152, 219));  // Azul
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setEnabled(false);  // Deshabilitado inicialmente
        
        btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setBackground(new Color(231, 76, 60));  // Rojo
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.setEnabled(false);  // Deshabilitado inicialmente
        
        btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setBackground(new Color(149, 165, 166));  // Gris
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        return panelPrincipal;
    }
    
    /**
     * Configurar los eventos de los botones
     */
    private void configurarEventos() {
        
        // Botón Agregar - Crear nueva mascota
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarMascota();
            }
        });
        
        // Botón Actualizar - Modificar mascota existente
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarMascota();
            }
        });
        
        // Botón Eliminar - Borrar mascota seleccionada
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarMascota();
            }
        });
        
        // Botón Limpiar - Limpiar formulario
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
    }
    
    /**
     * Cargar todas las mascotas en la tabla
     */
    private void cargarDatos() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
        
        // Obtener mascotas de la base de datos
        List<Mascota> mascotas = mascotaDAO.obtenerTodas();
        
        // Agregar cada mascota como una fila en la tabla
        for (Mascota mascota : mascotas) {
            Object[] fila = {
                mascota.getId(),
                mascota.getNombre(),
                mascota.getRaza(),
                mascota.getEdad() + " años",
                mascota.getCodigo(),
                mascota.getVacunas(),
                mascota.getFechaRegistro()
            };
            modeloTabla.addRow(fila);
        }
        
        // Mostrar mensaje en la barra de estado
        setTitle("🐾 Sistema CRUD de Mascotas - " + mascotas.size() + " mascotas registradas");
    }
    
    /**
     * Buscar mascotas por nombre
     */
    private void buscarMascotas() {
        String nombre = txtBuscar.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingresa un nombre para buscar", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
        
        // Buscar mascotas
        List<Mascota> mascotas = mascotaDAO.buscarPorNombre(nombre);
        
        // Agregar resultados a la tabla
        for (Mascota mascota : mascotas) {
            Object[] fila = {
                mascota.getId(),
                mascota.getNombre(),
                mascota.getRaza(),
                mascota.getEdad() + " años",
                mascota.getCodigo(),
                mascota.getVacunas(),
                mascota.getFechaRegistro()
            };
            modeloTabla.addRow(fila);
        }
        
        // Mostrar resultado de la búsqueda
        if (mascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron mascotas con el nombre: " + nombre, 
                "Sin resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        setTitle("🐾 Sistema CRUD de Mascotas - Búsqueda: " + mascotas.size() + " resultados");
    }
    
    /**
     * Agregar una nueva mascota
     */
    private void agregarMascota() {
        // Validar datos del formulario
        if (!validarFormulario()) {
            return;
        }
        
        // Crear objeto Mascota con los datos del formulario
        Mascota nuevaMascota = new Mascota(
            txtNombre.getText().trim(),
            txtRaza.getText().trim(),
            Integer.parseInt(txtEdad.getText().trim()),
            txtCodigo.getText().trim(),
            txtVacunas.getText().trim()
        );
        
        // Insertar en la base de datos
        if (mascotaDAO.insertar(nuevaMascota)) {
            JOptionPane.showMessageDialog(this, 
                "✅ Mascota agregada exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarFormulario();
            cargarDatos();  // Recargar la tabla
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al agregar la mascota", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Actualizar mascota seleccionada
     */
    private void actualizarMascota() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor selecciona una mascota de la tabla", 
                "No hay selección", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validarFormulario()) {
            return;
        }
        
        // Crear objeto Mascota con los datos actualizados
        Mascota mascotaActualizada = new Mascota(
            txtNombre.getText().trim(),
            txtRaza.getText().trim(),
            Integer.parseInt(txtEdad.getText().trim()),
            txtCodigo.getText().trim(),
            txtVacunas.getText().trim()
        );
        mascotaActualizada.setId(idSeleccionado);
        
        // Actualizar en la base de datos
        if (mascotaDAO.actualizar(mascotaActualizada)) {
            JOptionPane.showMessageDialog(this, 
                "✅ Mascota actualizada exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            limpiarFormulario();
            cargarDatos();  // Recargar la tabla
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al actualizar la mascota", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Eliminar mascota seleccionada
     */
    private void eliminarMascota() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor selecciona una mascota de la tabla", 
                "No hay selección", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmación antes de eliminar
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que quieres eliminar esta mascota?\n" +
            "Esta acción no se puede deshacer.", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            // Eliminar de la base de datos
            if (mascotaDAO.eliminar(idSeleccionado)) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Mascota eliminada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                limpiarFormulario();
                cargarDatos();  // Recargar la tabla
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al eliminar la mascota", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Cargar los datos de una mascota seleccionada en el formulario
     */
    private void cargarMascotaEnFormulario(int fila) {
        // Obtener datos de la fila seleccionada
        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        String raza = (String) modeloTabla.getValueAt(fila, 2);
        String edadStr = (String) modeloTabla.getValueAt(fila, 3);
        String codigo = (String) modeloTabla.getValueAt(fila, 4);
        String vacunas = (String) modeloTabla.getValueAt(fila, 5);
        
        // Extraer solo el número de la edad (quitar " años")
        int edad = Integer.parseInt(edadStr.replace(" años", ""));
        
        // Cargar datos en los campos del formulario
        txtNombre.setText(nombre);
        txtRaza.setText(raza);
        txtEdad.setText(String.valueOf(edad));
        txtCodigo.setText(codigo);
        txtVacunas.setText(vacunas);
        
        // Habilitar botones de actualizar y eliminar
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        
        // Cambiar texto del botón agregar para indicar modo edición
        btnAgregar.setText("➕ Agregar Nuevo");
    }
    
    /**
     * Limpiar todos los campos del formulario
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtRaza.setText("");
        txtEdad.setText("");
        txtCodigo.setText("");
        txtVacunas.setText("");
        txtBuscar.setText("");
        
        // Resetear variables de control
        idSeleccionado = -1;
        
        // Resetear estado de los botones
        btnAgregar.setText("➕ Agregar");
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        // Limpiar selección de la tabla
        tablaMascotas.clearSelection();
    }
    
    /**
     * Validar que todos los campos obligatorios estén llenos
     */
    private boolean validarFormulario() {
        // Verificar campos vacíos
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo 'Nombre' es obligatorio");
            txtNombre.requestFocus();
            return false;
        }
        
        if (txtRaza.getText().trim().isEmpty()) {
            mostrarError("El campo 'Raza' es obligatorio");
            txtRaza.requestFocus();
            return false;
        }
        
        if (txtEdad.getText().trim().isEmpty()) {
            mostrarError("El campo 'Edad' es obligatorio");
            txtEdad.requestFocus();
            return false;
        }
        
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarError("El campo 'Código' es obligatorio");
            txtCodigo.requestFocus();
            return false;
        }
        
        // Verificar que la edad sea un número válido
        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad < 0 || edad > 50) {
                mostrarError("La edad debe ser un número entre 0 y 50 años");
                txtEdad.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("La edad debe ser un número válido");
            txtEdad.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Mostrar mensaje de error
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Error de validación", 
            JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}