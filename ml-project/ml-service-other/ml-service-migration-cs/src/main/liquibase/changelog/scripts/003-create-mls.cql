CREATE TABLE IF NOT EXISTS transform_mls (
    id text PRIMARY KEY,
    ticker text,
    taskNumber text,
    dateStart text,
    dateEnd text,
    dateOffset text,
    batchSize text
);