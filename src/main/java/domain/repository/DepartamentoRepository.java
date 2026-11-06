package domain.repository;

import domain.model.Departamento;

import java.util.List;

public interface DepartamentoRepository
        extends IRepositorioExtend<Departamento, Long> {
    List<Departamento> findByEmpresaId(Long empresaId);
    List<Departamento> findByNombreContaining(String nombre);
}
