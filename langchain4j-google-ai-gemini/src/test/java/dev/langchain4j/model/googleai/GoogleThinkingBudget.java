package dev.langchain4j.model.googleai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class GoogleThinkingBudget {

    private static final String GOOGLE_AI_GEMINI_API_KEY = System.getenv("GOOGLE_AI_GEMINI_API_KEY");

    @Test
    void simple_test() {
        // given
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName("gemini-2.5-flash-preview-05-20")
                .temperature(2.0)
                .topP(0.5)
                .topK(10)
                .build();

        // when
        String response = gemini.chat("How much is 3+4? Reply with just the answer");

        System.out.println("Response: " + response);
    }

    @Test
    void thinking_budget_test() {
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName("gemini-2.5-flash-preview-05-20")
                .temperature(2.0)
                .topP(0.5)
                .topK(10)
                .maxOutputTokens(50)
                .thinkingConfig(ThinkingConfig.builder()
                        .thinkingBudget(10)
                        .includeThoughts(true)
                        .build())
                .build();

        String response = gemini.chat("what is Spring Framework? give one sentence answer");

        System.out.println("Response: " + response);
    }

    @Test
    void test_thinkingConfig_with_high_budget_and_thoughts_included() {
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName("gemini-2.5-flash-preview-05-20")
                .temperature(1.0)
                .thinkingConfig(ThinkingConfig.builder()
                        .thinkingBudget(20)
                        .includeThoughts(true)
                        .build())
                .build();

        String response = gemini.chat("What are the benefits of using Spring Boot?");
        System.out.println("Response (with thoughts): " + response);

        assertThat(response.toLowerCase()).doesNotContain("error");
    }

    @Test
    void test_thinkingConfig_without_thoughts() {
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName("gemini-2.5-flash-preview-05-20")
                .temperature(1.0)
                .thinkingConfig(ThinkingConfig.builder()
                        .thinkingBudget(10)
                        .includeThoughts(false)
                        .build())
                .build();

        String response = gemini.chat("What is Java?");

        assertThat(response.length()).isLessThan(200);
    }

    @Test
    void test_thinkingConfig_with_zero_budget() {
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName("gemini-2.5-flash-preview-05-20")
                .thinkingConfig(ThinkingConfig.builder()
                        .thinkingBudget(0)
                        .includeThoughts(true)
                        .build())
                .build();

        String response = gemini.chat("Explain polymorphism in one sentence.");
        System.out.println("Response (zero budget): " + response);

        assertThat(response).isNotBlank();
    }

    @Test
    void test_thinkingConfig_with_very_high_budget() {
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(GOOGLE_AI_GEMINI_API_KEY)
                .modelName("gemini-2.5-flash-preview-05-20")
                .thinkingConfig(ThinkingConfig.builder()
                        .thinkingBudget(9999)
                        .includeThoughts(true)
                        .build())
                .build();

        String response = gemini.chat("What is deep learning?");
        System.out.println("Response (high budget): " + response);

        assertThat(response).containsIgnoringCase("neural network");
    }
}
