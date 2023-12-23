package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.Cheque;

import java.util.List;

public interface ChequeRepo extends JpaRepository<Cheque, Long> {

    List<Cheque> getAllById(long id);


}
