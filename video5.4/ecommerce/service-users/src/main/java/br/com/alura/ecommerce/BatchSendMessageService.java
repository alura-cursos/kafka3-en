package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BatchSendMessageService {

    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        var myService = new BatchSendMessageService();
        try (var service = new KafkaService<>(BatchSendMessageService.class.getSimpleName(),
                "ECOMMERCE_SEND_MESSAGE_FOR_EVERY_USER",
                myService::parse,
                Map.of())) {
            service.run();
        }
    }

    public BatchSendMessageService() throws SQLException {
        this.users = new Users();
    }

    private final Users users;

    private final KafkaDispatcher<User> dispatcher = new KafkaDispatcher<>();

    private void parse(ConsumerRecord<String, Message<String>> record) throws CommonKafkaException {
        System.out.println("------------------------------------------");
        System.out.println("Processing a new batch for every user");

        var message = record.value();
        var topic = message.getPayload();

        try {
            for (User user : users.all()) {
                try {
                    dispatcher.send(topic, user.getUuid(),
                            message.getCorrelationID().continueWith(BatchSendMessageService.class.getSimpleName()), user);
                } catch (ExecutionException | InterruptedException e) {
                    // i am just skipping the user
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            throw new CommonKafkaException(ex);
        }
    }

}
