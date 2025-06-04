package app.novacodex.novacodex.editor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/execute")
public class CodeExecutionController {
    private static final String WORKSPACE_DIR = System.getProperty("user.home") + "/novacodex/workspaces/";
    private static final Map<String, String> FILE_EXTENSIONS = Map.of(
            "java", ".java",
            "python", ".py",
            "javascript", ".js"
    );

    @PostMapping
    public ExecutionResult executeCode(
            @RequestBody ExecutionRequest request,
            Principal principal) throws IOException, InterruptedException {

        try {
            // Создаем рабочую директорию для пользователя
            String userWorkspace = WORKSPACE_DIR + principal.getName() + "/";
            Path workspacePath = Paths.get(userWorkspace);
            Files.createDirectories(workspacePath);

            // Создаем файл с кодом
            String extension = FILE_EXTENSIONS.getOrDefault(request.language(), ".txt");
            Path codeFile = workspacePath.resolve("code" + extension);
            Files.writeString(codeFile, request.code(), StandardCharsets.UTF_8);

            // Выполняем код в зависимости от языка
            ProcessBuilder processBuilder = createProcessBuilder(request.language(), codeFile);
            processBuilder.directory(workspacePath.toFile());
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);

            String output;
            if (!finished) {
                process.destroyForcibly();
                output = "Execution timed out after 10 seconds";
            } else {
                output = new String(process.getInputStream().readAllBytes());
            }

            return ResponseEntity.ok(new ExecutionResult(
                    output,
                    "File saved to: " + codeFile.toString(),
                    codeFile.toString()
            )).getBody();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ExecutionResult("", "Error: " + e.getMessage(), "")).getBody();
        }
    }
    private ProcessBuilder createProcessBuilder(String language, Path codeFile) {
        switch (language.toLowerCase()) {
            case "java":
                return new ProcessBuilder("javac", codeFile.toString(), "&&", "java", "-cp", codeFile.getParent().toString(), "code");
            case "python":
                return new ProcessBuilder("python3", codeFile.toString());
            case "javascript":
                return new ProcessBuilder("node", codeFile.toString());
            case "csharp":
                return new ProcessBuilder("dotnet", "run", "--project", codeFile.toString());
            case "cpp":
                return new ProcessBuilder("g++", codeFile.toString(), "-o", "a.out", "&&", "./a.out");
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
    record ExecutionRequest(String code, String language) {}
    record ExecutionResult(String output, String fileInfo, String filePath) {}
}

