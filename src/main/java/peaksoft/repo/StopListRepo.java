package peaksoft.repo;

import jdk.dynalink.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;

import java.util.Optional;

public interface StopListRepo extends JpaRepository<StopList, Long> {


    Optional<StopList> getStopListsByMenuItem(MenuItem menuItem);
}
