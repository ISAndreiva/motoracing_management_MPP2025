package internal.andreiva.concursmotociclism.repository;

import internal.andreiva.concursmotociclism.domain.Entity;

import java.util.UUID;

public interface RepositoryInterface<Id ,E extends Entity<Id>>
{
    void add(E entity);

    E get(Id id);

    Iterable<E> getAll();

    void update(E entity);

    void remove(UUID id);
}
