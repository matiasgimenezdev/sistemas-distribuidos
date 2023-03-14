import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private static final int PORT = 5000;
    private static Map<String, Queue<String>> messageQueues = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor en linea");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader inputReader;
        private PrintWriter outputWriter;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                outputWriter.println("Bienvenido al servidor de mensajes. Por favor ingrese su nombre:");
                clientName = inputReader.readLine();
                if (clientName == null) {
                    return;
                }
                System.out.println("Nuevo cliente conectado: " + clientName);
                synchronized (messageQueues) {
                    if (!messageQueues.containsKey(clientName)) {
                        messageQueues.put(clientName, new LinkedList<>());
                    }
                }
                outputWriter.println("Bienvenido, " + clientName
                        + ". Para enviar un mensaje, ingrese el nombre del destinatario seguido por el mensaje.");
                String inputLine;
                while ((inputLine = inputReader.readLine()) != null) {
                    String[] messageParts = inputLine.split(" ", 2);
                    if (messageParts.length == 2) {
                        String recipient = messageParts[0];
                        String message = clientName + ": " + messageParts[1];
                        synchronized (messageQueues) {
                            Queue<String> messageQueue = messageQueues.get(recipient);
                            if (messageQueue != null) {
                                messageQueue.add(message);
                            } else {
                                outputWriter.println("El destinatario " + recipient + " no está conectado.");
                            }
                        }
                    } else {
                        outputWriter.println(
                                "Mensaje mal formado. Por favor ingrese el nombre del destinatario seguido por el mensaje.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al manejar la conexión con el cliente " + clientName + ": " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println(
                            "Error al cerrar la conexión con el cliente " + clientName + ": " + e.getMessage());
                }
                synchronized (messageQueues) {
                    messageQueues.remove(clientName);
                }
                System.out.println("Cliente desconectado: " + clientName);
            }
        }
    }
}
