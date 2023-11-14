package dev.banisomo.elmersWebAPI.Part1;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NumberToWordConverter {

    private final Map<Character, String> ones = new HashMap<>() {{
        put('0', "");
        put('1', " one");
        put('2', " two");
        put('3', " three");
        put('4', " four");
        put('5', " five");
        put('6', " six");
        put('7', " seven");
        put('8', " eight");
        put('9', " nine");
    }};

    private final Map<Character, String> teens = new HashMap<>() {{
        put('0', " ten");
        put('1', " eleven");
        put('2', " twelve");
        put('3', " thirteen");
        put('4', " fourteen");
        put('5', " fifteen");
        put('6', " sixteen");
        put('7', " seventeen");
        put('8', " eighteen");
        put('9', " nineteen");
    }};
    private final Map<Character, String> tens = new HashMap<>() {{
        put('2', " twenty");
        put('3', " thirty");
        put('4', " forty");
        put('5', " fifty");
        put('6', " sixty");
        put('7', " seventy");
        put('8', " eighty");
        put('9', " ninety");
    }};
    private final Map<Integer, String> sectionSuffix = new HashMap<>() {{
        put(4, " billion");
        put(3, " million");
        put(2, " thousand");
    }};


    /* This converter is based on the fact that every "section" of a number
     * is in the hundreds at most. For example: 136,000
     * the "thousands" section is one hundred thirty-six
     *
     * By Dealing with each section to get its "hundreds" value, then appending that sections
     * suffix, we can easily convert the number into its word.
     */
    public String convert(String number) {
        // Remove separators from number (only one of these calls does the work,
        // since we validated this string beforehand
        number = number.replaceAll(",", "");
        number = number.replaceAll(" ", "");

        // Number as an integer to solve cases of 0
        // Issues arose from inputs such as 000
        int numAsInt = Integer.parseInt(number);
        // If the number is 0, simply return Zero here to avoid issues
        if (numAsInt == 0)
            return "Zero";

        // Final result
        StringBuilder numberAsWord = new StringBuilder();
        // Used to parse the string
        int i = 0;

        // number of digits
        int numDigits = number.length();

        // Negative sign
        if (number.charAt(i) == '-') {
            numberAsWord.append("negative");
            ++i;
            --numDigits;
        }

        // number of sections (e.g. 333,222 will have 2)
        int numSections = (int) Math.ceil(numDigits / (double) 3);

        // Since we parse the string from left to right, begin at the largest section
        int currentSection = numSections;

        // first section might not be 3 digits
        if (numDigits % 3 != 0) {
            numberAsWord.append(
                    convertSection(
                            number.substring(i, i + numDigits % 3),
                            currentSection--,
                            false
                    )
            );

            i += numDigits % 3;
        }

        // Loop remaining sections, there is a multiple of 3 digits remaining
        for (; i < numDigits; i += 3) {
            numberAsWord.append(
                    convertSection(
                            number.substring(i, i + 3),
                            currentSection,
                            // Only add an "and" if the number has more than 3 digits, and we are in the final section
                            currentSection == 1 && numSections > 1
                    )
            );

            currentSection--;
        }

        // Remove Leading space
        if (numberAsWord.charAt(0) == ' ')
            numberAsWord.replace(0, 1, "");

        // Return with first letter capitalized
        return numberAsWord.substring(0,1).toUpperCase() + numberAsWord.substring(1).toLowerCase();
    }

    // Converts a single section into words
    private String convertSection(String number, int section, boolean includeAnd) {
        // pad number in case this section less than 3 digits (can only happen with the leftmost section)
        while (number.length() < 3) {
            number = "0" + number;
        }

        if (number.equals("000"))
            return "";

        // Final result
        StringBuilder numberAsWord = new StringBuilder();

        // First Digit
        char c = number.charAt(0);
        if (c != '0') {
            numberAsWord.append(ones.get(c)).append(" hundred");
        }

        // "ands" are added for the last 2 digits in some cases
        if (includeAnd)
            numberAsWord.append(" and");

        // Second digit
        c = number.charAt(1);
        // If the digit here is a 1, we skip the "ones" case and get the value from the teens map
        if (c == '1') {
            c = number.charAt(2);
            numberAsWord.append(teens.get(c));

            // Add section suffix and exit
            if(section > 1)
                numberAsWord.append(sectionSuffix.get(section));

            return numberAsWord.toString();
        }
        // Otherwise, we add the tens value ond continue to ones
        else if (c != '0') {
            numberAsWord.append(tens.get(c));
        }

        // Third digit
        c = number.charAt(2);
        if (c != 0) {
            numberAsWord.append(ones.get(c));
        }

        // Add section suffix and exit
        if(section > 1)
            numberAsWord.append(sectionSuffix.get(section));

        return numberAsWord.toString();
    }
}
