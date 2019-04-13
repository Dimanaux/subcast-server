package com.example.app.subcast.db.repositories;

import com.example.app.subcast.db.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
    @Override
    @Modifying
    @Transactional
    default <P extends Podcast> P save(P podcast) {
        Optional<Podcast> dbPodcast = findById(podcast.getId());
        Optional<String> feed = dbPodcast.map(Podcast::getFeedUrl);

        if (feed.isPresent() && !feed.get().isEmpty()) {
            return (P) dbPodcast.get();
        }

        savePodcast(podcast.getId(), podcast.getFeedUrl());
        return podcast;
    }

    @Query(
            value = "INSERT INTO podcast (id, feed_url)" +
                    " VALUES (:id, :feed) ",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    void savePodcast(@Param("id") long podcastId, @Param("feed") String feedUrl);

    @Query(
            value = "INSERT INTO subscription (account_id, podcast_id)" +
                    " VALUES (:account, :podcast)",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    void createSubscription(@Param("account") long accountId,
                            @Param("podcast") long subscriptionId);

    @Query(
            value = "DELETE FROM subscription WHERE" +
                    " account_id = :account AND podcast_id = :podcast",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    void deleteSubscription(@Param("account") long accountId,
                            @Param("podcast") long podcastId);
}
