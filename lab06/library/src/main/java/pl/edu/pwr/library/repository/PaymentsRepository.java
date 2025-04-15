package pl.edu.pwr.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.models.Abonament;
import pl.edu.pwr.library.models.Payment;
import pl.edu.pwr.library.models.Receivable;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payment,Integer> {
    Payment findByReceivable(Receivable receivable);
    List<Payment> findAllByAbonament(Abonament abonament);
    List<Payment> findAllByReceivable_Id(int id);
}
