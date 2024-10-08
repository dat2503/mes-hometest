package io.mesoneer.interview_challenges;

public enum EnumType {
    CLOSED,
    OPEN,
    OPEN_CLOSED,
    CLOSED_OPEN,
    LESS_THAN,
    AT_LEAST,
    AT_MOST,
    GREATER_THAN,
    ALL,
    INVALID_TYPE;

    public enum EnumRange {
        INFINITIVE("Infinitive");
        private String value;

        EnumRange(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
