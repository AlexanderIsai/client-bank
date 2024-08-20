INSERT INTO customer (name, email, age, password, phone_number, created_date) VALUES ('Ivan Ivanov', 'ivanov@example.com', 28, 'password123', '1234567890', CURRENT_TIMESTAMP);
INSERT INTO customer (name, email, age, password, phone_number, created_date) VALUES ('Maria Petrova', 'mpetrova@example.com', 32, 'password456', '2345678901', CURRENT_TIMESTAMP);
INSERT INTO customer (name, email, age, password, phone_number, created_date) VALUES ('John Smith', 'john.smith@example.com', 40, 'password789', '3456789012', CURRENT_TIMESTAMP);

INSERT INTO employer (name, address, created_date) VALUES ('GlobalTech', '1010 Binary Rd', CURRENT_TIMESTAMP);
INSERT INTO employer (name, address, created_date) VALUES ('HealthCorp', '2021 Health St', CURRENT_TIMESTAMP);
INSERT INTO employer (name, address, created_date) VALUES ('EduFuture', '3030 Education Ave', CURRENT_TIMESTAMP);

INSERT INTO account (number, currency, balance, customer_id, created_date) VALUES ('1122334455', 'USD', 1500.0, 1, CURRENT_TIMESTAMP);
INSERT INTO account (number, currency, balance, customer_id, created_date) VALUES ('2233445566', 'EUR', 2500.0, 2, CURRENT_TIMESTAMP);
INSERT INTO account (number, currency, balance, customer_id, created_date) VALUES ('3344556677', 'UAH', 3500.0, 2, CURRENT_TIMESTAMP);
INSERT INTO account (number, currency, balance, customer_id, created_date) VALUES ('4455667788', 'CHF', 4500.0, 3, CURRENT_TIMESTAMP);
INSERT INTO account (number, currency, balance, customer_id, created_date) VALUES ('5566778899', 'GBP', 5500.0, 3, CURRENT_TIMESTAMP);

INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 2);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (2, 3);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (3, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (3, 3);
