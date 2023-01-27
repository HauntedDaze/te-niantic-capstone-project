-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER comic_book_owner
WITH PASSWORD 'comic_book_pass';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO comic_book_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO comic_book_owner;

CREATE USER comic_book_appuser
WITH PASSWORD 'comic_book_pass';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO comic_book_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO comic_book_appuser;
