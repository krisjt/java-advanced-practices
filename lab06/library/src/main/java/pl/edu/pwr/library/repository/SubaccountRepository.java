package pl.edu.pwr.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.models.Subaccount;

import java.util.List;

public interface SubaccountRepository extends JpaRepository<Subaccount,Integer> {
    List<Subaccount> findAllByAbonament_Id(int id);
}
