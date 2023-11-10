CREATE TABLE transaction
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    create_date  DATE                  NOT NULL,
    description  VARCHAR(255)          NOT NULL,
    amount       BIGINT                NOT NULL
);

CREATE INDEX "createDate_index" ON transaction (create_date);