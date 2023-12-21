package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.request.StopListRequest;
import peaksoft.response.SimpleResponse;
import peaksoft.service.StopListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stopList")
@EnableMethodSecurity
public class StopListApi {

    private final StopListService service;


    @PostMapping("/save")
    public SimpleResponse save(@RequestBody StopListRequest stopListRequest){
        return service.save(stopListRequest);
    }
}
