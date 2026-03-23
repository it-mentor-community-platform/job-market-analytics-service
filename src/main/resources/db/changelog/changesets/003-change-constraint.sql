ALTER TABLE search_queries DROP CONSTRAINT query_unique;

ALTER TABLE search_queries ADD CONSTRAINT title_unique UNIQUE (title);