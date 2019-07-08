package br.com.davi.usercontrol;

import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.enums.RoleEnum;
import br.com.davi.usercontrol.services.UserService;
import br.com.davi.usercontrol.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        User user = new User();
        user.setName("Nome Exemplo");
        user.setEmail("mail@example.com");
        user.setPassword(PasswordUtils.generateBCrypt("123456"));
        user.setTelephone("(21)99856-2514");
        user.setCpf("01218306084");
        user.setRole(RoleEnum.ROLE_ADMIN);

        userService.persist(user);
    }
}
