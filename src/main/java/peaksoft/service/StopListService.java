package peaksoft.service;

import peaksoft.request.StopListRequest;
import peaksoft.response.SimpleResponse;

public interface StopListService {


    SimpleResponse save(StopListRequest stopListRequest);

    SimpleResponse delete(Long id);

    SimpleResponse update(Long id, StopListRequest stopListRequest);
}
