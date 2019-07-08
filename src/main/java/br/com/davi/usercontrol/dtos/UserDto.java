package br.com.davi.usercontrol.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * Data Transfer Object for User
 *
 * @author Davi Lessa
 * @version 1.0
 * @since 20-06-2019
 */
public class UserDto {

    private Optional<Long> id = Optional.empty();
    private String name;
    private String cpf;
    private String email;
    private String telephone;
    private String password;
    private String role;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    @NotEmpty(message = "Name can't be empty.")
    @Length(min = 3, max = 200, message = "Name must contain beyond 3 and 200 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "CPF can't be empty.")
    @CPF(message = "Invalid CPF.")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotEmpty(message = "Email can't be empty.")
    @Length(min = 5, max = 200, message = "Email must contain beyond 3 and 200 characters.")
    @Email(message = "Invalid email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Telephone can't be empty.")
    @Length(min = 7, max = 14, message = "Name must contain beyond 3 and 14 characters.")

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @NotEmpty(message = "Password can't be empty.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Role can't be empty.")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
