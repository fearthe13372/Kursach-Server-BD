package SERV;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Client implements AutoCloseable {
	private final Socket client;
	private final DataOutputStream os;
	private final BufferedReader is;

	public Client(String host, int port) throws UnknownHostException, IOException {
		client = new Socket(host, port);
		os = new DataOutputStream(client.getOutputStream());
		is = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}

	public void sendReceive(String message) {
		try {
			//System.out.println("Thread = " + Thread.currentThread());
			System.out.println("Отправляю на сервер: " + message);
			os.writeBytes(message + "\n");
			os.flush();
			// keep on reading from/to the socket till we receive the "Ok" from Server,
			// once we received that we break.
			String line;
			LinkedList<String> list = new LinkedList<>();

			while ((line = is.readLine()) != null){
				if(!line.equals("END")){
				list.add(line);
				System.out.println(line);
				System.out.println(line.isEmpty());}
				else{
					System.out.println("END");
					break;};
			}


		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void close() throws IOException {
		sendReceive("QUIT");
		is.close();
		os.close();
	}

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		int port = 8080;
		String host = "localhost";
		int totalClients = 4;
		Client client = new Client(host, port);
		//System.out.println("--------Action--------");
		client.sendReceive("Action");
		//System.out.println("--------Comedy--------");
		client.sendReceive("Comedy");
		//System.out.println("--------Detective--------");
		client.sendReceive("Detective");
		//System.out.println("--------Fairytale--------");
		client.sendReceive("Detective");
		//System.out.println("--------Film--------");
		client.sendReceive("Film");
		//System.out.println("--------Historical--------");
		client.sendReceive("Historical");
		client.close();

	}
}
