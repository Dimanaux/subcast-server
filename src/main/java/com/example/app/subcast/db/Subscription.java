package com.example.app.subcast.db;

public class Subscription {
    private Integer accountId;
    private Integer podcastId;

    public Subscription() {
    }

    public Subscription(Integer accountId, Integer podcastId) {
        this.accountId = accountId;
        this.podcastId = podcastId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Subscription setAccountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }

    public Integer getPodcastId() {
        return podcastId;
    }

    public Subscription setPodcastId(Integer podcastId) {
        this.podcastId = podcastId;
        return this;
    }
}
