package br.com.davi.usercontrol.controllers;

import br.com.davi.usercontrol.dtos.UserDto;
import br.com.davi.usercontrol.entity.User;
import br.com.davi.usercontrol.enums.RoleEnum;
import br.com.davi.usercontrol.response.Response;
import br.com.davi.usercontrol.services.UserService;
import br.com.davi.usercontrol.utils.PasswordUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * List all users
     *
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity<Response<List<UserDto>>> listUsers() {
        log.info("Searching all users");
        Response<List<UserDto>> response = new Response<>();
        Optional<List<User>> maybeUsersList = this.userService.findAll();
        List<User> usersList = maybeUsersList.get();
        List<UserDto> userDtoList = usersList.stream()
                .map(user -> this.convertUserFromUserDto(user)).collect(Collectors.toList());

        response.setData(userDtoList);
        return ResponseEntity.ok(response);
    }
    /**
     * Get one user by id
     *
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> getUserById(@PathVariable("id") Long id) {
        log.info("Searching user by id", id);
        Response<UserDto> response = new Response<>();
        Optional<User> user = this.userService.findById(id);

        if (!user.isPresent()){
            log.info("Invalid ID {}", id);
            response.getErrors().add("Error searching user. Id entry not found: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        UserDto userDto = this.convertUserFromUserDto(user.get());
        response.setData(userDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Register one user
     *
     * @param userDto
     * @param result
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/register")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<UserDto>> registerUser(@Valid @RequestBody UserDto userDto,
                                                          BindingResult result) throws ParseException {
        log.info("Registring User: {}", userDto);
        Response<UserDto> response = new Response<>();

        validateExistingData(userDto, result);
        User user = convertUserDtoFromUser(userDto, result);

        if (result.hasErrors()) {
            log.error("Error validating user data: {}", result.getAllErrors());

            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        user.setPassword(PasswordUtils.generateBCrypt(user.getPassword()));

        this.userService.persist(user);
        response.setData(this.convertUserFromUserDto(user));
        return ResponseEntity.ok(response);
    }

    /**
     * Update data from an existent user
     *
     * @param id
     * @param userDto
     * @param result
     * @return
     * @throws ParseException
     */
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Response<UserDto>> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto,
                                                        BindingResult result) throws ParseException {
        log.info("Updating user: {}", userDto.toString());
        Response<UserDto> response = new Response<>();

        userDto.setId(Optional.of(id));
        validateUser(userDto, result);

        User user = convertUserDtoFromUser(userDto, result);

        if (result.hasErrors()) {
            log.error("Error validating user data: {}", result.getAllErrors());

            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        user = this.userService.persist(user);
        response.setData(this.convertUserFromUserDto(user));
        return ResponseEntity.ok(response);

    }

    /**
     * Delete an user by id
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> deleteUser(@PathVariable("id") Long id) {
        log.info("Deleting user: {}", id);
        Response<String> response = new Response<>();
        Optional<User> user = this.userService.findById(id);

        if (!user.isPresent()) {
            log.info("Error when delete user. caused by invalid ID {}", id);
            response.getErrors().add("Error deleting user. Id entry not found: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.userService.remove(user.get());
        return ResponseEntity.ok(response);
    }


    private void validateUser(UserDto userDto, BindingResult result) {
        if (!userDto.getId().isPresent()) {
            result.addError(new ObjectError("user", "The user has not been informed.."));
            return;
        }

        log.info("Validating user id {}: ", userDto.getId().get());
        Optional<User> maybeUser = this.userService.findById(userDto.getId().get());
        if (!maybeUser.isPresent()) {
            result.addError(new ObjectError("user", "User not found. non-existent user."));
        }

        //Encrypt the password if is different
        if (!maybeUser.get().getPassword().equals(userDto.getPassword())){
            userDto.setPassword(PasswordUtils.generateBCrypt(userDto.getPassword()));
        }
    }


    private void validateExistingData(UserDto userDto, BindingResult result) {

        this.userService.findByCpf(userDto.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("cpf", "CPF already exists.")));

        this.userService.findByEmail(userDto.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("email", "Email already exists.")));
    }


    private User convertUserDtoFromUser(UserDto userDto, BindingResult result) {
        User user = new User();

        if (userDto.getId().isPresent()) {
            user.setId(userDto.getId().get());
        }
        user.setName(userDto.getName());
        user.setCpf(userDto.getCpf());
        user.setEmail(userDto.getEmail());
        user.setTelephone(userDto.getTelephone());
        user.setPassword(userDto.getPassword());

        if (EnumUtils.isValidEnum(RoleEnum.class, userDto.getRole())) {
            user.setRole(RoleEnum.valueOf(userDto.getRole()));
        } else {
            result.addError(new ObjectError("role", "Invalid Role"));
        }

        return user;
    }

    private UserDto convertUserFromUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(Optional.of(user.getId()));
        userDto.setName(user.getName());
        userDto.setCpf(user.getCpf());
        userDto.setEmail(user.getEmail());
        userDto.setTelephone(user.getTelephone());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole().name());

        return userDto;
    }
}
