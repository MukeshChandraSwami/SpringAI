CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store(
                                           id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content text,
    metadata json,
    embedding vector(1536)
    );

CREATE INDEX ON vector_store using HNSW (embedding vector_cosine_ops);

CREATE TABLE chat_memory (
                             id TEXT PRIMARY KEY,
                             conversation_id TEXT NOT NULL,
                             user_message TEXT NOT NULL,
                             bot_response TEXT NOT NULL
);

CREATE INDEX idx_conversation_id ON chat_memory(conversation_id);

SELECT * FROM pg_available_extensions WHERE name = 'vector';

SELECT * FROM vector_store;
