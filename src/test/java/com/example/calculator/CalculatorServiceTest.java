package com.example.calculator;

import com.example.calculator.CalculatorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {
  private final CalculatorService service = new CalculatorService();

  @Test
  void addsTwoNumbers() {
    assertEquals(5.0, service.add(2, 3));
  }

  @Test
  void subtractsTwoNumbers() {
    assertEquals(-1.0, service.subtract(2, 3));
  }

  @Test
  void multipliesTwoNumbers() {
    assertEquals(6.0, service.multiply(2, 3));
  }

  @Test
  void dividesTwoNumbers() {
    assertEquals(2.5, service.divide(5, 2));
  }

  @Test
  void divideByZeroThrows() {
    assertThrows(IllegalArgumentException.class, () -> service.divide(1, 0));
  }

  @Test
  void raisesToPower() {
    assertEquals(1024.0, service.power(2, 10));
  }
}
