package infrastructure.repository;

import domain.model.Empresa;
import domain.repository.EmpresaRepository;
import infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpresaRepositoryJDBC implements EmpresaRepository {
    @Override
    public Optional<Empresa> findByCif(String cif) {
        String sql = "SELECT * FROM empresa WHERE cif = ?";

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, cif);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empresa empresa = new Empresa(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("cif")
                    );
                    return Optional.of(empresa);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empresa por cif", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Empresa> findByNombreContaining(String nombre) {
        String sql = "SELECT * FROM empresa WHERE nombre LIKE ?";
        List<Empresa> empresas = new ArrayList<>();
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, "%"+nombre+"%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Empresa empresa = new Empresa(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("cif")
                    );
                    empresas.add(empresa);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empresas por nombre", e);
        }
        return empresas;
    }

    @Override
    public Optional<Empresa> findByIdOptional(Long aLong) {
        return Optional.ofNullable(findById(aLong));
    }

    @Override
    public List<Empresa> findAllToList() {
        return (List<Empresa>) findAll();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM empresa";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar las empresas", e);
        }
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
        String sql = "DELETE FROM empresa WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, aLong);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empresa ", e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM empresa";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empresa ", e);
        }
    }

    @Override
    public boolean existsById(Long aLong) {

        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "SELECT EXISTS (SELECT 1 FROM empresa WHERE id = ?)";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, aLong);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error comprobando empresa", e);
        }
        return false;
    }

    @Override
    public Empresa findById(Long aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser null");
        }

        String sql = "SELECT * FROM empresa WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Empresa(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("cif")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando empresa", e);
        }

        return null;
    }

    @Override
    public Iterable<Empresa> findAll() {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM empresa";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Empresa empresa = new Empresa(
                        resultSet.getLong("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("cif")
                );
                empresas.add(empresa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando empresas", e);
        }
        return empresas;
    }

    @Override
    public <S extends Empresa> S save(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La empresa no puede ser null");
        }

        String sqlInsert = "INSERT INTO empresa(nombre, cif) VALUES (?, ?)";
        String sqlUpdate = "UPDATE empresa SET nombre = ?, cif = ? WHERE id = ?";
        if (entity.getId() == null) {
            try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, entity.getNombre());
                statement.setString(2, entity.getCif());

                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        entity.setId(resultSet.getLong(1));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al insertar empresa",e);
            }
        } else {
            try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement  statement = connection.prepareStatement(sqlUpdate)) {
                statement.setString(1, entity.getNombre());
                statement.setString(2, entity.getCif());
                statement.setLong(3, entity.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar empresa", e);
            }

        }
        return entity;
    }
}
