package Statisticservice.demo.repository;

import Statisticservice.demo.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepo extends JpaRepository<Statistic, Integer> {

}
