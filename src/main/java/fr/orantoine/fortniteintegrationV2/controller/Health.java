package fr.orantoine.fortniteintegrationV2.controller;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class Health {

    private Counter reqEchoCounter;

    public Health(final MeterRegistry registry){
        reqEchoCounter = registry.counter("data_rest", "usecase", "healthCheck");
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> Health(){
        reqEchoCounter.increment();
        return ResponseEntity.ok("OK");
    }

}
