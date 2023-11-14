package dev.banisomo.elmersWebAPI.Part2;

import lombok.Data;

@Data
public class CoffeeProperties {

    // Properties
    private boolean machine_on;
    private boolean grinding_beans;
    private boolean empty_grounds_fault;
    private boolean water_empty_fault;
    private int number_of_cups_today;
    private boolean descale_required;
    private boolean have_another_one_carl;

    // Bitmasks to parse bits in the hex value
    private short machine_on_mask = 1;
    private short grinding_beans_mask = 1 << 1;
    private short empty_grounds_fault_mask = 1 << 2;
    private short water_empty_fault_mask = 1 << 3;
    private short number_of_cups_today_mask = cupsMaskSetter();
    private short descale_required_mask = 1 << 14;
    private int have_another_one_carl_mask = 1 << 13 | 1 << 15;

    private short cupsMaskSetter() {
        short mask = 0;
        for (short i = 4; i < 12; i++) {
            mask |= (short) (1 << i);
        }

        return mask;
    }

    public CoffeeProperties(Short hex) {
        this.machine_on = (hex & machine_on_mask) == machine_on_mask;
        this.grinding_beans = (hex & grinding_beans_mask) == grinding_beans_mask;
        this.empty_grounds_fault = (hex & empty_grounds_fault_mask) == empty_grounds_fault_mask;
        this.water_empty_fault = (hex & water_empty_fault_mask) == water_empty_fault_mask;
        this.number_of_cups_today = (hex & number_of_cups_today_mask) >> 5;
        this.descale_required = (hex & descale_required_mask) == descale_required_mask;
        this.have_another_one_carl = (hex & have_another_one_carl_mask) == have_another_one_carl_mask;
    }
}
