package pl.edu.pwr.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import pl.edu.pwr.library.models.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findAllById(Integer uuid);
}
