package backend.shopapi.repository;

import backend.shopapi.dao.UserDAO;
import backend.shopapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        String sql = "SELECT * FROM users LIMIT ? OFFSET ?";
        List<User> users = jdbcTemplate.query(sql,
                new Object[]{pageable.getPageSize(), pageable.getOffset()},
                new UserRowMapper()
                );
        String sqlCount = "SELECT COUNT(*) FROM users";
        int count = jdbcTemplate.queryForObject(sqlCount, Integer.class);
        return new PageImpl<>(users, pageable, count);
    }

    @Override
    public User findById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        if(users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public User updateUserAddress(long user_id, long address_id) {
        String sql = "UPDATE Users SET address_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, address_id , user_id);
        return findById(user_id);
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (login, password, email, client_name, client_surname, gender, birthday, registration_date, address_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        user.setRegistration_date(localDateTime);

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getClient_name());
            ps.setString(5, user.getClient_surname());
            ps.setString(6, user.getGender());
            ps.setDate(7, user.getBirthday() != null ? new java.sql.Date(user.getBirthday().getTime()) : null);
            ps.setTimestamp(8, timestamp);
            ps.setObject(9, user.getAddress_id() == 0 ? null : user.getAddress_id());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null) {
            user.setId((Long) keys.get("id"));
        }

        return user;
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<User> findByNameAndSurname(String clientName, String clientSurname) {
        String sql = "SELECT * FROM users WHERE client_name = ? AND client_surname = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), clientName, clientSurname);
        if(users.isEmpty()) {
            return null;
        }
        return users;
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setClient_name(rs.getString("client_name"));
            user.setClient_surname(rs.getString("client_surname"));
            user.setGender(rs.getString("gender"));
            user.setBirthday(rs.getDate("birthday"));

            Timestamp registrationDateTimestamp = rs.getTimestamp("registration_date");
            if (registrationDateTimestamp != null) {
                user.setRegistration_date(registrationDateTimestamp.toLocalDateTime());
            }

            user.setAddress_id(rs.getLong("address_id"));
            return user;
        }
    }
}
