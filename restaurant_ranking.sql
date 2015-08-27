CREATE TABLE restaurants (
  id serial PRIMARY KEY,
  name varchar,
  cuisine_id int,
  price varchar
);

CREATE TABLE cuisine (
  cuisine_id serial PRIMARY KEY,
  type varchar
);
