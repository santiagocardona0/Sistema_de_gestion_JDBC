package app;

import domain.model.*;
import infrastructure.database.DatabaseConnection;
import infrastructure.repository.*;

public class Main {

    public static void main(String[] args) {

        try {
            EmpresaRepositoryJDBC empresaRepo =
                    new EmpresaRepositoryJDBC();

            DepartamentoRepositoryJDBC departamentoRepo =
                    new DepartamentoRepositoryJDBC();

            EmpleadoRepositoryJDBC empleadoRepo =
                    new EmpleadoRepositoryJDBC();

            System.out.println("Conexión a la base de datos correcta.");

            System.out.println("Empresas: " + empresaRepo.count());
            System.out.println("Departamentos: " + departamentoRepo.count());
            System.out.println("Empleados: " + empleadoRepo.count());

        } catch (Exception e) {
            System.err.println("Error al ejecutar la aplicación: "
                    + e.getMessage());
        }
    }
}