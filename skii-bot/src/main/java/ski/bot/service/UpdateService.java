package ski.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ski.bot.config.Config;
import ski.bot.manager.HttpManager;
import ski.bot.manager.ParseManager;
import ski.bot.model.Updates;
import ski.bot.response.Response;
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

    private static boolean isWork;
    private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);


    public void update() throws InterruptedException, IOException, ExecutionException {
        isWork = true;
        while (isWork) {
            TimeUnit.SECONDS.sleep(config.getUpdateInterval());
            Updates updates = httpManager.getUpdates();

            if (!updates.result.isEmpty()) {
                int lastMessage = updates.result.size() - 1;
                int next_update_id = updates.result.get(lastMessage).update_id + 1;
                int chat_id = updates.result.get(lastMessage).message.chat.id;
                String message = updates.result.get(lastMessage).message.text.toLowerCase();
                logger.info("message: {}", message);

                httpManager.getUpdatesWithOffset(next_update_id);
                responseFactory.getResponse(message).processResponse(chat_id);
            }
        }
    }


    public void stop() {
        isWork = false;
    }

}
