package com.nexus.screening.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Controller connecting Spring Boot Backend to Flask AI/ML Service (BE-25).
 * Invokes Flask ML model for developmental screening inference.
 */
@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*")
public class AnalysisController {

    private final RestTemplate restTemplate;

    @Value("${nexus.ml.service.url:http://localhost:5000/predict}")
    private String mlServiceUrl;

    public AnalysisController() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * POST /api/analysis
     * Forwards child screening and stroke data to the Flask ML Service.
     */
    @PostMapping
    public ResponseEntity<?> analyzeAssessment(@RequestBody Map<String, Object> requestPayload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestPayload, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(mlServiceUrl, entity, Map.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                    "status", "ERROR",
                    "message", "Unable to communicate with Flask AI/ML screening service at " + mlServiceUrl,
                    "error", e.getMessage()
            ));
        }
    }
}
