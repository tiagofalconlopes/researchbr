package br.com.researchbr.backendresearchbr.Service;

import br.com.researchbr.backendresearchbr.DTO.UserDto;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;

import java.util.List;

public interface UserService {

    UserDto save(UserDto user);
    UserDto edit(Long id, UserDto user);
    List<UserDto> findAll();
    UserEntity findOne(String username);
    List<String> findAllEmail();
    void delete(long id);
}
