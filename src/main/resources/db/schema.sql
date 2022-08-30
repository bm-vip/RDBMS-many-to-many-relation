ALTER USER postgres PASSWORD 'postgres';
CREATE SCHEMA IF NOT EXISTS game_lover;
grant all privileges on database game_lover to postgres;