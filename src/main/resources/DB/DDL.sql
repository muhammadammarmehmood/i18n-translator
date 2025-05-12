CREATE SCHEMA IF NOT EXISTS translations;

CREATE TABLE translations.locales (
    id SERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    active_indicator BOOLEAN NOT NULL
);

CREATE TABLE translations.tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    active_indicator BOOLEAN NOT NULL
);

CREATE TABLE translations.translations (
    id SERIAL PRIMARY KEY,
    locale_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    key VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    active_indicator BOOLEAN NOT NULL,
    FOREIGN KEY (locale_id) REFERENCES translations.locales(id),
    FOREIGN KEY (tag_id) REFERENCES translations.tags(id)
);

CREATE INDEX idx_translations_locale_tag_key ON translations.translations (locale_id, tag_id, key);
CREATE INDEX idx_translations_tag ON translations.translations (tag_id);
CREATE INDEX idx_translations_key ON translations.translations (key);