package com.example.app.subcast.db;

import javax.persistence.*;

/**
 * represents someone's progress on particular podcast episode
 */
@Entity
@Table(name = "progress")
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // whose progress (account identifier)
    @Column(name = "account_id")
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "episode_guid")
    private Episode episode;

    // time listened in seconds
    @Column(name = "time")
    private Integer time;

    public Progress() {
    }

    public Long getId() {
        return id;
    }

    public Progress setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Progress setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public Episode getEpisode() {
        return episode;
    }

    public Progress setEpisode(Episode episode) {
        this.episode = episode;
        return this;
    }

    public Integer getTime() {
        return time;
    }

    public Progress setTime(Integer time) {
        this.time = time;
        return this;
    }
}
