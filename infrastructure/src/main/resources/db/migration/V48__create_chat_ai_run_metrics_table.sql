-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE chat_ai_run_metrics (
    id UUID PRIMARY KEY,
    ai_run_id UUID NOT NULL,
    prompt_tokens INTEGER NOT NULL,
    completion_tokens INTEGER NOT NULL,
    total_tokens INTEGER NOT NULL,
    cost DECIMAL(10,6) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_chat_ai_run_metrics_ai_run_id FOREIGN KEY (ai_run_id) REFERENCES chat_ai_runs (id)
);
