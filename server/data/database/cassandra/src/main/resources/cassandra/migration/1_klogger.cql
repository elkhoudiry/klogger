CREATE KEYSPACE IF NOT EXISTS klogger WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TYPE IF NOT EXISTS klogger.log_property (
id text,
name text,
value text
);

CREATE TABLE IF NOT EXISTS klogger.log (
id text,
log text,
type text,
level text,
properties list<frozen<klogger.log_property>>,
server_datetime text,
client_datetime text,
PRIMARY KEY (id)
);

CREATE TYPE IF NOT EXISTS klogger.event_property (
id text,
name text,
value text
);

CREATE TABLE IF NOT EXISTS klogger.event (
id text,
type text,
description text,
properties list<frozen<klogger.event_property>>,
server_datetime text,
client_datetime text,
PRIMARY KEY (id)
);