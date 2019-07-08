package br.com.davi.usercontrol.security.utils.service;

import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.security.JwtUserFactory;
import br.com.davi.usercontrol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(username);

        if (user.isPresent()){
            return JwtUserFactory.create(user.get());
        }

        throw new UsernameNotFoundException("Email not found");
    }
}
