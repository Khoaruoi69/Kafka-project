package Statisticservice.demo.service;

import Statisticservice.demo.entity.Statistic;
import Statisticservice.demo.repository.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticRepo statisticRepo;

    @KafkaListener(id="statisticGroup", topics = "statistic")
    public void listen( Statistic statistic) {
        logger.info("received:  " + statistic.getMessage());
        statisticRepo.save(statistic);
    }
}
