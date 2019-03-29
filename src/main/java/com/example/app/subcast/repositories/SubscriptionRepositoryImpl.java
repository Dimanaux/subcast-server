package com.example.app.subcast.repositories;

import com.example.app.subcast.db.Subscription;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    private final static String selectSubscriptionsByAccountQuery = "SELECT * FROM subscription WHERE account_id = ? ";
    private final static String insertSubscriptionQuery = "INSERT INTO subscription (account_id, podcast_id) VALUES (?, ?)";
    private final static String deleteSubscriptionByAccountAndPodcastIdQuery = "DELETE FROM subscription WHERE account_id = ? AND podcast_id = ?";

    private final static RowMapper<Subscription> subscriptionMapper = (rs, i) -> new Subscription(
            rs.getInt("account_id"),
            rs.getInt("podcast_id")
    );

    private final JdbcTemplate jdbcTemplate;


    public SubscriptionRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createSubscription(int accountId, int subscriptionId) {
        jdbcTemplate.update(insertSubscriptionQuery, accountId, subscriptionId);
    }

    @Override
    public List<Subscription> findAllByAccountId(int accountId) {
        return jdbcTemplate.query(selectSubscriptionsByAccountQuery, subscriptionMapper, accountId);
    }

    @Override
    public void deleteSubscription(int accountId, int podcastId) {
        jdbcTemplate.update(deleteSubscriptionByAccountAndPodcastIdQuery, accountId, podcastId);
    }
}
