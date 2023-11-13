package dev.banisomo.elmersWebAPI.Part1;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NumberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // valid numbers can be space separated (like 8 000 000)
        // or comma separated (like 8,000,000)

        String number = (String) target;

        String noSpaces = number.replaceAll(" ", "");
        String noCommas = number.replaceAll(",", "");

        if (!isNumber(noSpaces) && !isNumber(noCommas)) {
            errors.rejectValue("input", "invalid.number", "Invalid number");
        }
    }

    private boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
