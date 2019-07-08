package br.com.davi.usercontrol.services.impl;

import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.repositories.UserRepository;
import br.com.davi.usercontrol.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User persist(User user) {
        log.info("Saving an user in database {}", user);
        return this.userRepository.save(user);
    }

    @Override
    public Optional<List<User>> findAll() {
        log.info("Searching all users in database {}");
        return Optional.ofNullable(this.userRepository.findAll());
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Searching one user in database by ID {} ", id);
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        log.info("Searching one user in database by CPF {} ", cpf);
        return this.userRepository.findByCpf(cpf);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Searching one user in database by Email {} ", email);
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void remove(User user) {
        log.info("Searching one user in database by ID {} ", user);
        this.userRepository.delete(user);
    }
}
