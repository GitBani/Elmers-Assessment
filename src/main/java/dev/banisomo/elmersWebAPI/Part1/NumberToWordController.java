package dev.banisomo.elmersWebAPI.Part1;

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
@RequestMapping("/api/part1")
public class NumberToWordController {

    private final NumberValidator numberValidator;
    private final NumberToWordConverter numberToWordConverter;

    public NumberToWordController(NumberValidator numberValidator, NumberToWordConverter numberToWordConverter) {
        this.numberValidator = numberValidator;
        this.numberToWordConverter = numberToWordConverter;
    }

    @PostMapping("")
    public ResponseEntity<String> numberToWords(@Valid @RequestBody ApiInput input, Errors errors) {
        String number = input.getInput();
        numberValidator.validate(number, errors);

        return errors.hasErrors() ?
                new ResponseEntity<>(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(numberToWordConverter.convert(number), HttpStatus.OK);
    }
}
