package me.kingcjy.was.application.config;

public class TestComponent {

    private String value;

    public TestComponent(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "TestComponent{" +
                "value='" + value + '\'' +
                '}';
    }
}
