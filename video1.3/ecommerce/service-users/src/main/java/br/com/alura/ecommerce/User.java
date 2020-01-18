package br.com.alura.ecommerce;

public class User {

    private final String uuid, email;

    public User(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }
}
