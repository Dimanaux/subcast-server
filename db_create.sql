CREATE TABLE account
(
  id       SERIAL PRIMARY KEY,
  username TEXT NOT NULL,
  password TEXT NOT NULL,
  CONSTRAINT unique_username UNIQUE (username)
);

CREATE TABLE account_token
(
  token      TEXT PRIMARY KEY,
  account_id INTEGER,
  CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE podcast
(
  id       INTEGER PRIMARY KEY,
  feed_url TEXT NOT NULL
);

CREATE TABLE episode
(
  guid       TEXT PRIMARY KEY,
  podcast_id INTEGER,
  link       TEXT
);

CREATE TABLE subscription
(
  account_id INTEGER NOT NULL,
  podcast_id INTEGER NOT NULL,
  CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES account (id),
  CONSTRAINT subscription_unique_row PRIMARY KEY (account_id, podcast_id)
);

CREATE TABLE progress
(
  id           INTEGER PRIMARY KEY,
  episode_guid TEXT    NOT NULL,
  account_id   INTEGER NOT NULL,
  time         INTEGER NOT NULL,
  CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES account (id),
  CONSTRAINT progress_unique_row UNIQUE (episode_guid, account_id)
);

CREATE TABLE history
(
  episode_guid TEXT    PRIMARY KEY ,
  account_id   INTEGER NOT NULL,
  CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES account (id),
  CONSTRAINT history_unique_row UNIQUE (episode_guid, account_id)
);
