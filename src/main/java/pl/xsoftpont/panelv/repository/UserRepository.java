package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findByLoginEquals(String login);


}
