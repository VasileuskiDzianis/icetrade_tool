DROP TABLE IF EXISTS request_log;
CREATE TABLE request_log (
	id BIGSERIAL PRIMARY KEY,
	tender_id BIGINT,
	request_date TIMESTAMP
);