package ski.bot.response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface Response {

    void processResponse(int chat_id) throws InterruptedException, ExecutionException, IOException;

}
