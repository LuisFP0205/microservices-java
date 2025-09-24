package br.edu.atitus.currencyservice.controllers;

import br.edu.atitus.currencyservice.entities.CurrencyEntity;
import br.edu.atitus.currencyservice.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    private final CurrencyRepository repository ;

    @Value("${server.port}")
    private int serverPort;

    public CurrencyController(CurrencyRepository repository) {
        super ();
        this.repository = repository;
    }


    @GetMapping("/{value}/{source}/{target}" )
    public ResponseEntity<CurrencyEntity> getConvercion(
            @PathVariable Double value,
            @PathVariable String source,
            @PathVariable String target) throws Exception {
        CurrencyEntity currency = repository.findBySourceAndTarget(source,target)
                .orElseThrow(()-> new Exception("Currency not found"));

        currency.setConvertedValue(value * currency.getConversionRate());
        currency.setEnvironment("Currency running in port: " + serverPort);

        return ResponseEntity.ok(currency);
    }

}
