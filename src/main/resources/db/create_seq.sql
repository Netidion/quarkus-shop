-- Create a sequence for the 'id' column
CREATE SEQUENCE customers_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Set the default value for 'id' using the sequence
ALTER TABLE customers
    ALTER COLUMN id SET DEFAULT nextval('customers_seq');

-- Attach the sequence to the 'id' column
ALTER TABLE customers
    ALTER COLUMN id SET NOT NULL;