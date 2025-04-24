package pl.edu.pwr.library.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.database.models.PriceList;
import pl.edu.pwr.library.database.models.Type;

import java.util.List;

public interface PriceListRepository extends JpaRepository<PriceList,Integer> {
    PriceList findByAbonamentType(int id);
    PriceList findByAbonamentTypeAndActive(Type abonamentType, boolean active);
    List<PriceList> findAllByAbonamentTypeAndActive(Type abonamentType, boolean active);
    List<PriceList> findByActive(boolean active);
}
