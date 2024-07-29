package my.simple.library.repository;

import my.simple.library.model.AuthUser;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<AuthUser> findByUsername(String username) {
        String sql = "SELECT * FROM auth_user WHERE username = :username";

        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("username", username), AuthUser.class);
            return Optional.ofNullable(authUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<AuthUser> findByEmail(String email) {
        String sql = "SELECT * FROM auth_user WHERE email = :email";

        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("email", email), AuthUser.class);
            return Optional.ofNullable(authUser);
        }catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean save(AuthUser authUser) {
        String sql = "INSERT INTO auth_user(name, username, email, password) VALUES(:name, :username, :email, :password)";
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", authUser.getName());
            mapSqlParameterSource.addValue("username", authUser.getUsername());
            mapSqlParameterSource.addValue("email", authUser.getEmail());
            mapSqlParameterSource.addValue("password", authUser.getPassword());

            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
