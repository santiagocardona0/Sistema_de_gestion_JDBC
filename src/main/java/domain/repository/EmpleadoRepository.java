package domain.repository;

import domain.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository
        extends IRepositorioExtend<Empleado, Long> {

    Optional<Empleado> findByEmail(String email);
    List<Empleado> findByDepartamentoiD(Long departamentoId);
}
