package cn.zjw.study.dao;

import cn.zjw.study.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
