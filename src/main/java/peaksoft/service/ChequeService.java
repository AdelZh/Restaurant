package peaksoft.service;

import peaksoft.request.ChequeRequest;
import peaksoft.request.UserRequest;
import peaksoft.response.ChequeResponse;
import peaksoft.response.SimpleResponse;

public interface ChequeService {



    ChequeResponse save(ChequeRequest chequeRequest);

    Long countCheque(UserRequest userRequest);

    SimpleResponse delete(Long id);
}
