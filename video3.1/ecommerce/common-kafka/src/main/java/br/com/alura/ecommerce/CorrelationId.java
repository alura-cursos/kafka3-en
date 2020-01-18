package br.com.alura.ecommerce;

import java.util.UUID;

public class CorrelationId {

    private final String id;

    public CorrelationId(String base) {
        this.id = base + "-" + UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "CorrelationID{" +
                "id='" + id + '\'' +
                '}';
    }

    public CorrelationId continueWith(String title) {
        return new CorrelationId(this.id + "-" + title);
    }
}
