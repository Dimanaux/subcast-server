package com.example.app.subcast.repositories;

import com.example.app.subcast.db.Progress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProgressRepositoryImpl implements ProgressRepository {
    private String upsertProgressQuery = "INSERT INTO progress (account_id, link, time) VALUES(?1, ?2, ?3) ON CONFLICT DO UPDATE SET time = ?3;";
    private String selectByAccountQuery = "SELECT * FROM progress WHERE account_id = ?";
    private String deleteQuery = "DELETE FROM progress WHERE account_id = ? AND link = ?";
    private String selectByAccountAndLinkQuery = selectByAccountQuery + " AND link = ?";

    private static final RowMapper<Progress> progressMapper = (rs, i) -> new Progress(
            rs.getInt("account_id"),
            rs.getString("link"),
            rs.getInt("time")
    );

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProgressRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * update OR CREATE progress
     *
     * @param accountId - whose progress
     * @param link      - episode identifier
     * @param time      - time listened (in seconds)
     */
    @Override
    public void updateProgress(int accountId, String link, int time) {
        jdbcTemplate.update(upsertProgressQuery, accountId, link, time);
    }

    /**
     * get all progress by account
     *
     * @param accountId - whose progress
     * @return - all the progress objects of the account
     */
    @Override
    public List<Progress> findAllByAccountId(int accountId) {
        return jdbcTemplate.query(selectByAccountQuery, progressMapper, accountId);
    }

    /**
     * get progress for particular episode
     *
     * @param accountId - whose progress
     * @param link      - episode identifier
     * @return - progress object
     */
    @Override
    public Progress findByAccountAndLink(int accountId, String link) {
        List<Progress> result = jdbcTemplate.query(selectByAccountAndLinkQuery, progressMapper, accountId, link);
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * delete progress for particular episode
     *
     * @param accountId - whose progress
     * @param link      - episode identifier
     */
    @Override
    public void deleteProgress(int accountId, String link) {
        jdbcTemplate.update(deleteQuery, accountId, link);
    }
}
