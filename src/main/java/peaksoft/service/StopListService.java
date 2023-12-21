package peaksoft.service;

import peaksoft.request.StopListRequest;
import peaksoft.response.SimpleResponse;

public interface StopListService {


    SimpleResponse save(StopListRequest stopListRequest);
}
