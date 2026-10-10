package com.example.calculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void addEndpointReturnsSum() throws Exception {
    mockMvc.perform(get("/api/add").param("a", "2").param("b", "3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(5.0));
  }

  @Test
  void divideByZeroReturns400() throws Exception {
    mockMvc.perform(get("/api/divide").param("a", "1").param("b", "0"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("Cannot divide by zero"));
  }
}
