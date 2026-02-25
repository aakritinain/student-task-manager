import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class WebServer {

    static ArrayList<String> tasks = new ArrayList<>();
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) throws Exception {

        loadTasksFromFile();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/addTask", new AddTaskHandler());
        server.createContext("/getTasks", new GetTaskHandler());
        server.createContext("/deleteTask", new DeleteTaskHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:8080");
    }

    static void saveTasksToFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
        for (String t : tasks) {
            bw.write(t);
            bw.newLine();
        }
        bw.close();
    }

    static void loadTasksFromFile() throws Exception {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            tasks.add(line);
        }
        br.close();
    }
}

class AddTaskHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        try {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String task = br.readLine();

            WebServer.tasks.add(task);
            WebServer.saveTasksToFile();

            String response = "Task added";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } catch (Exception e) { e.printStackTrace(); }
    }
}

class GetTaskHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        try {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            StringBuilder response = new StringBuilder();
            for (String t : WebServer.tasks) {
                response.append(t).append("\n");
            }

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();

        } catch (Exception e) { e.printStackTrace(); }
    }
}

class DeleteTaskHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        try {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            int index = Integer.parseInt(br.readLine());

            if (index >= 0 && index < WebServer.tasks.size()) {
                WebServer.tasks.remove(index);
                WebServer.saveTasksToFile();
            }

            String response = "Task deleted";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } catch (Exception e) { e.printStackTrace(); }
    }
}