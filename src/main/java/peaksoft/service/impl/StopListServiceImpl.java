package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.MenuItemRepo;
import peaksoft.repo.StopListRepo;
import peaksoft.request.StopListRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.MenuItemService;
import peaksoft.service.StopListService;

import java.awt.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StopListServiceImpl implements StopListService {

    private final StopListRepo stopListRepo;
    private final MenuItemRepo menuItemRepo;

    @Override
    public SimpleResponse save(StopListRequest stopListRequest) {
        MenuItem menuItem=menuItemRepo.getMenuItemsByName(stopListRequest.menuName()).orElseThrow(
                () -> {
                    String message="such food not found with name: "+stopListRequest.menuName();
                    log.error(message);
                    return new NotFoundException(message);
                }
        );

        Optional<StopList> existStopList=stopListRepo.getStopListsByMenuItem(menuItem);

        if(existStopList.isPresent()){
          throw new AlreadyExistException("stopList already have been created");
        }else {
            StopList stopList=StopList.builder()
                    .date(stopListRequest.date())
                    .reason(stopListRequest.reason())
                    .build();

            stopList.setMenuItem(menuItem);
            menuItem.setStopList(stopList);
            stopListRepo.save(stopList);
        }

        return new SimpleResponse(HttpStatus.OK, "saved");
    }
}
