CREATE TABLE posts (
  post_id BIGINT PRIMARY KEY DEFAULT nextval('id_sequence'),
  post_user_id BIGINT NOT NULL
);