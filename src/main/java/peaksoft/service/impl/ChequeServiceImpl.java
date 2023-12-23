package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.BadCredentialsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repo.ChequeRepo;
import peaksoft.repo.MenuItemRepo;
import peaksoft.repo.UserRepo;
import peaksoft.request.ChequeRequest;
import peaksoft.request.UserRequest;
import peaksoft.response.ChequeResponse;
import peaksoft.response.SimpleResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Iterator;
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
        User user = userRepo.getUserByEmail(chequeRequest.email()).orElseThrow(
                () -> {
                    log.error("user with email: " + chequeRequest.email() + " not found");
                    return new NotFoundException(
                            "user with email: " + chequeRequest.email() + " not found");
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
    public SimpleResponse delete(Long id) {
        List<Cheque> cheques = chequeRepo.getAllById(id);
        if(cheques.isEmpty()){
            throw new NotFoundException("cheque with ID: "+id+" not found");
        }
        for (Cheque cheque : cheques) {
            Iterator<MenuItem> iterator = cheque.getMenuItem().iterator();
            while (iterator.hasNext()) {
                MenuItem menuItem = iterator.next();
                menuItem.setCheque(null);
                iterator.remove();
            }
            chequeRepo.delete(cheque);
            return new SimpleResponse(HttpStatus.OK, "deleted");
        }
        return new SimpleResponse(HttpStatus.BAD_REQUEST, "cheque not found");
    }


    @Override
    public Long countCheque(UserRequest userRequest) {
        LocalDate today=LocalDate.now();
        List<User> users=userRepo.getUserByEmail(userRequest.username()).stream().toList();

        long count=0L;

        for (User user:users){
            if(user.getCheque().isEmpty()){
                throw new BadCredentialsException("waiter did not serve guest for today: "+userRequest.username());
            }
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
