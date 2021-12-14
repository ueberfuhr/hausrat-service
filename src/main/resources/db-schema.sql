CREATE TABLE product (
    name VARCHAR(255) PRIMARY KEY,
    price INT NOT NULL
);
CREATE TABLE insurancecalculation (
    id INT PRIMARY KEY auto_increment,
    product VARCHAR(255) NOT NULL,
    livingarea NUMERIC(18,2) NOT NULL,
    principal VARCHAR(255),
    value NUMERIC(18,2) NOT NULL,
    currency VARCHAR(10),
    timestamp TIMESTAMP NOT NULL
);
