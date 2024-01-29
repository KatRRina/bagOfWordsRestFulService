CREATE TABLE information(
                            id SERIAL PRIMARY KEY,
                            user_login VARCHAR(100) NOT NULL,
                            text_first VARCHAR(100) NOT NULL,
                            text_second VARCHAR(100) NOT NULL,
                            outer_value INTEGER NOT NULL,
                            create_ timestamp NOT NULL
);