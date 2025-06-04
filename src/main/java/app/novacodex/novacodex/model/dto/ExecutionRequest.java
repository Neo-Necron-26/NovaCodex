package app.novacodex.novacodex.model.dto;

public class ExecutionRequest {
    private String code;
    private String language;

    // Геттеры и сеттеры
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}