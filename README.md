# 🐾 Sistema CRUD de Mascotas

Sistema de gestión de mascotas desarrollado en Java con interfaz gráfica Swing, Maven y base de datos MySQL.

## 📋 Características

- ✅ **CRUD Completo**: Crear, Leer, Actualizar y Eliminar mascotas
- 🎨 **Interfaz Gráfica Moderna**: Diseño intuitivo con Swing
- 🔍 **Sistema de Búsqueda**: Buscar mascotas por nombre
- 🗄️ **Base de Datos MySQL**: Almacenamiento persistente
- ✔️ **Validaciones**: Campos obligatorios y tipos de datos
- 🏗️ **Arquitectura MVC**: Separación de responsabilidades

## 🛠️ Tecnologías Utilizadas

- **Java 11+**
- **Maven** (Gestión de dependencias)
- **MySQL 8.0+**
- **Swing** (Interfaz gráfica)
- **JDBC** (Conexión a base de datos)

## 📦 Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

1. **Java Development Kit (JDK) 11 o superior**
2. **MySQL Server 8.0 o superior**
3. **NetBeans IDE** (opcional, recomendado)
4. **Maven** (incluido en NetBeans)

## 🗄️ Configuración de la Base de Datos

### Paso 1: Iniciar MySQL

Asegúrate de que MySQL Server esté ejecutándose en tu sistema.

### Paso 2: Acceder a MySQL

Abre tu terminal o línea de comandos y conecta a MySQL:

```bash
mysql -u root -p
```

**Importante:** Cuando te pida la contraseña, simplemente presiona `Enter` (contraseña en blanco).

### Paso 3: Crear la Base de Datos

Ejecuta los siguientes comandos en MySQL:

```sql
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS crud_mascotas;

-- Usar la base de datos
USE crud_mascotas;

-- Crear la tabla mascotas
CREATE TABLE mascotas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    raza VARCHAR(50) NOT NULL,
    edad INT NOT NULL,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    vacunas TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos de ejemplo (opcional)
INSERT INTO mascotas (nombre, raza, edad, codigo, vacunas) VALUES
('Max', 'Labrador', 3, 'DOG001', 'Rabia, Parvovirus, Moquillo'),
('Luna', 'Persa', 2, 'CAT001', 'Triple felina, Rabia'),
('Rocky', 'Bulldog', 5, 'DOG002', 'Rabia, Parvovirus, Bordetella');

-- Verificar que los datos se crearon correctamente
SELECT * FROM mascotas;
```

### Paso 4: Verificar la Configuración

Para confirmar que todo está configurado correctamente:

```sql
-- Verificar que la base de datos existe
SHOW DATABASES;

-- Verificar que la tabla existe
USE crud_mascotas;
SHOW TABLES;

-- Verificar la estructura de la tabla
DESCRIBE mascotas;
```

## ⚙️ Configuración del Usuario MySQL

**IMPORTANTE:** La aplicación está configurada para usar:

- **Usuario:** `root`
- **Contraseña:** *(en blanco)*
- **Puerto:** `3306`
- **Host:** `localhost`

### Si tu usuario root tiene contraseña:

Si tu usuario root de MySQL tiene contraseña, tienes dos opciones:

#### Opción 1: Quitar la contraseña (Recomendado para desarrollo)

```sql
-- Conectar a MySQL como root
mysql -u root -p

-- Cambiar la contraseña a vacío
ALTER USER 'root'@'localhost' IDENTIFIED BY '';
FLUSH PRIVILEGES;
```

#### Opción 2: Modificar el código

Edita el archivo `ConexionDB.java` y cambia la línea:

```java
private static final String PASSWORD = "tu_contraseña_aqui";
```

## 🚀 Instalación y Ejecución

### Método 1: Usando NetBeans

1. **Clonar o descargar el proyecto**
2. **Abrir NetBeans**
3. **File → Open Project** y seleccionar la carpeta del proyecto
4. **Esperar** a que Maven descargue las dependencias
5. **Clic derecho en el proyecto → Run**

### Método 2: Línea de comandos

```bash
# Navegar al directorio del proyecto
cd ruta/del/proyecto

# Compilar el proyecto
mvn compile

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="com.mycompany.crudmascotas.Main"
```

## 📱 Uso de la Aplicación

### Interfaz Principal

La aplicación cuenta con:

1. **Tabla de Mascotas**: Muestra todas las mascotas registradas
2. **Formulario de Datos**: Para agregar/editar información
3. **Botones de Acción**: Agregar, Actualizar, Eliminar, Limpiar
4. **Campo de Búsqueda**: Para buscar mascotas por nombre

### Operaciones CRUD

#### ➕ Agregar Mascota
1. Completar todos los campos del formulario
2. Hacer clic en **"Agregar"**
3. La mascota aparecerá en la tabla

#### ✏️ Editar Mascota
1. Hacer clic en una fila de la tabla
2. Los datos se cargarán en el formulario
3. Modificar los campos necesarios
4. Hacer clic en **"Actualizar"**

#### 🗑️ Eliminar Mascota
1. Seleccionar una mascota de la tabla
2. Hacer clic en **"Eliminar"**
3. Confirmar la eliminación

#### 🔍 Buscar Mascota
1. Escribir el nombre en el campo de búsqueda
2. Hacer clic en **"Buscar"**
3. Para ver todas: **"Mostrar Todos"**

## 📝 Campos de la Mascota

- **Nombre**: Nombre de la mascota (obligatorio)
- **Raza**: Raza o tipo de mascota (obligatorio)
- **Edad**: Edad en años (0-50) (obligatorio)
- **Código**: Código único de identificación (obligatorio)
- **Vacunas**: Lista de vacunas aplicadas (opcional)

## 🐛 Solución de Problemas

### Error de Conexión a MySQL

Si aparece error de conexión, verifica:

1. **MySQL está ejecutándose**:
   ```bash
   # Windows
   net start mysql80
   
   # Linux/Mac
   sudo systemctl start mysql
   ```

2. **La base de datos existe**:
   ```sql
   SHOW DATABASES;
   ```

3. **Las credenciales son correctas** (usuario: root, contraseña: vacía)

### Error "Table doesn't exist"

Ejecuta nuevamente los comandos de creación de la base de datos.

### Error de compilación

Asegúrate de tener:
- Java 11 o superior
- Maven configurado correctamente
- Todas las dependencias descargadas

## 📁 Estructura del Proyecto

```
src/main/java/
└── com/mycompany/crudmascotas/
    ├── Main.java                    # Clase principal
    ├── conexion/
    │   └── ConexionDB.java         # Conexión a MySQL
    ├── dao/
    │   └── MascotaDAO.java         # Operaciones CRUD
    ├── gui/
    │   └── VentanaPrincipal.java   # Interfaz gráfica
    └── modelo/
        └── Mascota.java            # Modelo de datos
```

## 🤝 Contribuciones

Si encuentras algún error o quieres mejorar la aplicación:

1. Reporta el problema en los issues
2. Sugiere mejoras
3. Comparte tu experiencia de uso

## 📞 Soporte

Si tienes problemas:

1. Verifica que seguiste todos los pasos de configuración
2. Revisa la consola de NetBeans para errores específicos
3. Asegúrate de que MySQL esté funcionando correctamente

---

¡Disfruta gestionando tus mascotas! 🐾
