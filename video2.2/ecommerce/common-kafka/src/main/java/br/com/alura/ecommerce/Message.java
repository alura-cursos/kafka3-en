package br.com.alura.ecommerce;

public class Message<T> {
    private final CorrelationID correlationID;
    private final T payload;

    public Message(CorrelationID correlationID, T payload) {
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

    public CorrelationID getCorrelationID() {
        return correlationID;
    }
}
