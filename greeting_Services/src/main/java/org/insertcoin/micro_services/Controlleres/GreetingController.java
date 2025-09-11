package org.insertcoin.micro_services.Controlleres;

import org.insertcoin.micro_services.service.Configs.GreetingConfigs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingConfigs config;

    public GreetingController(GreetingConfigs config) {
        this.config = config;
    }


    @GetMapping("/{name}")
    public ResponseEntity<String> greetingWithPathVariable(@PathVariable String name) {
        String greetingReturn = config.getGreeting();
        String textReturn = String.format("%s, %s!!!", greetingReturn, name);
        return ResponseEntity.ok(textReturn);
    }


    @GetMapping
    public ResponseEntity<String> greetingWithRequestParam(
            @RequestParam(required = false) String name
    ) {
        String greeting = config.getGreeting();
        String nameToUse = (name != null && !name.isEmpty()) ? name : config.getDefaultName();

        String response = String.format("%s, %s!!!", greeting, nameToUse);

        return ResponseEntity.ok(response);
    }

   
    @PostMapping
    public ResponseEntity<String> greetingWithBody(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        if (name == null || name.isEmpty()) {
            name = config.getDefaultName();
        }

        String greeting = config.getGreeting();
        String response = String.format("%s, %s!!!", greeting, name);
        return ResponseEntity.ok(response);
    }
}