package cn.zjw.jwtback.dao;

import cn.zjw.jwtback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
