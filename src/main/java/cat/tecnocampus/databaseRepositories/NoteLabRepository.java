package cat.tecnocampus.databaseRepositories;

import cat.tecnocampus.domain.NoteLab;
import cat.tecnocampus.domain.NoteLabBuilder;
import cat.tecnocampus.domain.UserLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by roure on 20/09/2016.
 */
@Repository
public class NoteLabRepository {
    private JdbcOperations jdbcOperations;

    public NoteLabRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Iterable<NoteLab> findAll() {
        return jdbcOperations.query("Select * from note_lab", new NoteLabRepository.NoteLabMapper());
    }

    public NoteLab findOne(long id) {
        return jdbcOperations.queryForObject("Select * from note_lab where id = ?", new Object[]{id}, new NoteLabRepository.NoteLabMapper());
    }

    public List<NoteLab> findAllFromUser(String username) {
        return jdbcOperations.query("select * from note_lab where owner = ?", new Object[]{username}, new NoteLabMapper());
    }

    public int save(NoteLab noteLab, UserLab owner) {
        return jdbcOperations.update("insert into note_lab (title, content, date_creation, date_edit, owner) values(?, ?, ?, ?, ?)",
                noteLab.getTitle(), noteLab.getContent(), Timestamp.valueOf(noteLab.getDateCreation()), Timestamp.valueOf(noteLab.getDateEdit()), owner.getUsername());
    }

    public int[] saveUserNotes(UserLab owner) {
        return jdbcOperations.batchUpdate("INSERT INTO note_lab (title, content, date_creation, date_edit, owner) values(?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                NoteLab note = owner.getNotesAsList().get(i);
                preparedStatement.setString(1, note.getTitle());
                preparedStatement.setString(2, note.getContent());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(note.getDateCreation()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(note.getDateEdit()));
                preparedStatement.setString(5, owner.getUsername());
            }

            @Override
            public int getBatchSize() {
                return owner.getnotes().size();
            }
        });
    }

    public int updateNote(NoteLab note) {
        return jdbcOperations.update("update note_lab set title = ?, content = ?, date_edit = ? where date_ creation = ?",
                note.getTitle(), note.getContent(), LocalDateTime.now(), note.getDateCreation());
    }

    private final class NoteLabMapper implements RowMapper<NoteLab> {
        @Override
        public NoteLab mapRow(ResultSet resultSet, int i) throws SQLException {
            return new NoteLabBuilder().setTitle(resultSet.getString("title"))
                    .setContent(resultSet.getString("content")).setTime(resultSet.getTimestamp("date_creation").toLocalDateTime())
                    .setTimeEdit(resultSet.getTimestamp("date_edit").toLocalDateTime())
                    .createNoteLab();
        }
    }

}
