package accountservice.controller;

import accountservice.model.AccountDTO;
import accountservice.model.MessageDTO;
import accountservice.model.StatisticDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/new")
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        StatisticDTO stat = new StatisticDTO();
        stat.setCreatedDate(new Date());
        stat.setMessage("Account" + accountDTO.getEmail() + "is created");

        //send notification
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(accountDTO.getEmail());
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Welcome to Jamster.io");
        messageDTO.setContent("Hello Alien");


        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send("notification", messageDTO).thenAccept( recordMetadata  -> {
                // handle success
                System.out.println("SUCCESS" + recordMetadata.getRecordMetadata().partition());
            }).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });
        }
        kafkaTemplate.send("statistic", stat);

        return accountDTO;
    }
}
