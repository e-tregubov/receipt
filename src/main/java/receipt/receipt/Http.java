package receipt.receipt;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class Http {

    public Http(Form form) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
        server.createContext("/check", new MyHTTPHandler(form));
        server.setExecutor(null);
        System.out.println("HTTP server ready. Listening port:8888..");
        server.start();
    }

        StringBuilder htmlBuilder = new StringBuilder();
        class MyHTTPHandler implements HttpHandler {
            public MyHTTPHandler(Form form) {
                for (String line : form.get())
                    htmlBuilder.append(line).append("\n");
            }

            @Override
            public void handle(HttpExchange t) throws IOException {
                String htmlResponse = htmlBuilder.toString();
                t.sendResponseHeaders(200, htmlResponse.length());
                OutputStream os = t.getResponseBody();
                os.write(htmlResponse.getBytes());
                os.close();
                System.exit(0);
            }
        }

}
