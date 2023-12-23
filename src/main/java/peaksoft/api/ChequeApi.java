package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import peaksoft.request.ChequeRequest;
import peaksoft.request.UserRequest;
import peaksoft.response.ChequeResponse;
import peaksoft.response.SimpleResponse;
import peaksoft.service.ChequeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheque")
@EnableMethodSecurity
public class ChequeApi {


    private final ChequeService chequeService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @PostMapping
    public ChequeResponse save(@RequestBody ChequeRequest chequeRequest){
        return chequeService.save(chequeRequest);
    }


    @GetMapping
    public ResponseEntity<Long> countChequeByUser(@RequestBody UserRequest userRequest){
        Long count=chequeService.countCheque(userRequest);
        return ResponseEntity.ok(count);
    }


    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return chequeService.delete(id);
    }

}
