package com.ciber.service;

import com.ciber.config.AppConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.StringJoiner;

public class NotificationService {

    private static final String STOCK_TEMPLATE_DEFAULT = "HX934c91515643712dc8f708bf61648bf8";
    private static final String LOTE_INICIO_TEMPLATE_DEFAULT = "HX3eddc56a7cf3160163daa065a022d873";
    private static final String LOTE_FIN_TEMPLATE_DEFAULT = "HX69811128c607414adbe80136ffe5a0cd";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void notifyStockMinimo(String material, int stockActual) {
        sendTemplate(
                AppConfig.get("twilio.template.stock.minimo", STOCK_TEMPLATE_DEFAULT),
                Map.of("1", material, "2", String.valueOf(stockActual))
        );
    }

    public void notifyLoteIniciado(String lote, String tecnico) {
        sendTemplate(
                AppConfig.get("twilio.template.lote.inicio", LOTE_INICIO_TEMPLATE_DEFAULT),
                Map.of("1", lote, "2", tecnico)
        );
    }

    public void notifyLoteFinalizado(String lote, String tecnico) {
        sendTemplate(
                AppConfig.get("twilio.template.lote.fin", LOTE_FIN_TEMPLATE_DEFAULT),
                Map.of("1", lote, "2", tecnico)
        );
    }

    private void sendTemplate(String contentSid, Map<String, String> variables) {
        if (!AppConfig.getBoolean("twilio.enabled", false)) {
            return;
        }

        TwilioSettings settings = TwilioSettings.load();
        if (!settings.isComplete()) {
            System.err.println("Twilio no configurado: revisa TWILIO_ACCOUNT_SID, credenciales y TWILIO_TO_WHATSAPP.");
            return;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.twilio.com/2010-04-01/Accounts/"
                            + settings.accountSid()
                            + "/Messages.json"))
                    .header("Authorization", basicAuth(settings.username(), settings.password()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formBody(settings, contentSid, variables)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                System.err.println("Twilio respondio con estado " + response.statusCode() + ": " + response.body());
            }
        } catch (IOException e) {
            System.err.println("No se pudo enviar notificacion Twilio: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Envio Twilio interrumpido.");
        }
    }

    private String formBody(TwilioSettings settings, String contentSid, Map<String, String> variables) {
        return form(
                Map.of(
                        "From", whatsapp(settings.from()),
                        "To", whatsapp(settings.to()),
                        "ContentSid", contentSid,
                        "ContentVariables", toJson(variables)
                )
        );
    }

    private String form(Map<String, String> values) {
        StringJoiner joiner = new StringJoiner("&");
        values.forEach((key, value) -> joiner.add(encode(key) + "=" + encode(value)));
        return joiner.toString();
    }

    private String toJson(Map<String, String> values) {
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        values.forEach((key, value) -> joiner.add("\"" + escapeJson(key) + "\":\"" + escapeJson(value) + "\""));
        return joiner.toString();
    }

    private String whatsapp(String number) {
        return number.startsWith("whatsapp:") ? number : "whatsapp:" + number;
    }

    private String basicAuth(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private record TwilioSettings(
            String accountSid,
            String apiKeySid,
            String apiKeySecret,
            String authToken,
            String from,
            String to
    ) {

        static TwilioSettings load() {
            return new TwilioSettings(
                    AppConfig.get("twilio.account.sid", ""),
                    AppConfig.get("twilio.api.key.sid", ""),
                    AppConfig.get("twilio.api.key.secret", ""),
                    AppConfig.get("twilio.auth.token", ""),
                    AppConfig.get("twilio.from.whatsapp", "+14155238886"),
                    AppConfig.get("twilio.to.whatsapp", "")
            );
        }

        boolean isComplete() {
            return hasText(accountSid)
                    && hasText(from)
                    && hasText(to)
                    && ((hasText(apiKeySid) && hasText(apiKeySecret)) || hasText(authToken));
        }

        String username() {
            return hasText(apiKeySid) ? apiKeySid : accountSid;
        }

        String password() {
            return hasText(apiKeySecret) ? apiKeySecret : authToken;
        }

        private boolean hasText(String value) {
            return value != null && !value.isBlank();
        }
    }
}
