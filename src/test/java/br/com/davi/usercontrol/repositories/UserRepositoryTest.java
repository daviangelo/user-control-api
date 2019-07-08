package br.com.davi.usercontrol.repositories;

import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.enums.RoleEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static final Long ID = 9999999L;

    private static User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(ID);
        user.setName("Example name");
        user.setCpf("99999999999");
        user.setEmail("example@mail.com");
        user.setPassword("examplepassword");
        user.setTelephone("(99)9999-9999");
        user.setRole(RoleEnum.ROLE_ADMIN);
        this.userRepository.save(user);
    }

    @After
    public final void tearDown() {
        this.userRepository.delete(user);
    }

    @Test
    public void testFindById() {
        User user = this.userRepository.findById(ID).get();

        Assert.assertEquals(ID, user.getId());
    }

}
