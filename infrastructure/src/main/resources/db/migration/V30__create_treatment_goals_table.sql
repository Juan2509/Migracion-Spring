CREATE TABLE treatment_goals (
    id UUID PRIMARY KEY,
    treatment_plan_id UUID NOT NULL,
    description TEXT NOT NULL,
    target_date DATE NOT NULL,
    completed_at TIMESTAMPTZ NOT NULL,
    notes TEXT NOT NULL,
    treatment_goal_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_treatment_goals_plan
        FOREIGN KEY (treatment_plan_id) REFERENCES treatment_plans (id),
    CONSTRAINT fk_treatment_goals_status
        FOREIGN KEY (treatment_goal_id) REFERENCES treatment_goal_statuses (id)
);
