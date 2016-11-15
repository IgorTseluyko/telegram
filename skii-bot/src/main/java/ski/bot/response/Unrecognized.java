package ski.bot.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ski.bot.manager.HttpManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Component
public class Unrecognized implements Response {

    @Autowired
    private HttpManager httpManager;

    private static Map<Integer, String> sorry = new HashMap<>();

    static {
        sorry.put(0, "Urecognized command, say what?");
        sorry.put(1, "Ehmm???");
        sorry.put(2, "I am busy, sorry");
        sorry.put(3, "Maybe next time");
        sorry.put(4, "How dare you?!");
    }

    @Override
    public void processResponse(int chat_id) throws InterruptedException, ExecutionException, IOException {
        httpManager.sendMessage(chat_id, sorry.get(new Random().nextInt(5)));
    }

}
