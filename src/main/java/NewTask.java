import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


public class NewTask {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try(
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()){
                boolean durable = true;

                channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
                String message = String.join("", argv);
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes("UTF-8"));
                System.out.println(" [X] Sent '" + message + "'");
        }


    }
}