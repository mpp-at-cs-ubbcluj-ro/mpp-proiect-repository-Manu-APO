package repository;

import competition.Registry;

public interface RegistryRepository extends RepositoryCrud<Long, Registry> {
    Registry getRegistryByCredentials(String username, String password);
}


