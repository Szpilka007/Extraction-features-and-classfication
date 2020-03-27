CREATE TABLE IF NOT EXISTS authors (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL
);

INSERT INTO authors (first_name, last_name) VALUES
  ('Gracjan', 'Grala'),
  ('Adam', 'Krzanowski');