package com.example.app.subcast.repositories;

import com.example.app.subcast.db.Subscription;

import java.util.List;

public interface SubscriptionRepository {
    void createSubscription(int accountId, int subscriptionId);

    List<Subscription> findAllByAccountId(int accountId);

    void deleteSubscription(int accountId, int podcastId);
}
