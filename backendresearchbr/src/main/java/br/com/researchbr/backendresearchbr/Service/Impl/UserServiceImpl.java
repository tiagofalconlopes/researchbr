package br.com.researchbr.backendresearchbr.Service.Impl;

import br.com.researchbr.backendresearchbr.DTO.UserDto;
import br.com.researchbr.backendresearchbr.Entity.ProjectEntity;
import br.com.researchbr.backendresearchbr.Entity.RoleEntity;
import br.com.researchbr.backendresearchbr.Entity.UserEntity;
import br.com.researchbr.backendresearchbr.Repository.ProjectRepository;
import br.com.researchbr.backendresearchbr.Repository.RoleRepository;
import br.com.researchbr.backendresearchbr.Repository.UserRepository;
import br.com.researchbr.backendresearchbr.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(userId);
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        if(user == null){
            log.error("Invalid username or password.");
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> grantedAuthorities = getAuthorities(user);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    private Set<GrantedAuthority> getAuthorities(UserEntity user) {
        Set<RoleEntity> roleByUserId = new HashSet<>();
        roleByUserId.add(user.getRole());
        final Set<GrantedAuthority> authorities = roleByUserId.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase())).collect(Collectors.toSet());
        return authorities;
    }

    public List<UserDto> findAll() {
        try {
            List<UserDto> users = new ArrayList<>();
            userRepository.findAll().iterator().forEachRemaining(user -> users.add(user.toUserDto()));
            return users;
        } catch (Exception e) {
            log.error("Error while returning users list: " + e.getMessage());
            throw new RuntimeException();
        }

    }

    @Override
    public UserEntity findOne(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error("Error while returning user : " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public List<String> findAllEmail() {
        try {
            List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
            List<String> emails = new ArrayList<>();
            users.forEach( user -> emails.add(user.getEmail()) );
            return emails;
        } catch (Exception e) {
            log.error("Error while returning user : " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error while deleting user" + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public UserDto save(UserDto userDto) {
        try{
            checkDuplicatedUserInfo(userDto);
            UserEntity user = createUserFromDto(userDto);
            UserDto newDto = new UserDto(userRepository.save(user));
            return newDto;
        } catch (Exception e) {
            log.error("Error while saving user: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public UserDto edit(Long id, UserDto userDto) {
        try{
            userDto.setId(id);
            RoleEntity role = roleRepository.find(userDto.getRoleName());
            userDto.setRole(role);
            UserEntity user = createUserFromDto(userDto);
            UserDto newDto = new UserDto(userRepository.save(user));
            return newDto;
        } catch (Exception e) {
            log.error("Error while editing user: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private void checkDuplicatedUserInfo(UserDto userDto) {
        UserEntity userWithDuplicateUsername = userRepository.findByUsername(userDto.getUsername());
        if(userWithDuplicateUsername != null && userDto.getId() != userWithDuplicateUsername.getId()) {
            log.error(String.format("Duplicated username %", userDto.getUsername()));
            throw new RuntimeException("Duplicated username.");
        }
        UserEntity userWithDuplicateEmail = userRepository.findByEmail(userDto.getEmail());
        if(userWithDuplicateEmail != null && userDto.getId() != userWithDuplicateEmail.getId()) {
            log.error(String.format("Duplicated email %", userDto.getEmail()));
            throw new RuntimeException("Duplicated email.");
        }
        UserEntity userWithDuplicateCpf = userRepository.findByCpf(userDto.getCpf());
        if(userWithDuplicateCpf != null && userDto.getId() != userWithDuplicateCpf.getId()) {
            log.error(String.format("Duplicated CPF %", userDto.getEmail()));
            throw new RuntimeException("Duplicated CPF.");
        }
    }

    private UserEntity createUserFromDto(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCpf(userDto.getCpf());
        RoleEntity role = roleRepository.find(userDto.getRoleName());
        user.setRole(role);

        if(userDto.getProjectsIds() != null) {
            Set<ProjectEntity> projects = new HashSet<>();
            for(int i = 0; i < userDto.getProjectsIds().size(); i++){
                projects.add(projectRepository.findById(userDto.getProjectsIds().get(i)).get());
            }
            user.setProjects(projects);
        }

        return user;
    }

}
