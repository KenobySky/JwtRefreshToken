package br.kenobysky;

import br.kenobysky.models.Client;
import br.kenobysky.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author Shadows
 */
@Configuration
public class DefaultUserConfiguration implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
   
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application Runner running....");

        //
        PageRequest pageRequest = PageRequest.of(0, 5);
        
        List<Client> listaUsers = userService.listAll(pageRequest).getContent();
        if (listaUsers.isEmpty()) {
            
            Client defautlClient = new Client();
            //
            
            //
            defautlClient.setCode("001");
            defautlClient.setPassword("admin");

            //
            userService.save(defautlClient, true);
        }
        //

        //
        System.out.println("Finished Application Runner!");
    }
    
}
