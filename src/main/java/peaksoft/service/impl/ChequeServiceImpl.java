package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.exception.handler.GlobalExceptionHandler;
import peaksoft.repo.ChequeRepo;
import peaksoft.repo.MenuItemRepo;
import peaksoft.repo.UserRepo;
import peaksoft.request.ChequeRequest;
import peaksoft.request.UserRequest;
import peaksoft.response.ChequeResponse;
import peaksoft.response.SimpleResponse;
import peaksoft.service.ChequeService;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChequeServiceImpl implements ChequeService {


    private final ChequeRepo chequeRepo;
    private final UserRepo userRepo;
    private final MenuItemRepo menuItemRepo;



    @Override
    public ChequeResponse save(ChequeRequest chequeRequest) {
        User user = userRepo.getUserByFirstName(chequeRequest.firstName()).orElseThrow(
                () -> {
                    log.error("user with email: " + chequeRequest.firstName() + " not found");
                    return new NotFoundException(
                            "user with email: " + chequeRequest.firstName() + " not found");
                });

        List<MenuItem> menuItems = menuItemRepo.getAll(chequeRequest.menuName());

        double price = 0.0;
        double serviceCharge = 0.1;
        int money = (int) (price * serviceCharge);


        Cheque cheque = Cheque.builder()
                .createAt(ZonedDateTime.now())
                .priceAverage((int) chequeRequest.averagePrice())
                .menuItem(menuItems)
                .build();

        for (MenuItem menuItem : menuItems) {
            if (menuItem.getStopList() == null) {
                price += menuItem.getPrice();
                menuItem.setCheque(Collections.singletonList(cheque));
                cheque.setUser(user);
                chequeRepo.save(cheque);
            } else {
                throw new AlreadyExistException("this menuItem is in stopList!!");
            }
        }
        return ChequeResponse.builder()
                .firstName(cheque.getUser().getFirstName())
                .menuName(cheque.getMenuItem())
                .averagePrice(cheque.getPriceAverage())
                .grandTotal((int) price)
                .service(money)
                .build();
    }




    @Override
    public Long countCheque(UserRequest userRequest) {
        LocalDate today=LocalDate.now();
        List<User> users=userRepo.getUserByEmail(userRequest.username()).stream().toList();


        long count=0L;

        for (User user:users){
            List<Cheque> cheques=user.getCheque();
            for(Cheque cheque:cheques){
                ZonedDateTime chequeDate=cheque.getCreateAt();
                if(chequeDate != null && chequeDate.toLocalDate().isEqual(today)){
                    count++;
                }
            }
        }
        return count;
    }
}
