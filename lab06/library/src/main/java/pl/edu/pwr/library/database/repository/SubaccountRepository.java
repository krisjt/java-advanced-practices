package pl.edu.pwr.library.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.database.models.Subaccount;

import java.util.List;

public interface SubaccountRepository extends JpaRepository<Subaccount,Integer> {
    List<Subaccount> findAllByAbonament_Id(int id);
}
