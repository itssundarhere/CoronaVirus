package io.dashboard.coronaVirusDashBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("io.*")
@EnableScheduling
public class CoronaVirusDashBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronaVirusDashBoardApplication.class, args);
	}

}
