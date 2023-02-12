package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    //connectionList хранит все соединения Server
    private final static List<Connection> connectionList = Collections.synchronizedList(new ArrayList<>());


    public void startServer(int port) {

        try {//создаем серверный сокет
            serverSocket = new ServerSocket(port);
            Main.logger.info("Сервер запущен");
            //  delThread();
            while (true) {
                //ждем соединения и создаем сокет
                socket = serverSocket.accept();
                //создаем новое соединение
                Connection connection = new Connection(socket);
                // добавляем соединение в список соединений
                connectionList.add(connection);
                //запускаем текущее соединение
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {//закрываем соединения
            try {
                serverSocket.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private class Connection extends Thread {//класс будет создавать новое соединение
        private BufferedReader in;
        private PrintWriter out;
        //  private Socket socket;


        public Connection(Socket socket) {
            //    this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            //ждем имя нового пользователя
            String name = null;
            try {
                name = in.readLine();
                messageAllConnection("Подключился новый пользователь: " + name);
                Main.logger.info("Подключился новый пользователь: " + name);

                String str = "";
                while (true) {// цикл общения с пользователем,
                    str = in.readLine();
                    if ("exit".equalsIgnoreCase(str))//если exit-
                        // то выход из цикла общения
                        break;
                    Main.logger.info(name + " : " + str);
                    messageAllConnection(name + " : " + str);
                }
                messageAllConnection("Отключился пользователь: " + name);
                Main.logger.info(name + " отключился");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Main.logger.info("Закрытие потоков пользователя: " + name);
                delConnection(this, name);
            }

        }

        private void messageAllConnection(String message) {
            synchronized (connectionList) {
                connectionList.forEach(connection -> connection.out.println(message));
            }
        }

        private static void delConnection(Connection connection, String name) {//удаление закрытого соединения из списка ссединений
            synchronized (connectionList) {
                Main.logger.info("Удалено соедиения пользователя " + name + " из списка соединений");
                connectionList.remove(connection);
            }
        }

    }
}
