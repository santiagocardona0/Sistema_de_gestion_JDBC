package app;

import Infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Aplicación JDBC iniciada");

        //Comprobar la conexion de la base de datos
        try(Connection connection = DatabaseConnection.getConnection() ) {
            System.out.println("Conexion a postgre correctamente");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }
}