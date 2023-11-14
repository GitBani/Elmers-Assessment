package dev.banisomo.elmersWebAPI.Part2;

import org.springframework.stereotype.Component;

@Component
public class HexStringToShortConverter {
    public static short convert(String hexString) {
        // Parse the string in base 16 then cast to a short
        return (short) Integer.parseInt(hexString, 16);
    }
}
