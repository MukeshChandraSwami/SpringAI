package com.learn.ml_chat_boat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.learn")
public class MlChatBoatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlChatBoatApplication.class, args);
	}

}
