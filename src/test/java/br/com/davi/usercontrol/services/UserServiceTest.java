package br.com.davi.usercontrol.services;

import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new User()));
        BDDMockito.given(this.userRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(new User()));
        BDDMockito.given(this.userRepository.findByCpf(Mockito.anyString())).willReturn(Optional.of(new User()));
        BDDMockito.given(this.userRepository.findAll()).willReturn(new ArrayList<User>());
        BDDMockito.given(this.userRepository.save(Mockito.any(User.class))).willReturn(new User());
    }

    @Test
    public void testFindAll(){
        Optional<List<User>> maybeUsers = this.userService.findAll();
        Assert.assertTrue(maybeUsers.isPresent());
    }
    @Test
    public void testFindById(){
        Optional<User> maybeUser = this.userService.findById(1L);
        Assert.assertTrue(maybeUser.isPresent());
    }
    @Test
    public void testFindByCpf(){
        Optional<User> maybeUser = this.userService.findByCpf("490.128.000-72");
        Assert.assertTrue(maybeUser.isPresent());
    }
    @Test
    public void testFindByEmail(){
        Optional<User> maybeUser = this.userService.findByEmail("example@mail.com");
        Assert.assertTrue(maybeUser.isPresent());
    }
    @Test
    public void testPersist(){
        User user = this.userService.persist(new User());
        Assert.assertNotNull(user);
    }
}
