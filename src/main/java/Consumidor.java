import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumidor {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Consumidor");

        String NOME_FILA = "filaOla";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();

        Channel canal = connection.createChannel();
        canal.queueDeclare(NOME_FILA,false,false,false,null);

        DeliverCallback callback = (consumerTag,delivery)->{
            String message = new String (delivery.getBody());
            System.out.println(String.format("Recebi a mensagem: %s",message));
        };

        canal.basicConsume(NOME_FILA,true,callback,consumerTag->{});

        System.out.println("Continuarei executando outras atividades enquanto não chega mensagem...");
    }
}
