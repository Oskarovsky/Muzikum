package com.oskarro.muzikum.user;

import com.oskarro.muzikum.track.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @GetMapping(value = "/lastAdded/{quantity}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<User> getLastAddedUsers(@PathVariable Integer quantity) {
        return userService.getLastAddedUsers(quantity);
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping(value = "/top/uploader/{periodOfTime}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public User getTopUploader(@PathVariable String periodOfTime) {
        return userService.getTopUploader(periodOfTime);
    }

    @GetMapping(value = "/top/uploader/{periodOfTime}/{numberOfUser}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<User> getTopUploaders(@PathVariable String periodOfTime, @PathVariable int numberOfUser) {
        return userService.getTopUploaders(periodOfTime, numberOfUser);
    }

    @GetMapping(value = "/{userId}/tracks/amount")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Integer getNumberOfTracksAddedByUserId(@PathVariable Integer userId) {
        return userService.getNumberOfTracksAddedByUserId(userId);
    }

    @GetMapping(value = "/{username}/tracks/amount")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Integer getNumberOfTracksAddedByUsername(@PathVariable String username) {
        return userService.getNumberOfTracksAddedByUsername(username);
    }

    @GetMapping(value = "/{username}/tracks/{periodOfTime}/amount")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Integer getNumberOfTracksAddedInGivenPeriodByUsername(@PathVariable String username,
                                                                 @PathVariable String periodOfTime) {
        return userService.getNumberOfTracksAddedInGivenPeriodByUsername(username, periodOfTime);
    }
}
