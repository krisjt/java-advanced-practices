package pl.edu.pwr.library.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.database.models.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findAllById(Integer uuid);
}
