CREATE TABLE IF NOT EXISTS classification_tasks (
    id BIGSERIAL,
    created_at VARCHAR(64),
    status VARCHAR(64) NOT NULL,
    meta JSONB NOT NULL,
    storage_file_id VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS classification_subtasks (
    id BIGSERIAL,
    status VARCHAR(64) NOT NULL,
    classification_task_id BIGINT REFERENCES classification_tasks(id) NOT NULL,
    context JSONB,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS classification_data (
    id BIGSERIAL,
    text VARCHAR(255) NOT NULL,
    code VARCHAR(16),
    classification_subtask_id BIGINT REFERENCES classification_subtasks(id) NOT NULL,
    PRIMARY KEY (id)
);