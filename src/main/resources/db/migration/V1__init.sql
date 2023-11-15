CREATE TABLE system
(
    system_id SERIAL PRIMARY KEY,
    name  TEXT NOT NULL
);
ALTER SEQUENCE system_system_id_seq RESTART 10000;

CREATE TABLE mapping
(
    mapping_id SERIAL PRIMARY KEY,
    source_system_id INT references system (system_id),
    source_value TEXT NOT NULL,
    target_system_id INT references system (system_id),
    target_value TEXT NOT NULL,
    target_value_type TEXT NOT NULL
);
ALTER SEQUENCE mapping_mapping_id_seq RESTART 10000;