package iftm.optimisticlock.repositories.mapper;

import iftm.optimisticlock.models.SockItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SockItemRowMapper implements RowMapper<SockItem> {

    @Override
    public SockItem mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        final SockItem sockItem = new SockItem();
        sockItem.setId(resultSet.getLong("id"));
        sockItem.setName(resultSet.getString("name"));
        sockItem.setQuantity(resultSet.getInt("quantity"));
        sockItem.setVersion(resultSet.getInt("version"));
        return sockItem;
    }
}
