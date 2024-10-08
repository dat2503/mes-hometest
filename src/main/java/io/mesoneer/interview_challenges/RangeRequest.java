package io.mesoneer.interview_challenges;

public class RangeRequest {
    private String range;
    private String value;
    private String classDefinition;

    public RangeRequest(String range, String value, String classDefinition) {
        this.range = range;
        this.value = value;
        this.classDefinition = classDefinition;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getClassDefinition() {
        return classDefinition;
    }

    public void setClassDefinition(String classDefinition) {
        this.classDefinition = classDefinition;
    }
}
