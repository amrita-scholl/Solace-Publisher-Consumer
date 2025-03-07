package com.solace.consumer.service.controller;

import com.solace.consumer.service.service.SolacePublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solace")
public class SolaceConsumerController {

    @Autowired
    private SolacePublisherService messageService;

    // API endpoint to send a message to the Solace queue
    @PostMapping("/sendMessage")
    public String sendMessageToQueue(@RequestBody String messageBody) {
        try {
            messageService.sendMessage(messageBody);
            return "Message sent successfully!";
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }
    }


//    // Endpoint to manually start the listener
//    @GetMapping("/startListener")
//    public String startListener() {
//        messageService.startListener();
//        return "Listener started";
//    }
//
//    // Endpoint to manually stop the listener
//    @GetMapping("/stopListener")
//    public String stopListener() {
//        messageService.stopListener();
//        return "Listener stopped";
//    }

}
