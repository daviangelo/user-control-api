package br.com.davi.usercontrol.controllers;

import br.com.davi.usercontrol.dtos.UserDto;
import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.enums.RoleEnum;
import br.com.davi.usercontrol.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String BASE_URL = "/api/users/";
    private static final Long ID = 1L;
    private static String NAME = "Example name";
    private static String CPF = "135.667.440-26";
    private static String EMAIL = "example@mail.com";
    private static String TELEPHONE = "(21)97928-3856";
    private static String PASSWORD = "y7utqgpG";
    private static RoleEnum ROLE = RoleEnum.ROLE_ADMIN;


    @Test
    @WithMockUser
    public void testRegisterUser() throws Exception{
        User user = getUserData();
        BDDMockito.given(this.userService.persist(Mockito.any(User.class))).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .content(this.getRequestJsonPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cpf").value(CPF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.telephone").value(TELEPHONE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value(PASSWORD))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(ROLE.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());

    }

    private String getRequestJsonPost() throws JsonProcessingException {
        UserDto userDto = new UserDto();
        userDto.setId(Optional.of(ID));
        userDto.setName(NAME);
        userDto.setCpf(CPF);
        userDto.setEmail(EMAIL);
        userDto.setTelephone(TELEPHONE);
        userDto.setPassword(PASSWORD);
        userDto.setRole(ROLE.name());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(userDto);
    }


    private User getUserData(){
        User user = new User();
        user.setId(ID);
        user.setName(NAME);
        user.setCpf(CPF);
        user.setEmail(EMAIL);
        user.setTelephone(TELEPHONE);
        user.setPassword(PASSWORD);
        user.setRole(ROLE);

        return user;
    }

}
