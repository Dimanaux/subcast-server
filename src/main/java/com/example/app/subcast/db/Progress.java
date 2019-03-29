package com.example.app.subcast.db;

/**
 * represents someone's progress on particular podcast episode
 */
public class Progress {
    // whose progress (account identifier)
    private int accountId;

    // episode link! IDENTIFIER
    private String link;

    // time listened in seconds
    private int time;

    public Progress() {
    }

    public Progress(int accountId, String link, int time) {
        this.accountId = accountId;
        this.link = link;
        this.time = time;
    }

    public int getAccountId() {
        return accountId;
    }

    public Progress setAccountId(int accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Progress setLink(String link) {
        this.link = link;
        return this;
    }

    public int getTime() {
        return time;
    }

    public Progress setTime(int time) {
        this.time = time;
        return this;
    }
}
