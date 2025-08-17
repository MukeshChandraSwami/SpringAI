-- Creating embedding via LM Studio manually

CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS resource_data_embeddings (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    account_mapping_id UUID NOT NULL,
    resource_id UUID NOT NULL,
    resource_type TEXT NOT NULL,
    content TEXT NOT NULL,
    metadata json,
    embedding vector(384), -- because gte-small produces 384-d vectors
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX ON resource_data_embeddings USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
CREATE INDEX ON resource_data_embeddings (account_mapping_id);

DROP TABLE resource_data_embeddings;


-- Creating embedding via OpenAi Model automatically

CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE resource_data_embeddings (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content TEXT NOT null,
    metadata json,
    embedding vector(3072),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX ON resource_data_embeddings USING HNSW (embedding vector_cosine_ops);

