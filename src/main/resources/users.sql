CREATE SEQUENCE id_sequence;

CREATE TABLE users (
  user_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('id_sequence'),
  user_name VARCHAR(20) NOT NULL UNIQUE,
  user_pass VARCHAR(255) NOT NULL,
  user_role VARCHAR(10) NOT NULL,
  user_avatar BYTEA
);

CREATE TABLE follows (
  follower_id BIGINT NOT NULL REFERENCES users (user_id),
  followee_id BIGINT NOT NULL REFERENCES users (user_id),
  PRIMARY KEY (followee_id, follower_id)
);

CREATE TABLE posts (
  post_id BIGINT PRIMARY KEY DEFAULT nextval('id_sequence'),
  post_user_id BIGINT NOT NULL REFERENCES users (user_id),
  post_text VARCHAR(140) NOT NULL,
  post_date TIMESTAMP NOT NULL
);

CREATE TABLE posts_likes (
  post_id BIGINT NOT NULL REFERENCES posts (post_id),
  user_id BIGINT NOT NULL REFERENCES users (user_id),
  PRIMARY KEY (post_id, user_id)
);

CREATE TABLE comments (
  comment_id BIGINT PRIMARY KEY DEFAULT nextval('id_sequence'),
  comment_text VARCHAR(140) NOT NULL,
  comment_date TIMESTAMP NOT NULL,
  comment_user_id BIGINT NOT NULL REFERENCES users (user_id),
  comment_post_id BIGINT NOT NULL REFERENCES posts (post_id)
);

CREATE TABLE comments_likes (
  comment_id BIGINT NOT NULL REFERENCES comments (comment_id),
  user_id BIGINT NOT NULL REFERENCES users (user_id),
  PRIMARY KEY (comment_id, user_id)
);
