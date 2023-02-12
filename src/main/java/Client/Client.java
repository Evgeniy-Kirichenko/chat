package Client;

import Config.Config;
import Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {


    private Scanner sc;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter bw;

    public Client() {
        final Config config = Config.getInstance();
        try {
            sc = new Scanner(System.in);
            socket = new Socket(config.getIp(), config.getPort());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);//делаем autoFlush
            System.out.println("Введите свое имя");
            String name = sc.nextLine();//читаем имя пользователя
            bw.println(name); // отправляем имя пользователя в socket

            ListerServer listerServer = new ListerServer(br);//создаем отдельный поток для приема сообщений с сервера
            listerServer.start();// запускаем поток

            String msg = "";
            while (!"exit".equalsIgnoreCase(msg)) {
                msg = sc.nextLine();//пишем свое сообщение
                bw.println(msg);//отправляем сообщение на сервер
            }

            listerServer.interrupt();//запрос на прерывание потока сообщений с сервера

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sc.close();
                socket.close();
                bw.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static class ListerServer extends Thread {
        private final BufferedReader br;

        ListerServer(BufferedReader br) {
            this.br = br;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {//если нет запроса на прерывание потока, выводим сообщение
                //в консоль
                try {
                    System.out.println(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
