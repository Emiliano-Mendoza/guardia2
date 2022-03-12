package com.practicasupervisada.guardia2.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {


    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessage getMessage(final Message message) throws InterruptedException {
        Thread.sleep(1000);
        
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }

    @GetMapping("/msj")
    public String mensajes(Model model) {
    	
    	return "Mensajes";
    }
}