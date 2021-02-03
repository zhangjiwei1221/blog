package cn.zjw.chatback.dao;

import cn.zjw.chatback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String userName, String password);
}
