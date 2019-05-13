import pika
import json

def consumer_callback(ch, method, properties, body):
	novos_livros = json.loads(body)
	print('Quantidade de livros:', len(novos_livros))
	for livro in novos_livros:
		print('Nome:', livro['nome'], 'Editora:', livro['editora'])


connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))

nome_fila = 'filaJson'
channel = connection.channel()
channel.queue_declare(queue=nome_fila)
channel.basic_consume(queue=nome_fila,on_message_callback=consumer_callback, auto_ack=True)
channel.start_consuming()

