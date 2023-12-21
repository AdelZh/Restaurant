package peaksoft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.entity.Cheque;

import java.util.Optional;

public interface ChequeRepo extends JpaRepository<Cheque, Long> {



}
