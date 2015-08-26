Database Structure

Database Name: restaurant_ranking
Test Database Name: restaurant_ranking_test

Table 1
cuisine (cuisine_id serial PRIMARY KEY, type varchar)

Table 2
restaurants (id serial PRIMARY KEY, cuisine_id int, price_id int, name varchar)

Table 3
prices (price_id serial PRIMARY KEY, price_range int)
