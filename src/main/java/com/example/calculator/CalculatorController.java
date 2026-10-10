
package com.example.calculator;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalculatorController {
  private final CalculatorService service;

  public CalculatorController(CalculatorService service) {
    this.service = service;
  }

  @GetMapping("/add")
  public Map<String, Double> add(@RequestParam double a, @RequestParam double b) {
    return Map.of("result", service.add(a, b));
  }

  @GetMapping("/subtract")
  public Map<String, Double> subtract(@RequestParam double a, @RequestParam double b) {
    return Map.of("result", service.subtract(a, b));
  }

  @GetMapping("/multiply")
  public Map<String, Double> multiply(@RequestParam double a, @RequestParam double b) {
    return Map.of("result", service.multiply(a, b));
  }

  @GetMapping("/divide")
  public Map<String, Double> divide(@RequestParam double a, @RequestParam double b) {
    return Map.of("result", service.divide(a, b));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleBadInput(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
  }
}
