package com.weatherapp.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GeminiService {
    // The correct endpoint for Gemini API
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
    private static final String API_KEY = "enter your API key";
    

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public GeminiService() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

   public String getExplanation(String weatherDescription) {
    try {
        // Create the request body according to Gemini API specifications
        ObjectNode requestBody = objectMapper.createObjectNode();

        // Create contents array
        ArrayNode contents = requestBody.putArray("contents");
        ObjectNode content = objectMapper.createObjectNode();

        // Create the parts array
        ArrayNode parts = objectMapper.createArrayNode();
        ObjectNode textPart = objectMapper.createObjectNode();
        textPart.put("text", "I am giving you the weather data of each city. Just analyze it and give me clothing recommendations. Think and provide a detailed explanation. " + weatherDescription);
        parts.add(textPart);

        // Add parts to content
        content.put("role", "user");
        content.set("parts", parts);

        // Add content to contents array
        contents.add(content);

        // Add generation config
        ObjectNode generationConfig = objectMapper.createObjectNode();
        generationConfig.put("temperature", 0.7);
        generationConfig.put("topP", 0.8);
        generationConfig.put("topK", 40);
        requestBody.set("generationConfig", generationConfig);

        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        // Create HTTP request with API key as query parameter
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        // Send request and get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse response
        var responseJson = objectMapper.readTree(response.body());

        // Extract the generated text from the response
        String rawText = "No explanation generated.";
        if (responseJson.has("candidates") &&
            responseJson.get("candidates").size() > 0 &&
            responseJson.get("candidates").get(0).has("content") &&
            responseJson.get("candidates").get(0).get("content").has("parts") &&
            responseJson.get("candidates").get(0).get("content").get("parts").size() > 0) {
            
            rawText = responseJson
                .get("candidates")
                .get(0)
                .get("content")
                .get("parts")
                .get(0)
                .get("text")
                .asText();
        }

        // Strip HTML tags from the response
        return stripHtmlTags(rawText);

    } catch (JsonProcessingException e) {
        // Handle JSON processing exceptions
        return "Error processing JSON: " + e.getMessage();
    } catch (InterruptedException e) {
        // Handle interruption exceptions
        Thread.currentThread().interrupt(); // Restore interrupted status
        return "Request was interrupted: " + e.getMessage();
    } catch (IOException e) {
        // Handle IO exceptions
        return "IO error occurred: " + e.getMessage();
    } catch (Exception e) {
        // Handle any other exceptions
        return "An unexpected error occurred: " + e.getMessage();
    }
}
    private String stripHtmlTags(String html) {
        if (html == null || html.isEmpty()) {
            return html;
        }
        // Remove HTML tags
        String strippedHtml = html.replaceAll("\\<.*?\\>", "");
        // Remove specific unwanted text
        strippedHtml = strippedHtml.replaceAll("(?i)<html>|</html>|<body>|</body>|<br>|width:\\s*\\d+px", "");
        // Remove extra spaces and line breaks
        strippedHtml = strippedHtml.replaceAll("\\s+", " ").trim();
        return strippedHtml;
    }
}
