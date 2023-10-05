package br.kenobysky;

import br.kenobysky.models.User;
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
        
        List<User> listaUsers = userService.listAll(pageRequest).getContent();
        if (listaUsers.isEmpty()) {
            
            User defaultUser = new User();
            //
            defaultUser.setCodigo("001");
            defaultUser.setPassword("admin");

            //
            userService.save(defaultUser, true);
        }
        //

        //
        System.out.println("Finished Application Runner!");
    }
    
}
