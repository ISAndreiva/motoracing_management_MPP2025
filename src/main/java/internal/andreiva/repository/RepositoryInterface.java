package internal.andreiva.repository;

import internal.andreiva.domain.Entity;

import java.util.UUID;

public interface RepositoryInterface<E extends Entity>
{
    void add(E entity);

    E get(UUID id);

    Iterable<E> getAll();

    void update(E entity);

    void remove(UUID id);
}
