package app.novacodex.novacodex.service.auth;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class KeyVerifier {
    public static boolean verifyKey(String key, String universityCode) {
        try {
            URL url = new URL("http://localhost:3001/api/verify-key");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"key\": \"%s\", \"universityCode\": \"%s\"}",
                    key, universityCode
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString().contains("\"valid\":true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}