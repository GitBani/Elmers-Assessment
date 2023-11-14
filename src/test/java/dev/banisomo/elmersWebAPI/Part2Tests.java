package dev.banisomo.elmersWebAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.banisomo.elmersWebAPI.Part1.NumberValidator;
import dev.banisomo.elmersWebAPI.Part2.CoffeePropertiesController;
import dev.banisomo.elmersWebAPI.Part2.HexValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {CoffeePropertiesController.class, HexValidator.class, NumberValidator.class})
public class Part2Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper to convert objects to JSON

    @ParameterizedTest
    @MethodSource("provideTestData")
    public void hexParsingTests(String input, String expectedOutput) throws Exception {
        // Create an instance of ApiInput
        ApiInput apiInput = new ApiInput(input);

        // Convert ApiInput to JSON
        String jsonInput = objectMapper.writeValueAsString(apiInput);

        // Perform the API request and validate the response
        mockMvc.perform(post("/api/part2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutput));
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of("0xBBF1", "{\"machine_on\": true, \"grinding_beans\": false, \"empty_grounds_fault\": false, \"water_empty_fault\": false, \"number_of_cups_today\": 191, \"descale_required\": false, \"have_another_one_carl\": true}"),
                Arguments.of("0x33a3", "{\"machine_on\": true, \"grinding_beans\": true, \"empty_grounds_fault\": false, \"water_empty_fault\": false, \"number_of_cups_today\": 58, \"descale_required\": false, \"have_another_one_carl\": true}"),
                Arguments.of("0x99C1", "{\"machine_on\": true, \"grinding_beans\": false, \"empty_grounds_fault\": false, \"water_empty_fault\": false, \"number_of_cups_today\": 156, \"descale_required\": false, \"have_another_one_carl\": true}"),
                Arguments.of("0x0", "{\"machine_on\": false, \"grinding_beans\": false, \"empty_grounds_fault\": false, \"water_empty_fault\": false, \"number_of_cups_today\": 0, \"descale_required\": false, \"have_another_one_carl\": false}"),
                Arguments.of("0xF", "{\"machine_on\": true, \"grinding_beans\": true, \"empty_grounds_fault\": true, \"water_empty_fault\": true, \"number_of_cups_today\": 0, \"descale_required\": false, \"have_another_one_carl\": false}"),
                Arguments.of("0xFfFf", "{\"machine_on\": true, \"grinding_beans\": true, \"empty_grounds_fault\": true, \"water_empty_fault\": true, \"number_of_cups_today\": 255, \"descale_required\": true, \"have_another_one_carl\": true}")
        );
    }

    @ParameterizedTest
    @CsvSource({
            "\"\"",
            "0x",
            "BBBB",
            "0xXyz6",
            "0xDEADBEEF", // Larger than 2 bytes
    })
    public void hexFailureTests(String input) throws Exception {
        // Create an instance of ApiInput
        ApiInput apiInput = new ApiInput(input);

        // Convert ApiInput to JSON
        String jsonInput = objectMapper.writeValueAsString(apiInput);

        // Perform the API request and validate the response
        mockMvc.perform(post("/api/part2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest());
    }
}
