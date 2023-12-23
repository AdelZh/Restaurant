package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import peaksoft.request.StopListRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.StopListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stopList")
@EnableMethodSecurity
public class StopListApi {

    private final StopListService service;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody StopListRequest stopListRequest){
        return service.save(stopListRequest);
    }


    @PutMapping
    public SimpleResponse update(@RequestParam Long id, @RequestBody StopListRequest stopListRequest){
        return service.update(id, stopListRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return service.delete(id);
    }
}
