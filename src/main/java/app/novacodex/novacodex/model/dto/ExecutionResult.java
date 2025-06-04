package app.novacodex.novacodex.model.dto;

public class ExecutionResult {
    private String output;
    private String fileInfo;
    private String filePath;

    // Конструктор, геттеры и сеттеры
    public ExecutionResult(String output, String fileInfo, String filePath) {
        this.output = output;
        this.fileInfo = fileInfo;
        this.filePath = filePath;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getOutput() {
        return output;
    }
}