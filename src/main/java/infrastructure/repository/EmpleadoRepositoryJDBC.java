
package infrastructure.repository;

import domain.model.Empleado;
import domain.repository.EmpleadoRepository;
import infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoRepositoryJDBC implements EmpleadoRepository {

    @Override
    public Optional<Empleado> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        String sql = "SELECT * FROM empleado WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empleado empleado = new Empleado(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email"),
                            resultSet.getLong("departamento_id")
                    );
                    return Optional.of(empleado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar email", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Empleado> findByDepartamentoId(Long departamentoId) {
        if (departamentoId == null) {
            throw new IllegalArgumentException("El ID de departamento no puede ser null");
        }

        String sql = "SELECT * FROM empleado WHERE departamento_id = ?";
        List<Empleado> empleados = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, departamentoId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Empleado empleado = new Empleado(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email"),
                            resultSet.getLong("departamento_id")
                    );
                    empleados.add(empleado);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados por departamento", e);
        }

        return empleados;
    }

    @Override
    public Optional<Empleado> findByIdOptional(Long id) {
        return Optional.ofNullable(findById(id));
    }

    @Override
    public List<Empleado> findAllToList() {
        return (List<Empleado>) findAll();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM empleado";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al contar empleados", e);
        }

        return 0;
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "DELETE FROM empleado WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleado", e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM empleado";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleados", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "SELECT EXISTS(SELECT 1 FROM empleado WHERE id = ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al comprobar empleado", e);
        }

        return false;
    }

    @Override
    public Empleado findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "SELECT * FROM empleado WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Empleado(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email"),
                            resultSet.getLong("departamento_id")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado por ID", e);
        }

        return null;
    }

    @Override
    public Iterable<Empleado> findAll() {
        String sql = "SELECT * FROM empleado";
        List<Empleado> empleados = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Empleado empleado = new Empleado(
                        resultSet.getLong("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        resultSet.getLong("departamento_id")
                );

                empleados.add(empleado);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar empleados", e);
        }

        return empleados;
    }

    @Override
    public <S extends Empleado> S save(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("El empleado no puede ser null");
        }

        if (entity.getId() == null) {
            String sql = "INSERT INTO empleado (nombre, email, departamento_id) VALUES (?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, entity.getNombre());
                statement.setString(2, entity.getEmail());
                statement.setLong(3, entity.getDepartamentoId());

                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error al insertar empleado", e);
            }

        } else {
            String sql = "UPDATE empleado SET nombre = ?, email = ?, departamento_id = ? WHERE id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, entity.getNombre());
                statement.setString(2, entity.getEmail());
                statement.setLong(3, entity.getDepartamentoId());
                statement.setLong(4, entity.getId());

                statement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar empleado", e);
            }
        }

        return entity;
    }
}
