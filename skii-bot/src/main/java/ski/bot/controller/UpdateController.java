package ski.bot.controller;

import ski.bot.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class UpdateController {

    @Autowired
    private UpdateService updateService ;
    
    @RequestMapping("/start")
    public void start() throws InterruptedException, ExecutionException, IOException {
        updateService.update();
    }

    @RequestMapping("/stop")
    public void stop() throws InterruptedException, ExecutionException, IOException {
        updateService.stop();
    }
    
}
