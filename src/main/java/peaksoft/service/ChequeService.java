package peaksoft.service;

import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import peaksoft.request.ChequeRequest;
import peaksoft.request.UserRequest;
import peaksoft.response.ChequeResponse;
import peaksoft.response.SimpleResponse;

public interface ChequeService {



    ChequeResponse save(ChequeRequest chequeRequest);

    Long countCheque(UserRequest userRequest);
}
