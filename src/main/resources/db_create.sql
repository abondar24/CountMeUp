CREATE DATABASE IF NOT EXISTS count_me_up;

USE count_me_up;

CREATE TABLE IF NOT EXISTS user (

  id            BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  token         VARCHAR(255),
  user_id       VARCHAR(255),
  vote_attempts INTEGER
);

CREATE TABLE IF NOT EXISTS competition (

  id         BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  start_date VARCHAR(255)       NOT NULL,
  end_date   VARCHAR(255),
  is_active  BOOLEAN
);

CREATE TABLE IF NOT EXISTS candidate (

  id             BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name           VARCHAR(255)       NOT NULL,
  competition_id BIGINT
);


ALTER TABLE candidate
  ADD CONSTRAINT fk_comp_cand
FOREIGN KEY (competition_id) REFERENCES competition (id);


CREATE TABLE IF NOT EXISTS vote (

  id             BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  candidate_id   BIGINT,
  competition_id BIGINT
);


ALTER TABLE vote
  ADD CONSTRAINT fk_vote_cand
FOREIGN KEY (candidate_id) REFERENCES candidate (id);

ALTER TABLE vote
  ADD CONSTRAINT fk_comp_vote
FOREIGN KEY (competition_id) REFERENCES competition (id);