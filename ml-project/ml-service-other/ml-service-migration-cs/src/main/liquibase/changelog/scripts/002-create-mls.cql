CREATE TABLE IF NOT EXISTS trainresult_mls (
    id text PRIMARY KEY,
    dateTime text,
    close text,
    labelDatetime text,
    realResult text,
    prediction text,
    error text
);