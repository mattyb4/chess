package handler;

import com.google.gson.Gson;

public record RegisterRequest(String username, String password, String email) {
}

