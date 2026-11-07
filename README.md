# Sistema de gestión JDBC – Java y PostgreSQL

Aplicación desarrollada en Java para gestionar empresas, departamentos y empleados mediante una base de datos PostgreSQL.

El objetivo del proyecto es trabajar con JDBC y comprender cómo se estructura una aplicación que accede a una base de datos relacional, separando las entidades del dominio de las operaciones de persistencia.

## Tecnologías

- Java
- JDBC
- PostgreSQL
- SQL
- Git

## Estructura del proyecto

El código está organizado en dos partes principales:

```text
src/
├── domain/
│   ├── model/
│   └── repository/
│
└── infrastructure/
    ├── database/
    └── repository/
```

- **domain.model:** contiene las entidades Empresa, Departamento y Empleado.
- **domain.repository:** define las interfaces y los contratos de los repositorios.
- **infrastructure.database:** gestiona la conexión con PostgreSQL.
- **infrastructure.repository:** contiene las implementaciones de los repositorios mediante JDBC.

## Modelo de datos

La base de datos contiene tres entidades relacionadas:

- **Empresa:** almacena el nombre y CIF de cada empresa.
- **Departamento:** pertenece a una empresa.
- **Empleado:** pertenece a un departamento y almacena su nombre y correo electrónico.

Relaciones:

```text
Empresa
   │
   └── Departamento
            │
            └── Empleado
```

## Funcionalidades

- Operaciones de consulta, inserción y eliminación.
- Búsqueda de registros por identificador.
- Consultas personalizadas por atributos.
- Comprobación de existencia de registros.
- Recuento de registros.
- Uso de `PreparedStatement` para ejecutar consultas SQL parametrizadas.
- Gestión de resultados con `ResultSet`.
- Uso de `Optional` para representar resultados que pueden no existir.

## Configuración

### 1. Crear la base de datos

Crea una base de datos PostgreSQL y ejecuta el script SQL del proyecto para generar las tablas y sus relaciones.

### 2. Configurar las variables de entorno

La aplicación utiliza estas variables para conectarse a PostgreSQL:

```env
DB_URL=jdbc:postgresql://localhost:5432/nombre_base_datos
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

Sustituye los valores por los datos de tu propia conexión.

### 3. Ejecutar la aplicación

Abre el proyecto en IntelliJ IDEA, configura las variables de entorno y ejecuta la clase principal.

## Qué estoy trabajando con este proyecto

Este proyecto forma parte de mi formación en Desarrollo de Aplicaciones Multiplataforma (DAM).

Con él estoy profundizando en:

- Acceso a bases de datos desde Java.
- Diseño e implementación de repositorios.
- Programación orientada a objetos.
- Separación entre dominio e infraestructura.
- Relaciones entre entidades y consultas SQL.

---

**Autor:** Santiago Cardona  
**GitHub:** https://github.com/santiagocardona0  
**Web:** https://santiagocardona.xyz
