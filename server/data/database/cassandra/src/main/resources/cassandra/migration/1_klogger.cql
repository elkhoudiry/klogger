CREATE KEYSPACE IF NOT EXISTS klogger WITH REPLICATION = {'class':'SimpleStrategy', 'Seed':1};

CREATE TABLE IF NOT EXISTS klogger.logs (
id text,
log text,
type text,
level text,
properties list<frozen<klogger.log_property>>,
server_datetime text,
client_datetime text,
PRIMARY KEY ((id), id)
);

CREATE TYPE IF NOT EXISTS klogger.log_property (
id text,
name text,
value text
);