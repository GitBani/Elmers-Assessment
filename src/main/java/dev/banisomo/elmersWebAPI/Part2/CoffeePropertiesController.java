package dev.banisomo.elmersWebAPI.Part2;

import dev.banisomo.elmersWebAPI.ApiInput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/part2")
public class CoffeePropertiesController {

    private final HexValidator validator;

    public CoffeePropertiesController(HexValidator validator) {
        this.validator = validator;
    }

    @PostMapping("")
    public ResponseEntity<CoffeeProperties> setCoffeeProperties(@Valid @RequestBody ApiInput input, Errors errors) {
        String hex = input.getInput();
        validator.validate(hex, errors);

        if (errors.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                // Since input was valid, validator saved the hex string as a short
                new CoffeeProperties(validator.getAcceptedHex()),
                HttpStatus.OK
        );
    }
}
