-- Creating the marketing_channels table. A event can have multiple marketing channels.
create table if not exists marketing_channels (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    acct_mapping_id int not null,
    event_id uuid not null,
    channelName text not null,
    created_at TIMESTAMP DEFAULT NOW()
);

create table if not exists marketing_channel_content (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    acct_mapping_id int not null,
    event_id uuid not null,
    channel_id uuid not null,
    content text not null,
    created_at TIMESTAMP DEFAULT NOW()
);

create table if not exists event_details (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    acct_mapping_id int not null,
    event_id uuid not null,
    title text not null,
    description text not null,
    session_title text,
    speaker_bio text,
    language_code text not null,
    created_at TIMESTAMP DEFAULT NOW()
);

create table if not exists seo_keywords (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    acct_mapping_id int not null,
    event_id uuid not null,
    seo_keyword text not null,
    created_at TIMESTAMP DEFAULT NOW()
);
