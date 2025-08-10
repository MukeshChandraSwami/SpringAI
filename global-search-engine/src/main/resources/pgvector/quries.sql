CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store(
                                           id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content text,
    metadata json,
    embedding vector(1536) -- for open ai model 4o-mini
    );

CREATE INDEX ON vector_store using HNSW (embedding vector_cosine_ops);

DROP TABLE vector_store;


-- -----------------------------------------------------------------------------------------
CREATE TABLE resource_data_embeddings (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    account_mapping_id UUID NOT NULL,
    resource_id UUID NOT NULL,
    resource_type TEXT NOT NULL,
    content TEXT NOT NULL,
    embedding vector(384), -- because gte-small produces 384-d vectors
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX ON resource_data_embeddings USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
CREATE INDEX ON resource_data_embeddings (account_mapping_id);

DROP TABLE resource_data_embeddings;
-- -----------------------------------------------------------------------------------------

CREATE TABLE chat_memory (
                             id TEXT PRIMARY KEY,
                             conversation_id TEXT NOT NULL,
                             user_message TEXT NOT NULL,
                             bot_response TEXT NOT NULL
);

CREATE INDEX idx_conversation_id ON chat_memory(conversation_id);

SELECT * FROM pg_available_extensions WHERE name = 'vector';

SELECT * FROM vector_store;
SELECT * FROM chat_memory;
