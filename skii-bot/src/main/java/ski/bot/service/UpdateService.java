package ski.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ski.bot.config.Config;
import ski.bot.manager.HttpManager;
import ski.bot.manager.ParseManager;
import ski.bot.model.Updates;
import ski.bot.response.ResponseFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class UpdateService {

    @Autowired
    private ParseManager parseManager;
    @Autowired
    private Config config;
    @Autowired
    private HttpManager httpManager;
    @Autowired
    private ResponseFactory responseFactory;

    private static boolean isWorking;
    private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);

    public void update() throws InterruptedException, IOException, ExecutionException {
        if (isWorking) return;

        isWorking = true;
        int offset = 0;

        while (isWorking) {
            Updates updates = httpManager.getUpdatesWithOffset(offset);
            if ("true".equalsIgnoreCase(updates.ok) && !updates.result.isEmpty()) {
                for (Updates.Result result : updates.result) {
                    Updates.Message message = result.message;
                    int chat_id = message.chat.id;
                    offset = result.update_id + 1;
                    String userText = message.text.toLowerCase();
                    logger.info("user text: {}", userText);
                    responseFactory.getResponse(userText).processResponse(chat_id);
                }
            }
        }
    }

    public void stop() {
        isWorking = false;
    }

}
