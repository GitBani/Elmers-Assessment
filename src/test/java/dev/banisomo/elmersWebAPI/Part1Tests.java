package dev.banisomo.elmersWebAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.banisomo.elmersWebAPI.Part1.NumberToWordController;
import dev.banisomo.elmersWebAPI.Part1.NumberValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {NumberToWordController.class, NumberValidator.class})
public class Part1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper to convert objects to JSON

    @ParameterizedTest
    @CsvSource({
            "0, Zero",
            "13, Thirteen",
            "85, Eighty five",
            "-10, Negative ten",
            "673, Six hundred seventy three",
            "-888, Negative eight hundred eighty eight",
            "5 237, Five thousand two hundred and thirty seven",
            "-1333, Negative one thousand three hundred and thirty three",
            "16455, Sixteen thousand four hundred and fifty five",
            "-99 999, Negative ninety nine thousand nine hundred and ninety nine",
            "105 105 105, One hundred five million one hundred five thousand one hundred and five",
            "-500 000 001, Negative five hundred million and one",
            "2 147 483 646, Two billion one hundred forty seven million four hundred eighty three thousand six hundred and forty six",
            "-1888888888, Negative one billion eight hundred eighty eight million eight hundred eighty eight thousand eight hundred and eighty eight"
    })
    public void numberToWordConversionTests(String input, String expectedOutput) throws Exception {
        // Create an instance of ApiInput
        ApiInput apiInput = new ApiInput(input);

        // Convert ApiInput to JSON
        String jsonInput = objectMapper.writeValueAsString(apiInput);

        // Perform the API request and validate the response
        mockMvc.perform(post("/api/part1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedOutput));
    }

    @ParameterizedTest
    @CsvSource({
            "--1",
            "Hello",
            "One",
            "1.357",
            "3 000 000 000", // Larger than a 32-bit int
            "333-33",
            "8e10"
    })
    public void numberToWordFailureTests(String input) throws Exception {
        // Create an instance of ApiInput
        ApiInput apiInput = new ApiInput(input);

        // Convert ApiInput to JSON
        String jsonInput = objectMapper.writeValueAsString(apiInput);

        // Perform the API request and validate the response
        mockMvc.perform(post("/api/part1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest());
    }
}
