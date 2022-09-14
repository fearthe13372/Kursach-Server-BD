package SERV;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class Server implements AutoCloseable {
	private final ServerSocket server;
	private static final String USERNAME =  "root";
	private static final String PASSWORD =  "12345";
	private static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
	// JDBC variables for opening and managing connection
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	public Server(String host, int port, int backlogConnectionQueueLength) throws UnknownHostException, IOException {
		server = new ServerSocket(port, backlogConnectionQueueLength, InetAddress.getByName(host));
		System.out.println(Thread.currentThread() + " Сервер создан");
	}
	
	public void start() {
		System.out.println(Thread.currentThread() + " Сервер готов: " + server);
		while (true) {
			acceptAndHandleClient(server);
		}
	}

	private void acceptAndHandleClient(ServerSocket server) {
		System.out.println(Thread.currentThread() + " Ожидаю подключений...");
		try (Socket clientSocket = server.accept()) {
			handleNewClient(clientSocket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

  private void handleNewClient(Socket clientSocket) throws IOException {
	  //System.out.println(Thread.currentThread() + " Received Connection from " + clientSocket);
		BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintStream os = new PrintStream(clientSocket.getOutputStream());
		// echo that data back to the client, except for QUIT.
		String line = null;
	  LinkedList<String> list = null;
	  //list=selectActionMovie(list);
		while (true) {
			//(line = is.readLine()) != null
			line = is.readLine();
			System.out.println(Thread.currentThread() + " Сервер получил => " + line);
			switch (line) {
				case "Action":{
					list=selectActionMovie(list);
					for (String item:list  ) {

						os.println(item);
						os.flush();

					}
					os.println("END");
					System.out.println("END");
					os.flush();
					line="";
					break;}
				case "Detective":{
					list=selectDetective(list);
					for (String item:list  ) {

						os.println(item);
						os.flush();

					}
					os.println("END");
					System.out.println("END");
					os.flush();
					line="";
					break;}
				case "Comedy":{
					list=selectComedy(list);
					for (String item:list  ) {

						os.println(item);
						os.flush();

					}
					os.println("END");
					System.out.println("END");
					os.flush();
					line="";
					break;}
				case "Fairy":{
					list=selectFairytale(list);
					for (String item:list  ) {

						os.println(item);
						os.flush();

					}
					os.println("END");
					System.out.println("END");
					os.flush();
					line="";
					break;}
				case "Film":{
					list=selectfilm(list);
					for (String item:list  ) {

						os.println(item);
						os.flush();

					}
					os.println("END");
					System.out.println("END");
					os.flush();
					line="";
					break;}
				case "Historical":{
					list=selectHistorical(list);
					for (String item:list  ) {

						os.println(item);
						os.flush();
						line="";
					}
					os.println("END");
					System.out.println("END");
					os.flush();
					line="";
					break;}
				case "Search":{
					line = is.readLine();//name:BlaBlaBLa

					list=SearchName(list,line.substring(6));
					for (String item:list  ) {

						os.println(item);
						os.flush();

					}
					os.println("END");
					System.out.println("END");
					os.flush();
					line="";
					break;}
				case "QUIT":
						break;


				default:
					System.out.println("Упс,что то пошло не так !");
			}

			if (line.equalsIgnoreCase("QUIT"))
				break;

		}
		System.out.println(Thread.currentThread() + " Сервер закрывает подключение после отправки => Ok");
		os.println("Ok");
		os.flush();
		is.close();
		os.close();
  }
	
	public void close() throws IOException {
		server.close();
	}
	public static LinkedList<String> selectActionMovie (LinkedList<String> list){
			list= new LinkedList<String>();
			try{
				String query = "select film.* from film  inner join action_movie on(film.idFilm=action_movie.idfilm)";

				try {
					// opening database connection to MySQL server
					con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

					// getting Statement object to execute query
					stmt = con.createStatement();

					// executing SELECT query
					rs = stmt.executeQuery(query);

					while (rs.next()) {
						String namee = rs.getString(2);
						String descriptiom = rs.getString(3);
						String time = rs.getString(4);
						String year = rs.getString(5);
						String country = rs.getString(6);

						URL htm=rs.getURL(7);
						String url = htm.toString();
						int mark = rs.getInt(8);
						list.add(namee+"!"+descriptiom+"!"+time+"!"+year+"!"+country+"!"+url+"!");

					}

				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				} finally {
					//close connection ,stmt and resultset here
					try { con.close(); } catch(SQLException se) { /*can't do anything */ }
					try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
					try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
				}
			}
			catch (Exception e){e.printStackTrace();}
			return list;
	}
	public static LinkedList<String> selectDetective (LinkedList<String> list){
		list= new LinkedList<String>();
		try{
			String query = "select film.* from film  inner join detective on(film.idFilm=detective.idfilm)";

			try {
				// opening database connection to MySQL server
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				// getting Statement object to execute query
				stmt = con.createStatement();

				// executing SELECT query
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String namee = rs.getString(2);
					String descriptiom = rs.getString(3);
					String time = rs.getString(4);
					String year = rs.getString(5);
					String country = rs.getString(6);

					URL htm=rs.getURL(7);
					String url = htm.toString();
					int mark = rs.getInt(8);
					list.add(namee+"!"+descriptiom+"!"+time+"!"+year+"!"+country+"!"+url+"!");

				}

			} catch (SQLException sqlEx) {
				sqlEx.printStackTrace();
			} finally {
				//close connection ,stmt and resultset here
				try { con.close(); } catch(SQLException se) { /*can't do anything */ }
				try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
				try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
			}
		}
		catch (Exception e){e.printStackTrace();}
		return list;
	}
	public static LinkedList<String> selectComedy (LinkedList<String> list){
		list= new LinkedList<String>();
		try{
			String query = "select film.* from film  inner join comedy on(film.idFilm=comedy.idfilm)";

			try {
				// opening database connection to MySQL server
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				// getting Statement object to execute query
				stmt = con.createStatement();

				// executing SELECT query
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String namee = rs.getString(2);
					String descriptiom = rs.getString(3);
					String time = rs.getString(4);
					String year = rs.getString(5);
					String country = rs.getString(6);

					URL htm=rs.getURL(7);
					String url = htm.toString();
					int mark = rs.getInt(8);
					list.add(namee+"!"+descriptiom+"!"+time+"!"+year+"!"+country+"!"+url+"!");

				}

			} catch (SQLException sqlEx) {
				sqlEx.printStackTrace();
			} finally {
				//close connection ,stmt and resultset here
				try { con.close(); } catch(SQLException se) { /*can't do anything */ }
				try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
				try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
			}
		}
		catch (Exception e){e.printStackTrace();}
		return list;
	}
	public static LinkedList<String> selectFairytale (LinkedList<String> list){
		list= new LinkedList<String>();
		try{
			String query = "select film.* from film  inner join fairytale on(film.idFilm=fairytale.idfilm)";

			try {
				// opening database connection to MySQL server
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				// getting Statement object to execute query
				stmt = con.createStatement();

				// executing SELECT query
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String namee = rs.getString(2);
					String descriptiom = rs.getString(3);
					String time = rs.getString(4);
					String year = rs.getString(5);
					String country = rs.getString(6);

					URL htm=rs.getURL(7);
					String url = htm.toString();
					int mark = rs.getInt(8);
					list.add(namee+"!"+descriptiom+"!"+time+"!"+year+"!"+country+"!"+url+"!");

				}

			} catch (SQLException sqlEx) {
				sqlEx.printStackTrace();
			} finally {
				//close connection ,stmt and resultset here
				try { con.close(); } catch(SQLException se) { /*can't do anything */ }
				try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
				try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
			}
		}
		catch (Exception e){e.printStackTrace();}
		return list;
	}
	public static LinkedList<String> selectfilm (LinkedList<String> list){
		list= new LinkedList<String>();
		try{
			String query = "select film.* from film";

			try {
				// opening database connection to MySQL server
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				// getting Statement object to execute query
				stmt = con.createStatement();

				// executing SELECT query
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String namee = rs.getString(2);
					String descriptiom = rs.getString(3);
					String time = rs.getString(4);
					String year = rs.getString(5);
					String country = rs.getString(6);

					URL htm=rs.getURL(7);
					String url = htm.toString();
					int mark = rs.getInt(8);
					list.add(namee+"!"+descriptiom+"!"+time+"!"+year+"!"+country+"!"+url+"!");

				}

			} catch (SQLException sqlEx) {
				sqlEx.printStackTrace();
			} finally {
				//close connection ,stmt and resultset here
				try { con.close(); } catch(SQLException se) { /*can't do anything */ }
				try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
				try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
			}
		}
		catch (Exception e){e.printStackTrace();}
		return list;
	}
	public static LinkedList<String> selectHistorical (LinkedList<String> list){
		list= new LinkedList<String>();
		try{
			String query = "select film.* from film  inner join historical_film on(film.idFilm=historical_film.idfilm)";

			try {
				// opening database connection to MySQL server
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				// getting Statement object to execute query
				stmt = con.createStatement();

				// executing SELECT query
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String namee = rs.getString(2);
					String descriptiom = rs.getString(3);
					String time = rs.getString(4);
					String year = rs.getString(5);
					String country = rs.getString(6);

					URL htm=rs.getURL(7);
					String url = htm.toString();
					int mark = rs.getInt(8);
					list.add(namee+"!"+descriptiom+"!"+time+"!"+year+"!"+country+"!"+url+"!");

				}

			} catch (SQLException sqlEx) {
				sqlEx.printStackTrace();
			} finally {
				//close connection ,stmt and resultset here
				try { con.close(); } catch(SQLException se) { /*can't do anything */ }
				try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
				try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
			}
		}
		catch (Exception e){e.printStackTrace();}
		return list;
	}
	public static LinkedList<String> SearchName (LinkedList<String> list,String nameSrch){
		list= new LinkedList<String>();
		try{
			String query = String.format("select film.* from film  where film.namee='%s'",nameSrch);
			System.out.println(query);
			try {
				// opening database connection to MySQL server
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				// getting Statement object to execute query
				stmt = con.createStatement();

				// executing SELECT query
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String namee = rs.getString(2);
					String descriptiom = rs.getString(3);
					String time = rs.getString(4);
					String year = rs.getString(5);
					String country = rs.getString(6);

					URL htm=rs.getURL(7);
					String url = htm.toString();
					int mark = rs.getInt(8);
					list.add(namee+"!"+descriptiom+"!"+time+"!"+year+"!"+country+"!"+url+"!");

				}

			} catch (SQLException sqlEx) {
				sqlEx.printStackTrace();
			} finally {
				//close connection ,stmt and resultset here
				try { con.close(); } catch(SQLException se) { /*can't do anything */ }
				try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
				try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
			}
		}
		catch (Exception e){e.printStackTrace();}
		return list;
	}
	public static void main(String[] args) {
		try (Server server = new Server("localhost", 8080, 50)) {

			server.start();

		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
