import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import  org.json.*;

public class Produtor {
    public static void main(String[] args) throws Exception {
        String NOME_FILA = "filaJson";

        List<Livro> livros = new ArrayList<Livro>();

        for (int i=0; i<11; i++){
            Livro livro = new Livro("Livro"+i, "Letras");
            livros.add(livro);
        }

        JSONArray livrosJSON = new JSONArray(livros);

        String mensagem = livrosJSON.toString();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        try(
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()){

                channel.queueDeclare(NOME_FILA,false,false,false,null);

                channel.basicPublish("",NOME_FILA, null, mensagem.getBytes());
                System.out.println(channel.getConnection().getAddress());
                System.out.println("Enviei mensagem: " + mensagem);
        }


    }
}
