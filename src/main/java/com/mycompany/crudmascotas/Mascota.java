/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudmascotas;

/**
 *
 * @author cirom
 */
import java.sql.Timestamp;

/**
 * Clase que representa una mascota en el sistema
 * Esta clase sirve como modelo de datos (POJO - Plain Old Java Object)
 */
public class Mascota {
    
    // Atributos privados (encapsulación)
    private int id;                    // ID único de la mascota
    private String nombre;             // Nombre de la mascota
    private String raza;               // Raza de la mascota
    private int edad;                  // Edad en años
    private String codigo;             // Código único de identificación
    private String vacunas;            // Lista de vacunas aplicadas
    private Timestamp fechaRegistro;   // Fecha y hora de registro
    
    // Constructor vacío (necesario para crear objetos sin parámetros)
    public Mascota() {
    }
    
    // Constructor con parámetros (para crear mascota con datos)
    public Mascota(String nombre, String raza, int edad, String codigo, String vacunas) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.codigo = codigo;
        this.vacunas = vacunas;
    }
    
    // Constructor completo (incluye ID y fecha para datos existentes en BD)
    public Mascota(int id, String nombre, String raza, int edad, String codigo, String vacunas, Timestamp fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.codigo = codigo;
        this.vacunas = vacunas;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Métodos getter y setter (para acceder y modificar los atributos)
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public void setRaza(String raza) {
        this.raza = raza;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getVacunas() {
        return vacunas;
    }
    
    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    // Método toString para mostrar la información de la mascota
    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", codigo='" + codigo + '\'' +
                ", vacunas='" + vacunas + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
