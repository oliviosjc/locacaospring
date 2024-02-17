package org.oliviox.locacaospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LocacaospringApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(LocacaospringApplication.class, args);
	}

}
