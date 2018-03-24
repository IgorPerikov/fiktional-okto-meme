CREATE TABLE IF NOT EXISTS users (
  id    SERIAL PRIMARY KEY,
  name  VARCHAR(100) NOT NULL,
  phone VARCHAR(15)  NOT NULL
);

CREATE TABLE IF NOT EXISTS service_providers (
  id          SERIAL PRIMARY KEY,
  user_id     INTEGER REFERENCES users (id),
  description VARCHAR(1000) NOT NULL,
  category    VARCHAR(20)   NOT NULL
);

CREATE TABLE IF NOT EXISTS service_requests (
  id          SERIAL PRIMARY KEY,
  user_id     INTEGER REFERENCES users (id),
  description VARCHAR(1000) NOT NULL,
  category    VARCHAR(20)   NOT NULL
);
