package pl.edu.pwr.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.models.Receivable;

import java.time.LocalDate;
import java.util.List;

public interface ReceivablesRepository extends JpaRepository<Receivable,Integer> {
    List<Receivable> findAllByPaydayIsBetween(LocalDate from, LocalDate to);
    List<Receivable> findAllByPaydayIsBetweenAndPayed(LocalDate from, LocalDate to,boolean payed);
    List<Receivable> findByPaydayBefore(LocalDate from);
    List<Receivable> findByPaydayBeforeAndPayed(LocalDate from, boolean payed);
}
