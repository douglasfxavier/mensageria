import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



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

                channel.queueDeclare(NOME_FILA,false,false,false,null);
                String mensagem = "Olá, mundo!";

                channel.basicPublish("",NOME_FILA, null, mensagem.getBytes());
                System.out.println(channel.getConnection().getAddress());
                System.out.println("Enviei mensagem: " + mensagem);
        }


    }
}
