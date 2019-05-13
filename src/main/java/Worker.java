import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Worker");

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();

        boolean durable = true;

        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String (delivery.getBody(),"UTF-8");
            System.out.println(String.format(" [X] Received '" +  message + "'"));
            try{
                doWork(message);
            } finally {
                System.out.println(" [X] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };

        channel.basicConsume(QUEUE_NAME,false, deliverCallback, consumerTag->{});

        System.out.println("Continuarei executando outras atividades enquanto não chega mensagem...");
    }

    private static void doWork(String task) {
        for (char ch: task.toCharArray()){
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
