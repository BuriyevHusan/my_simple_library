package my.simple.library.repository;

import my.simple.library.model.Attachment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class AttachmentRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AttachmentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<Attachment> findById(String id) {
        String sql = "select * from attachment where book_id = :id";

        try {
            Attachment attachment = namedParameterJdbcTemplate.queryForObject(sql, Map.of("id", id), Attachment.class);
            return Optional.ofNullable(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Integer save(Attachment attachment) {
        String sql = "insert into attachment(original_file_name, generated_file_name, size, content_type, image_url, book_id) " +
                "values(:original_name, :generated_name, :size, :content_type, :image_url, :book_id)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("original_name", attachment.getOriginalFileName())
                .addValue("generated_name", attachment.getGeneratedFileName())
                .addValue("size", attachment.getSize())
                .addValue("content_type", attachment.getContentType())
                .addValue("image_url", attachment.getImageUrl())
                .addValue("book_id", attachment.getBookId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
