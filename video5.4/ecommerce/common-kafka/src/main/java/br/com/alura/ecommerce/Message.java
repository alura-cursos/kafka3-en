package br.com.alura.ecommerce;

public class Message<T> {
    private final CorrelationId correlationID;
    private final T payload;

    public Message(CorrelationId correlationID, T payload) {
        this.correlationID = correlationID;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Message{" +
                "correlationID=" + correlationID +
                ", payload=" + payload +
                '}';
    }

    public T getPayload() {
        return payload;
    }

    public CorrelationId getCorrelationID() {
        return correlationID;
    }
}
