CREATE DATABASE count_me_up;

CREATE TABLE user (

  id            BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  token         VARCHAR(255),
  user_id       VARCHAR(255),
  vote_attempts INTEGER
);

CREATE TABLE competition (

  id         BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  start_date VARCHAR(255)       NOT NULL,
  end_date   VARCHAR(255),
  is_active  BOOLEAN
);

CREATE TABLE candidate (

  id             BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name           VARCHAR(255)       NOT NULL,
  competitionId BIGINT
);


ALTER TABLE candidate
  ADD CONSTRAINT fk_comp_cand
FOREIGN KEY (competitionId) REFERENCES competition (id);


CREATE TABLE vote (

  id             BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  candidate_id   BIGINT,
  competitionId BIGINT
);


ALTER TABLE vote
  ADD CONSTRAINT fk_vote_cand
FOREIGN KEY (candidate_id) REFERENCES candidate (id);

ALTER TABLE vote
  ADD CONSTRAINT fk_comp_vote
FOREIGN KEY (competitionId) REFERENCES competition (id);