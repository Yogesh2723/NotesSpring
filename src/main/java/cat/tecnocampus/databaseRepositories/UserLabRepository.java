package cat.tecnocampus.databaseRepositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.domain.UserLabBuilder;

/**
 * Created by roure on 19/09/2016.
 */
@Repository
public class UserLabRepository {

    private final JdbcOperations jdbcOperations;
    
    private final NoteLabRepository noteLabRepository;

    public UserLabRepository(JdbcOperations jdbcOperations, NoteLabRepository noteLabRepository) {
        this.jdbcOperations = jdbcOperations;
        this.noteLabRepository = noteLabRepository;
    }

    public List<UserLab> findAll() {
        return jdbcOperations.query("Select * from user_lab", new UserLabMapper());
    }

    public List<UserLab> findAllLazy() {
        return jdbcOperations.query("Select * from user_lab", (rs, i) -> mapUserLab(rs));
    }

    public UserLab findOne(String userName) {
        return jdbcOperations.queryForObject("Select * from user_lab where username = ?", new UserLabMapper(), userName);
    }

    public int save(UserLab userLab) {
        int userUpdate = jdbcOperations.update("insert into user_lab values(?, ?, ?, ?)", userLab.getUsername(), userLab.getName(), userLab.getSecondName(), userLab.getEmail());
        noteLabRepository.saveUserNotes(userLab);

        return userUpdate;
    }

    private final class UserLabMapper implements RowMapper<UserLab> {
        @Override
        public UserLab mapRow(ResultSet resultSet, int i) throws SQLException {
            UserLab userLab = mapUserLab(resultSet);
            
            List<NoteLab> notes = noteLabRepository.findAllFromUser(userLab.getUsername());
            userLab.addNotes(notes);
            return userLab;
        }
    }
    
    private UserLab mapUserLab(ResultSet resultSet) throws SQLException {
    	return new UserLabBuilder()
    				.setUsername(resultSet.getString("username"))
    				.setName(resultSet.getString("name"))
    				.setSecondname(resultSet.getString("second_name"))
    				.setEmail(resultSet.getString("email"))
    				.createUserLab();
    }

}
