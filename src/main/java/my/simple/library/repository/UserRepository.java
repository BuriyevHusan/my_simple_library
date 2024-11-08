package my.simple.library.repository;

import my.simple.library.model.AuthUser;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RoleRepository roleRepository;

    public UserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.roleRepository = roleRepository;
    }

    public Optional<AuthUser> findByUsername(String username) {
        String sql = "SELECT * FROM auth_user WHERE username = :username";


        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, Map.of("username", username),new BeanPropertyRowMapper<>(AuthUser.class));
            return Optional.ofNullable(authUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<AuthUser> findByEmail(String email) {
        String sql = "SELECT * FROM auth_user WHERE email = :email";

        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, Map.of("email", email), new BeanPropertyRowMapper<>(AuthUser.class));
            return Optional.ofNullable(authUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean save(AuthUser authUser) {
        String sql = "insert into auth_user (name, username, email, password, status) values (:name, :username, :email, :password, :status)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue("name", authUser.getName());
        mapSqlParameterSource.addValue("username", authUser.getUsername());
        mapSqlParameterSource.addValue("email", authUser.getEmail());
        mapSqlParameterSource.addValue("password", authUser.getPassword());
        mapSqlParameterSource.addValue("status", authUser.getStatus().name());

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            Integer key = Objects.requireNonNull(keyHolder.getKey()).intValue();

            roleRepository.saveByUserId(key);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AuthUser> findAllUser() {
        String sql = "SELECT * FROM auth_user";

        try {
            return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AuthUser.class));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Optional<AuthUser> findById(Integer id) {
        String sql = "select * from auth_user where id = :id";
        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, Map.of("id", id), new BeanPropertyRowMapper<>(AuthUser.class));
            return Optional.ofNullable(authUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void update(AuthUser user) {
        String sql = "update auth_user set status = :userStatus where id = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue("userStatus", user.getStatus().name());
        mapSqlParameterSource.addValue("id", user.getId());
        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
