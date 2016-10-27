package cat.tecnocampus.security;

import cat.tecnocampus.databaseRepositories.NoteLabRepository;
import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.domain.UserLabBuilder;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by roure on 19/09/2016.
 */
@Repository
public class UserSecurityRepository {

    private JdbcOperations jdbcOperations;
    private PasswordEncoder passwordEncoder;

    public UserSecurityRepository(JdbcOperations jdbcOperations, PasswordEncoder passwordEncoder) {
        this.jdbcOperations = jdbcOperations;
        this.passwordEncoder = passwordEncoder;
    }

    public UserSecurity findOne(String username) {
        return jdbcOperations.queryForObject("Select * from users where username = ?", new Object[]{username}, new UserSecurityMapper());
    }

    public void save(String username, String password) {
        jdbcOperations.update("insert into users (username, password) values(?, ?)", username, passwordEncoder.encode(password));
        jdbcOperations.update("insert into user_roles (username, role) values(?, 'ROLE_USER')", username);
    }

    private List<String> findRoles(String username) {
        return jdbcOperations.query("Select * from user_roles where username = ?", new Object[]{username}, new RoleMapper());
    }

    private final class UserSecurityMapper implements RowMapper<UserSecurity> {
        @Override
        public UserSecurity mapRow(ResultSet resultSet, int i) throws SQLException {
            UserSecurity user = new UserSecurity(resultSet.getString("username"), resultSet.getString("password"));
            List<String> roles = findRoles(resultSet.getString("username"));
            user.addRoles(roles);
            return user;
        }
    }

    private final class RoleMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString("role");
        }
    }


}
