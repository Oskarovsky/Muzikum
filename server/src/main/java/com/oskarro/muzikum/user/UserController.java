package com.oskarro.muzikum.user;

import com.oskarro.muzikum.config.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/id/{userId}")
    public User getUserByUserId(@PathVariable final Integer userId) {
        return userService.getUserById(userId);
    }

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable final String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping(value = "/lastAdded/{quantity}")
    public List<User> getLastAddedUsers(@PathVariable final Integer quantity) {
        return userService.getLastAddedUsers(quantity);
    }

    @GetMapping(value = "/top/uploader/{periodOfTime}")
    public User getTopUploader(@PathVariable final String periodOfTime) {
        return userService.getTopUploader(periodOfTime);
    }

    @GetMapping(value = "/top/uploader/{periodOfTime}/{numberOfUser}")
    public List<User> getTopUploaders(@PathVariable final String periodOfTime,
                                      @PathVariable final int numberOfUser) {
        return userService.getTopUploaders(periodOfTime, numberOfUser);
    }

    @GetMapping(value = "/id/{userId}/tracks/count")
    public Integer getNumberOfTracksAddedByUserId(@PathVariable final Integer userId) {
        return userService.getNumberOfTracksAddedByUserId(userId);
    }

    @GetMapping(value = "/{username}/tracks/count")
    public Integer getNumberOfTracksAddedByUsername(@PathVariable final String username) {
        return userService.getNumberOfTracksAddedByUsername(username);
    }

    @GetMapping(value = "/{username}/tracks/{periodOfTime}/count")
    public Integer getNumberOfTracksAddedInGivenPeriodByUsername(@PathVariable final String username,
                                                                 @PathVariable final String periodOfTime) {
        return userService.getNumberOfTracksAddedInGivenPeriodByUsername(username, periodOfTime);
    }

    @PatchMapping(value = "/{userId}")
    public User updateUser(@PathVariable final String userId,
                           @RequestBody final UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserPrincipal user) {
        return ResponseEntity.ok(user.getUsername());
    }

}
