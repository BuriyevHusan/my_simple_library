package my.simple.library.repository;

import my.simple.library.model.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class BookRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Book> findAll() {
        String sql = "select * from book";
        try {
            return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
        }catch (Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    public Optional<Book> findById(String id) {
        String sql = "select * from book b join comment c on c.book_id = b.id where b.id = :id";
        try {
            Book book = namedParameterJdbcTemplate.queryForObject(sql, Map.of("id", id), Book.class);
            return Optional.ofNullable(book);
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Integer save(Book book) {
        String sql= "insert into book(title, author, description) values(:title, :author, :description)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", book.getTitle());
        mapSqlParameterSource.addValue("author", book.getAuthor());
        mapSqlParameterSource.addValue("description", book.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
