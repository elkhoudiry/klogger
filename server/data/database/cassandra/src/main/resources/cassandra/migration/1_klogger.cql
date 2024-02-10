CREATE KEYSPACE IF NOT EXISTS klogger WITH REPLICATION = {'class':'NetworkTopologyStrategy', 'Seed':3};

CREATE TABLE IF NOT EXISTS klogger.logs (
id text,
log text,
type text,
level text,
server_date_time text,
client_date_time text,
PRIMARY KEY ((id), id)
);

CREATE TABLE IF NOT EXISTS klogger.logs_properties (
id text,
log_id text,
name text,
value text,
PRIMARY KEY ((log_id), id)
);