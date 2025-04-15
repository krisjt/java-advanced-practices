package pl.edu.pwr.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.library.models.PriceList;
import pl.edu.pwr.library.models.Type;

import java.util.List;

public interface PriceListRepository extends JpaRepository<PriceList,Integer> {
    PriceList findByAbonamentType(int id);
    PriceList findByAbonamentTypeAndActive(Type abonamentType, boolean active);
    List<PriceList> findAllByAbonamentTypeAndActive(Type abonamentType, boolean active);
    List<PriceList> findByActive(boolean active);
}
