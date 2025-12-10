package com.example.coach.api;

public class ResponseAPI<T> {
    private int code;
    private String message;
    private T result;

    /**
     * Retourne le code HTTP
     * @return code HTTP
     */
    public int getCode() {
        return code;
    }

    /**
     * Retourne le message
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retourne le résultat de la requête API
     * @return T
     */
    public T getResult() {
        return result;
    }
}
