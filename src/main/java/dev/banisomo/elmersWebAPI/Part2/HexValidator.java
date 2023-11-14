package dev.banisomo.elmersWebAPI.Part2;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Getter
@Component
public class HexValidator implements Validator {

    private Short acceptedHex = null;

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String hexString = (String) target;

        if (!hexString.startsWith("0x")) {
            errors.rejectValue("input", "invalid.prefix", "Prefix must be '0x'");
        }

        hexString = hexString.substring(2);

        if (hexString.isEmpty() || hexString.length() > 4) {
            errors.rejectValue("input", "range.error", "Hex number must be 2 bytes");
        }

        try {
            acceptedHex = (short) Integer.parseInt(hexString, 16);
        } catch (NumberFormatException e) {
            errors.rejectValue("input", "invalid.hex", "Invalid hex number");
        }
    }
}
