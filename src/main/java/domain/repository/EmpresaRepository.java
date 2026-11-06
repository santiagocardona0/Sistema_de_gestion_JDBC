package domain.repository;

import domain.model.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository
        extends  IRepositorioExtend <Empresa, Long> {

    Optional<Empresa> findByCif(String cif);
    List<Empresa> findByNombreContaining(String nombre);
}
