package br.com.researchbr.backendresearchbr.Controller;

import br.com.researchbr.backendresearchbr.DTO.ApiResponse;
import br.com.researchbr.backendresearchbr.DTO.UserDto;
import br.com.researchbr.backendresearchbr.Service.AuthenticationFacadeService;
import br.com.researchbr.backendresearchbr.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String SUCCESS = "success";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacadeService authenticationFacadeService;

    @GetMapping(value = "/all")
    public ApiResponse listUser(){
        log.info(String.format("Receiving request for list users %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        return new ApiResponse(HttpStatus.OK, SUCCESS, userService.findAll());
    }

    @PostMapping(value = "/new")
    public ApiResponse create(@RequestBody UserDto user){
        log.info(String.format("Saving a new user %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        return new ApiResponse(HttpStatus.OK, SUCCESS, userService.save(user));
    }

    @GetMapping(value = "/user/{username}")
    public ApiResponse getUser(@PathVariable String username){
        log.info(String.format("Updating user data by username %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        return new ApiResponse(HttpStatus.OK, SUCCESS, userService.findOne(username));
    }

    @GetMapping(value = "/userEmail")
    public ApiResponse getUserEmail(){
        log.info(String.format("Updating user data by email %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        return new ApiResponse(HttpStatus.OK, SUCCESS, userService.findAllEmail());
    }

    @DeleteMapping(value = "/user/delete/{id}")
    public void delete(@PathVariable(value = "id") Long id){
        log.info(String.format("Deleting a user %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        userService.delete(id);
    }



}
