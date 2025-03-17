package backend.shopapi.dao;

import backend.shopapi.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDAO {
    Page<User> findAll(Pageable pageable);
    User findById(long id);
    User save(User user);
    User updateUserAddress(long user_id, long address_id);
    void deleteById(long id);
    List<User> findByNameAndSurname(String clientName, String clientSurname);
}
