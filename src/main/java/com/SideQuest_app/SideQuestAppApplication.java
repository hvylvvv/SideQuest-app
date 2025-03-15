package com.SideQuest_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SideQuestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SideQuestAppApplication.class, args);
	}

}
