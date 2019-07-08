package br.com.davi.usercontrol.services;

import br.com.davi.usercontrol.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Register one user in database
     *
     * @param user
     * @return User
     */
    User persist(User user);

    /**
     * List all users registered in database
     *
     * @return Optional<List < User>>
     */
    Optional<List<User>> findAll();

    /**
     * Find one user registred in database by id
     * @param id
     * @return
     */
    Optional<User> findById(Long id);

    /**
     * Remove one user
     * @param user
     */
    void remove(User user);

    /**
     * Find one user registred in database by cpf
     * @param cpf
     * @return
     */
    Optional<User> findByCpf(String cpf);

    /**
     * Find one user registred in database by email
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);


}
