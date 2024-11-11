CREATE TABLE IF NOT EXISTS cards (
    card_id serial primary key,
    customer_id INT NOT NULL,
    card_number VARCHAR(16) NOT NULL UNIQUE,
    card_type VARCHAR(50) NOT NULL,
    total_limit DECIMAL NOT NULL,
    amount_used DECIMAL NOT NULL,
    available_amount DECIMAL NOT NULL,
    created_at DATE NOT NULL,
    created_by VARCHAR(20) NOT NULL,
    updated_at DATE DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS card_payments (
    card_payment_id serial primary key ,
    card_number VARCHAR(16) NOT NULL,
    customer_id INT NOT NULL,
    paid_amount DECIMAL NOT NULL,
    created_at DATE NOT NULL,
    created_by VARCHAR(20) NOT NULL,
    updated_at DATE DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL,
    FOREIGN KEY (card_number) REFERENCES cards(card_number)
);

CREATE TABLE IF NOT EXISTS card_expenses (
    card_expense_id serial primary key,
    card_number VARCHAR(16) NOT NULL,
    description VARCHAR(100) NOT NULL,
    expense_type VARCHAR(50) NOT NULL,
    establishment_id VARCHAR(100) NOT NULL,
    amount DECIMAL NOT NULL,
    created_at DATE NOT NULL,
    created_by VARCHAR(20) NOT NULL,
    updated_at DATE DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL,
    FOREIGN KEY (card_number) REFERENCES cards(card_number)
);
