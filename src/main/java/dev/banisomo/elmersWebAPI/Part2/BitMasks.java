package dev.banisomo.elmersWebAPI.Part2;

import org.springframework.stereotype.Component;

@Component
public class BitMasks {

    public static short mask(short[] bits) {
        if (bits == null || bits.length == 0) {
            throw new IllegalArgumentException("Bit positions array must not be null or empty.");
        }

        short mask = 0;
        for (short bit : bits) {
            mask |= (short) (1 << (bit - 1));
        }

        return mask;
    }

    public static boolean checkMask(short value, short mask) {
        return (value & mask) == mask;
    }

    public static boolean checkEitherMask(short value, short mask1, short mask2) {
        return ((value & mask1) == mask1) || ((value & mask2) == mask2);
    }
}
