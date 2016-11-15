package ski.bot.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Igor_Tseluiko on 15.11.2016.
 */
@Component
public class ResponseFactory {

    @Autowired
    private Snow snow;
    @Autowired
    private Weather weather;
    @Autowired
    private Unrecognized unrecognized;


    public Response getResponse(String message) {
        switch (message) {
            case "snow": {
                return snow;
            }
            case "weather": {
                return weather;
            }
            default: {
                return unrecognized;
            }
        }
    }
}
