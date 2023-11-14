package dev.banisomo.elmersWebAPI.Part2;

import lombok.Data;

@Data
public class CoffeeProperties {

    // Properties
    private boolean machine_on;
    private boolean grinding_beans;
    private boolean empty_grounds_fault;
    private boolean water_empty_fault;
    private short number_of_cups_today;
    private boolean descale_required;
    private boolean have_another_one_carl;

    public CoffeeProperties(Short hex) {
        // Parse the bits in the given hex value using bit masking and set the appropriate properties
        short mask = BitMasks.mask(new short[]{1});
        this.machine_on = BitMasks.checkMask(hex, mask);

        mask = BitMasks.mask(new short[]{2});
        this.grinding_beans = BitMasks.checkMask(hex, mask);

        mask = BitMasks.mask(new short[]{3});
        this.empty_grounds_fault = BitMasks.checkMask(hex, mask);

        mask = BitMasks.mask(new short[]{4});
        this.water_empty_fault = BitMasks.checkMask(hex, mask);

        mask = BitMasks.mask(new short[]{5, 6, 7, 8, 9, 10, 11, 12});
        // after parsing bits, shift them all the way to the right to get its value
        this.number_of_cups_today = (short) ((hex & mask) >> 4);

        mask = BitMasks.mask(new short[]{15});
        this.descale_required = BitMasks.checkMask(hex, mask);

        mask = BitMasks.mask(new short[]{14, 16});
        this.have_another_one_carl = BitMasks.checkEitherMask(hex, (short) (1 << 13), (short) (1 << 15));
    }
}
