package com.example.app.subcast.repositories;

import com.example.app.subcast.db.Progress;

import java.util.List;

/**
 * repository to manage progress in podcasts episodes
 */
public interface ProgressRepository {
    /**
     * update OR CREATE progress
     *
     * @param accountId - whose progress
     * @param link      - episode identifier
     * @param seconds   - time listened
     */
    void updateProgress(int accountId, String link, int seconds);

    /**
     * get all progress by account
     *
     * @param accountId - whose progress
     * @return - all the progress objects of the account
     */
    List<Progress> findAllByAccountId(int accountId);

    /**
     * get progress for particular episode
     *
     * @param accountId - whose progress
     * @param link      - episode identifier
     * @return - progress object
     */
    Progress findByAccountAndLink(int accountId, String link);

    /**
     * delete progress for particular episode
     *
     * @param accountId - whose progress
     * @param link      - episode identifier
     */
    void deleteProgress(int accountId, String link);
}
