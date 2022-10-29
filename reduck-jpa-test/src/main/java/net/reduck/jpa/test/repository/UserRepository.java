package net.reduck.jpa.test.repository;

import net.reduck.asm.jsr269.Jcp269;
import net.reduck.jpa.specification.JpaRepositoryExtend;
import net.reduck.jpa.test.entity.User;
import net.reduck.jpa.test.vo.UserVO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/6 23:08
 */
public interface UserRepository extends JpaRepositoryExtend<User, Long> {


    @Query(value = "select new net.reduck.jpa.test.vo.UserVO(u.username, u.password) from User as u")
    @Jcp269
    List<UserVO> findAllBy();

}
