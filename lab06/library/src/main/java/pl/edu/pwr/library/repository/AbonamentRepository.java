package pl.edu.pwr.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.models.Abonament;

import java.util.List;

public interface AbonamentRepository extends JpaRepository<Abonament,Integer> {

    List<Abonament> findAllByClient_Id(int id);
}
