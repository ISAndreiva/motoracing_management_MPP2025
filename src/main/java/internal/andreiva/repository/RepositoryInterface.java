package internal.andreiva.repository;

import internal.andreiva.domain.Entity;

import java.util.UUID;

public interface RepositoryInterface<Id ,E extends Entity<Id>>
{
    void add(E entity);

    E get(Id id);

    Iterable<E> getAll();

    void update(E entity);

    void remove(UUID id);
}
