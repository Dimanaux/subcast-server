package com.example.app.subcast.db;

import javax.persistence.*;

@Entity
@Table(name = "episode")
public class Episode {
    @Id
    @Column(name = "guid")
    private String guid;

    @ManyToOne
    @JoinColumn(name = "podcast_id")
    private Podcast podcast;

    @Column(name = "link")
    private String link;

    public Episode() {
    }

    public String getGuid() {
        return guid;
    }

    public Episode setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public Podcast getPodcast() {
        return podcast;
    }

    public Episode setPodcast(Podcast podcast) {
        this.podcast = podcast;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Episode setLink(String link) {
        this.link = link;
        return this;
    }
}
