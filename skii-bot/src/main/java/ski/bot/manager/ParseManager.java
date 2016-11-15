package ski.bot.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component
public class ParseManager {

    public String parse(String html, Function<Document, String> f) throws IOException {
        Document doc = Jsoup.parse(html);
        return f.apply(doc);
    }
}
