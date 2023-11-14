package dev.banisomo.elmersWebAPI.Part1;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NumberValidator implements Validator {

    String acceptedNum = null;

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

        // If the input is valid, set the field to that number
        // I do this because I want to keep the separate-removed form of the input number
        if (isNumber(noSpaces)) {
            this.acceptedNum = noSpaces;
            return;
        }

        if (isNumber(noCommas)) {
            this.acceptedNum = noCommas;
            return;
        }

        // Otherwise, it is an invalid number
        errors.rejectValue("input", "invalid.number", "Invalid number");

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
