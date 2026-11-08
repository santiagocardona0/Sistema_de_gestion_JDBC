
package infrastructure.repository;

import domain.model.Departamento;
import domain.repository.DepartamentoRepository;
import infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartamentoRepositoryJDBC implements DepartamentoRepository {

    @Override
    public List<Departamento> findByEmpresaId(Long empresaId) {
        if (empresaId == null) {
            throw new IllegalArgumentException("El ID de empresa no puede ser null");
        }

        String sql = "SELECT * FROM departamento WHERE empresa_id = ?";
        List<Departamento> departamentos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, empresaId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Departamento departamento = new Departamento(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getLong("empresa_id")
                    );
                    departamentos.add(departamento);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar departamentos por empresa", e);
        }

        return departamentos;
    }

    @Override
    public List<Departamento> findByNombreContaining(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        String sql = "SELECT * FROM departamento WHERE nombre LIKE ?";
        List<Departamento> departamentos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + nombre + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Departamento departamento = new Departamento(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getLong("empresa_id")
                    );
                    departamentos.add(departamento);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando departamentos por nombre", e);
        }

        return departamentos;
    }

    @Override
    public Optional<Departamento> findByIdOptional(Long aLong) {
        return Optional.ofNullable(findById(aLong));
    }

    @Override
    public List<Departamento> findAllToList() {
        return (List<Departamento>) findAll();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM departamento";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error contando departamentos", e);
        }

        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "DELETE FROM departamento WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando departamento", e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM departamento";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando departamentos", e);
        }
    }

    @Override
    public boolean existsById(Long aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "SELECT EXISTS (SELECT 1 FROM departamento WHERE id = ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error comprobando departamento", e);
        }

        return false;
    }

    @Override
    public Departamento findById(Long aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "SELECT * FROM departamento WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Departamento(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getLong("empresa_id")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando departamento", e);
        }

        return null;
    }

    @Override
    public Iterable<Departamento> findAll() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM departamento";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                departamentos.add(new Departamento(
                        resultSet.getLong("id"),
                        resultSet.getString("nombre"),
                        resultSet.getLong("empresa_id")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando departamentos", e);
        }

        return departamentos;
    }

    @Override
    public <S extends Departamento> S save(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("El departamento no puede ser null");
        }

        String sqlInsert = "INSERT INTO departamento(nombre, empresa_id) VALUES (?, ?)";
        String sqlUpdate = "UPDATE departamento SET nombre = ?, empresa_id = ? WHERE id = ?";

        if (entity.getId() == null) {

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, entity.getNombre());
                statement.setLong(2, entity.getEmpresaId());

                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        entity.setId(resultSet.getLong(1));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error insertando departamento", e);
            }

        } else {

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {

                statement.setString(1, entity.getNombre());
                statement.setLong(2, entity.getEmpresaId());
                statement.setLong(3, entity.getId());

                statement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Error actualizando departamento", e);
            }
        }

        return entity;
    }
}
