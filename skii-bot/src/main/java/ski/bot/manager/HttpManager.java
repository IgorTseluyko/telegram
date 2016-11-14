package ski.bot.manager;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class HttpManager {

    private static CloseableHttpAsyncClient httpcAsyncClient = HttpAsyncClients.createDefault();
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    @PostConstruct
    private void init(){
        httpcAsyncClient.start();
    }

    @PreDestroy
    private void destroy() throws IOException {
        httpcAsyncClient.close();
    }



}
