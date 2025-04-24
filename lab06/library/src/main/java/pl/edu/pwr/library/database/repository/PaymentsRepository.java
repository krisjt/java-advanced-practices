package pl.edu.pwr.library.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.database.models.Abonament;
import pl.edu.pwr.library.database.models.Payment;
import pl.edu.pwr.library.database.models.Receivable;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payment,Integer> {
    Payment findByReceivable(Receivable receivable);
    List<Payment> findAllByAbonament(Abonament abonament);
    List<Payment> findAllByReceivable_Id(int id);
}
