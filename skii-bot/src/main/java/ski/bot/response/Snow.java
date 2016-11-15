package ski.bot.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ski.bot.config.Config;
import ski.bot.manager.HttpManager;
import ski.bot.manager.ParseManager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class Snow implements Response {

    @Autowired
    private HttpManager httpManager;
    @Autowired
    private Config config;
    @Autowired
    private ParseManager parseManager;

    @Override
    public void processResponse(int chat_id) throws InterruptedException, ExecutionException, IOException {
        String snowJasnaHtml = httpManager.send(config.getJasnaSnow());
        String snowZakopaneHtml = httpManager.send(config.getZakopaneSnow());

        String parsedJasna = parseManager.parse(snowJasnaHtml, doc -> doc.select("meta[name=description]").attr("content"));
        String parsedZakopane = parseManager.parse(snowZakopaneHtml, doc -> doc.select("meta[name=description]").attr("content"));

        httpManager.sendMessage(chat_id, parsedJasna + "\n" + parsedZakopane);
    }
}
