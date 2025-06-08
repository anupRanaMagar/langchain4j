package dev.langchain4j.model.googleai;

import java.util.Objects;

public class ThinkingConfig {
    
    private final Integer thinkingBudget;
    private final boolean includeThoughts;

    private ThinkingConfig(Builder builder) {
        this.thinkingBudget = builder.thinkingBudget;
        this.includeThoughts = builder.includeThoughts;
    }

    public Integer getThinkingBudget() {
        return thinkingBudget;
    }

    public boolean getIncludeThoughts() {
        return includeThoughts;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer thinkingBudget;
        private boolean includeThoughts;

        private Builder() {
            this.thinkingBudget = 0; 
            this.includeThoughts = false; 
        }

        public Builder includeThoughts(boolean includeThoughts) {
            this.includeThoughts = includeThoughts;
            return this;
        }

        public Builder thinkingBudget(Integer thinkingBudget) {
            this.thinkingBudget = thinkingBudget;
            return this;
        }

        public ThinkingConfig build() {
            if (thinkingBudget != null && thinkingBudget < 0) {
                throw new IllegalArgumentException("Thinking budget must be a non-negative integer");
            }
            return new ThinkingConfig(this);
        }
    }

    @Override
    public String toString() {
        return "ThinkingConfig{" +
               "thinkingBudget=" + thinkingBudget +
               ", includeThoughts=" + includeThoughts +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThinkingConfig that = (ThinkingConfig) o;
        return includeThoughts == that.includeThoughts &&
               Objects.equals(thinkingBudget, that.thinkingBudget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thinkingBudget, includeThoughts);
    }
}