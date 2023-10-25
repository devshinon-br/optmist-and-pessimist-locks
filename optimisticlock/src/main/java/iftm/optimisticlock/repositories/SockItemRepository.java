package iftm.optimisticlock.repositories;

import iftm.optimisticlock.models.SockItem;
import iftm.optimisticlock.repositories.mapper.SockItemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SockItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public SockItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SockItem> findAll() {
        String sql = "SELECT * FROM sock_item";
        return jdbcTemplate.query(sql, new SockItemRowMapper());
    }

    public SockItem findById(Long id) {
        String sql = "SELECT * FROM sock_item WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new SockItemRowMapper());
    }

    public void save(SockItem sockItem) {
        String sql = "INSERT INTO sock_item (id, name, quantity, version) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, sockItem.getId(), sockItem.getName(), sockItem.getQuantity(), sockItem.getVersion());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM sock_item WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
