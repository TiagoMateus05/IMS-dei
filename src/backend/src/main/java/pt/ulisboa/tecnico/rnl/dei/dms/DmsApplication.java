package pt.ulisboa.tecnico.rnl.dei.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
    "pt.ulisboa.tecnico.rnl.dei.dms.logs.domain",
    "pt.ulisboa.tecnico.rnl.dei.dms.resources.domain",
    "pt.ulisboa.tecnico.rnl.dei.dms.person.domain"
})
public class DmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

}
