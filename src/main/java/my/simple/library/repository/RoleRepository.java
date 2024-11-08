package my.simple.library.repository;

import my.simple.library.model.UserRole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class RoleRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RoleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<UserRole> findByUserId(Integer id) {
        String sql = "select r.* from user_role ur join role r on r.id = ur.role_id where ur.user_id = :userId";

        try {
            return namedParameterJdbcTemplate.query(sql, Map.of("userId", id), BeanPropertyRowMapper.newInstance(UserRole.class));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void saveByUserId(Integer userId) {
        String sql="insert into user_role(user_id,role_id) values(:userId,:roleId)";
        Integer userRoleId = getRoleUserById();
        System.out.println(userRoleId);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        mapSqlParameterSource.addValue("roleId", userRoleId);

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer getRoleUserById(){
        String sql = "select id from role where code like 'USER'";
        return namedParameterJdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
    }

    private Integer getRoleAdminById(){
        String sql = "select id from role where code like 'ADMIN'";
        return namedParameterJdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
    }
}
