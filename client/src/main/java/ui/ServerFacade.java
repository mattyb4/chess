package ui;

import com.google.gson.Gson;
import model.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.net.*;
import java.io.*;
import java.util.Collection;


public class ServerFacade {
    private final String serverUrl;
    public record GamesListResponse(Collection<GameSumm> games) {}

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData register(UserData request) throws ResponseException {
        return makeRequest("POST", "/user", request, AuthData.class, null);
    }

    public AuthData login(UserData request) throws ResponseException {
        return makeRequest("POST", "/session", request, AuthData.class, null);
    }

    public void logout(String authToken) throws ResponseException {
        makeRequest("DELETE", "/session", null, Object.class, authToken);
    }

    public void clear() throws ResponseException {
        makeRequest("DELETE", "/db", null, Object.class, null);
    }

    public GameData create(String gameName, String authToken) throws ResponseException {
        record CreateRequest(String gameName, String authToken) {}
        var request = new CreateRequest(gameName, authToken);
        return makeRequest("POST", "/game", request, GameData.class, authToken);
    }

    public Collection<GameSumm> listAllGames(String authToken) throws ResponseException {
        //Type collectionType = new TypeToken<Collection<GameSumm>>() {}.getType();
        GamesListResponse response = makeRequest("GET", "/game", null, GamesListResponse.class, authToken);
        return response.games();
    }

    public void join(JoinRequest request, String authToken) throws ResponseException {
        makeRequest("PUT", "/game", request, Object.class, authToken);
    }

    private <T> T makeRequest(String method, String path, Object request, Type responseType, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (authToken != null) {
                http.setRequestProperty("Authorization", authToken);
            }
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);

            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                return new Gson().fromJson(reader, responseType);
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr);
                }
            }

            throw new ResponseException(status, "other failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
