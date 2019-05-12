import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


public class Produtor {
    public static void main(String[] args) throws Exception {
        String NOME_FILA = "filaOla";

        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("localhost");
//        connectionFactory.setPort(5672);
//        connectionFactory.setHost("192.168.0.103");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");

        try(
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()){
                boolean duravel = true;

                channel.queueDeclare(NOME_FILA,duravel,false,false,null);
                String mensagem = "Olá, mundo!";
                channel.basicPublish("",NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes());
                System.out.println(channel.getConnection().getAddress());
                System.out.println("Enviei mensagem: " + mensagem);
        }


    }
}
