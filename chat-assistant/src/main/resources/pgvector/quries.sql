CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE chat_memory (
                             id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
                             user_id UUID NOT NULL,
                             conversation_id UUID NOT NULL,
                             user_type TEXT NOT NULL,
                             message TEXT NOT NULL,
                             created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_conversation_id ON chat_memory(conversation_id);

SELECT * FROM chat_memory;
