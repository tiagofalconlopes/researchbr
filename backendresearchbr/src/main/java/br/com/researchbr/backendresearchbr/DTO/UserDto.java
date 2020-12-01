package br.com.researchbr.backendresearchbr.DTO;

import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleType;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;

public class UserDto {
    private long id;
    private String username;
    private String password;
    private String email;
    private String cpf;
    private RoleEntity role;
    private String roleName;


    public UserDto(){}

    public UserDto(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.role = user.toUserDto().role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
