package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import peaksoft.request.ChequeRequest;
import peaksoft.request.UserRequest;
import peaksoft.response.ChequeResponse;
import peaksoft.service.ChequeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheque")
@EnableMethodSecurity
public class ChequeApi {


    private final ChequeService chequeService;

    @PostMapping
    public ChequeResponse save(@RequestBody ChequeRequest chequeRequest){
        return chequeService.save(chequeRequest);
    }



    @GetMapping
    public ResponseEntity<Long> countChequeByUser(@RequestBody UserRequest userRequest){
        Long count=chequeService.countCheque(userRequest);
        return ResponseEntity.ok(count);
    }

}
