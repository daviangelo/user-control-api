package br.com.davi.usercontrol.security;

import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.enums.RoleEnum;
import br.com.davi.usercontrol.security.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

    private JwtUserFactory(){
    }

    /**
     * Converts an user to JwtUser
     *
     * @param user
     * @return JwtUser
     */
    public static JwtUser create(User user){
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), mapToGrantedAuthorities(user.getRole()));
    }

    /**
     * Converts the user role to the format used by Spring Security
     *
     * @param roleEnum
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(RoleEnum roleEnum){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(roleEnum.toString()));
        return authorities;
    }

}
