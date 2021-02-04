package cn.zjw.chatback.controller;

import cn.zjw.chatback.entity.User;
import cn.zjw.chatback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * GetFriendsController
 *
 * @author zjw
 * @createTime 2021/2/4 08:54
 */
@RestController
public class GetFriendsController {

    private final UserService userService;

    @Autowired
    public GetFriendsController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/getFriends")
    public List<User> getFriends(@RequestParam("id") Long uid) {
        return userService.getFriends(uid);
    }

}
