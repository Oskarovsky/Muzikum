package com.oskarro.muzikum.user;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/id/{userId}")
    public User getUserByUserId(@PathVariable Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @GetMapping(value = "/lastAdded/{quantity}")
    public List<User> getLastAddedUsers(@PathVariable Integer quantity) {
        return userService.getLastAddedUsers(quantity);
    }

    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping(value = "/top/uploader/{periodOfTime}")
    public User getTopUploader(@PathVariable String periodOfTime) {
        return userService.getTopUploader(periodOfTime);
    }

    @GetMapping(value = "/top/uploader/{periodOfTime}/{numberOfUser}")
    public List<User> getTopUploaders(@PathVariable String periodOfTime, @PathVariable int numberOfUser) {
        return userService.getTopUploaders(periodOfTime, numberOfUser);
    }

    @GetMapping(value = "/{userId}/tracks/amount")
    public Integer getNumberOfTracksAddedByUserId(@PathVariable Integer userId) {
        return userService.getNumberOfTracksAddedByUserId(userId);
    }

    @GetMapping(value = "/{username}/tracks/amount")
    public Integer getNumberOfTracksAddedByUsername(@PathVariable String username) {
        return userService.getNumberOfTracksAddedByUsername(username);
    }

    @GetMapping(value = "/{username}/tracks/{periodOfTime}/amount")
    public Integer getNumberOfTracksAddedInGivenPeriodByUsername(@PathVariable String username,
                                                                 @PathVariable String periodOfTime) {
        return userService.getNumberOfTracksAddedInGivenPeriodByUsername(username, periodOfTime);
    }

    @PostMapping(value = "/{userId}/update")
    public User updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

}
