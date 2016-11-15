package ski.bot.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ski.bot.config.Config;
import ski.bot.manager.HttpManager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class Weather implements Response {

    @Autowired
    private HttpManager httpManager;
    @Autowired
    private Config config;

    @Override
    public void processResponse(int chat_id) throws InterruptedException, ExecutionException, IOException {
        String weatherJasna = config.getJasnaWeather();
        String weatherZakopane = config.getZakopaneWeather();
        httpManager.sendMessage(chat_id, weatherJasna + "\n " + weatherZakopane);
    }
}
