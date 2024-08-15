INSERT INTO customer (name, email, age) VALUES ('Ivan Ivanov', 'ivanov@example.com', 28);
INSERT INTO customer (name, email, age) VALUES ('Maria Petrova', 'mpetrova@example.com', 32);
INSERT INTO customer (name, email, age) VALUES ('John Smith', 'john.smith@example.com', 40);

INSERT INTO employer (name, address) VALUES ('GlobalTech', '1010 Binary Rd');
INSERT INTO employer (name, address) VALUES ('HealthCorp', '2021 Health St');
INSERT INTO employer (name, address) VALUES ('EduFuture', '3030 Education Ave');

INSERT INTO account (number, currency, balance, customer_id) VALUES ('1122334455', 'USD', 1500.0, 1);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('2233445566', 'EUR', 2500.0, 2);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('3344556677', 'UAH', 3500.0, 2);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('4455667788', 'CHF', 4500.0, 3);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('5566778899', 'GBP', 5500.0, 3);

INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 2);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (2, 3);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (3, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (3, 3);
