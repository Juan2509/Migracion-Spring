-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_ai_run_errors (
    id UUID PRIMARY KEY,
    ai_run_id UUID NOT NULL,
    error_message TEXT NOT NULL,
    error_code VARCHAR(80) NOT NULL,
    provider_error_id VARCHAR(120) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_ai_run_errors_ai_run_id FOREIGN KEY (ai_run_id) REFERENCES chat_ai_runs (id)
);
