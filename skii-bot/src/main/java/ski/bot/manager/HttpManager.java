package ski.bot.manager;

import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ski.bot.config.Config;
import ski.bot.model.Updates;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class HttpManager {

    @Autowired
    private Config config;

    private static int poolSize;
    private static int maxConn;
    private static CloseableHttpAsyncClient httpcAsyncClient;
    private static CloseableHttpClient httpclient;
    private String getUpdatesUri;
    private String sendMessageUri;
    private String timeout;
    private static final Logger logger = LoggerFactory.getLogger(HttpManager.class);

    @Value("${poolSize}")
    public void setPoolSize(int val) {
        poolSize = val;
    }

    @Value("${maxConn}")
    public void setMaxConn(int val) {
        maxConn = val;
    }

    @PostConstruct
    private void init() {
        getUpdatesUri = config.getTelegramUrl() + config.getToken() + "/getupdates";
        sendMessageUri = config.getTelegramUrl() + config.getToken() + "/sendmessage";
        timeout = String.valueOf(config.getTimeOut());

        httpcAsyncClient = HttpAsyncClients
                .custom()
                .setMaxConnTotal(poolSize)
                .setMaxConnPerRoute(maxConn)
                .build();

        httpclient = HttpClients.createDefault();
        httpcAsyncClient.start();
    }

    @PreDestroy
    private void destroy() throws IOException {
        httpcAsyncClient.close();
        httpclient.close();
    }

    public Updates getUpdates() throws IOException, ExecutionException, InterruptedException {
        HttpPost post = new HttpPost(getUpdatesUri);
        String raw = sendAsync(post);
        Updates updates = new GsonBuilder().create().fromJson(raw, Updates.class);
        logger.info("updates received: {}", updates);
        return updates;
    }

    public void getUpdatesWithOffset(int offset) throws IOException, InterruptedException, ExecutionException {
        HttpPost post = new HttpPost(getUpdatesUri);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("offset", String.valueOf(offset)));
        nameValuePairs.add(new BasicNameValuePair("timeout", timeout));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        sendAsync(post);
    }

    public void sendMessage(int chat_id, String text) throws IOException, InterruptedException, ExecutionException {
        HttpPost post = new HttpPost(sendMessageUri);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("chat_id", String.valueOf(chat_id)));
        nameValuePairs.add(new BasicNameValuePair("text", text));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        String response = sendAsync(post);
        logger.info("response: {}", response);

    }

    private String sendAsync(HttpUriRequest request) throws ExecutionException, InterruptedException, IOException {
        Future<HttpResponse> future = httpcAsyncClient.execute(request, null);
        HttpResponse response = future.get();
        return EntityUtils.toString(response.getEntity());
    }

    public String send(String uri) throws InterruptedException, ExecutionException, IOException {
        HttpGet get = new HttpGet(uri);
        return sendAsync(get);
    }

}
