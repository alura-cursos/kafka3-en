package br.com.alura.ecommerce;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MessageAdapter implements JsonSerializer<Message>, JsonDeserializer<Message> {
    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        var obj = new JsonObject();
        // this is allowing other services to know my services internal implementation
        // you can choose other approachs to serialize the type hint/information
        obj.addProperty("type", message.getPayload().getClass().getName());
        obj.add("payload", context.serialize(message.getPayload()));
        obj.add("correlationId", context.serialize(message.getCorrelationID()));
        return obj;
    }

    @Override
    public Message deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        var obj = element.getAsJsonObject();
        // since we are using classes and not aliases or something else
        // it might be a breach
        var payloadType = obj.get("type").getAsString();
        var correlationId = (CorrelationId) context.deserialize(obj.get("correlationId"), CorrelationId.class);
        try {
            var payload = context.deserialize(obj.get("payload"), Class.forName(payloadType));
            return new Message<>(correlationId, payload);
        } catch (ClassNotFoundException e) {
            // catch and retrow, you might want to deal with
            // this exception
            throw new JsonParseException(e);
        }
    }
}
