package domain.repository;

import java.util.List;
import java.util.Optional;

public interface IRepositorioExtend <T, ID>
        extends IRepositorio<T,ID>{
    Optional<T> findByIdOptional(ID id);
    List<T> findAllToList();
}
