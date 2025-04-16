----------------------------------------------------
-- DROP IF EXIST
----------------------------------------------------
DROP TABLE SALE_PARTICIPANT CASCADE CONSTRAINTS;
DROP TABLE SALE CASCADE CONSTRAINTS;
DROP TABLE CAR CASCADE CONSTRAINTS;
DROP TABLE PERSON CASCADE CONSTRAINTS;
DROP TABLE DEALER CASCADE CONSTRAINTS;
DROP TABLE CAR_MODEL CASCADE CONSTRAINTS;
DROP TABLE MANUFACTURER CASCADE CONSTRAINTS;

----------------------------------------------------
-- DEFINITION
----------------------------------------------------

CREATE TABLE MANUFACTURER (
    manufacturer_id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    country VARCHAR2(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE CAR_MODEL (
    model_id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    manufacturer_id NUMBER NOT NULL,
    model_name VARCHAR2(100) NOT NULL,
    category VARCHAR2(50),
    base_price NUMBER(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_car_model_manufacturer FOREIGN KEY (manufacturer_id)
        REFERENCES MANUFACTURER(manufacturer_id)
);

CREATE TABLE DEALER (
    dealer_id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    dealer_name VARCHAR2(100) NOT NULL,
    location VARCHAR2(150),
    contact_info VARCHAR2(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE PERSON (
    person_id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    first_name VARCHAR2(50) NOT NULL,
    last_name VARCHAR2(50) NOT NULL,
    contact_number VARCHAR2(20),
    email VARCHAR2(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE CAR (
    car_id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    model_id NUMBER NOT NULL,
    vin VARCHAR2(17) UNIQUE NOT NULL,
    color VARCHAR2(30),
    manufacturing_year NUMBER(4),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_car_model FOREIGN KEY (model_id)
        REFERENCES CAR_MODEL(model_id)
);

CREATE TABLE SALE (
    sale_id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    car_id NUMBER NOT NULL,
    sale_date DATE DEFAULT SYSDATE,
    sale_price NUMBER(10, 2) NOT NULL,
    country VARCHAR2(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sale_car FOREIGN KEY (car_id)
        REFERENCES CAR(car_id)
);

CREATE TABLE SALE_PARTICIPANT (
    sale_participant_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sale_id NUMBER NOT NULL,
    participant_id NUMBER NOT NULL,
    participant_type VARCHAR2(10) NOT NULL,
    role VARCHAR2(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sale_participant_sale FOREIGN KEY (sale_id)
        REFERENCES SALE(sale_id),
    CONSTRAINT chk_participant_type CHECK (participant_type IN ('PERSON', 'DEALER')),
    CONSTRAINT chk_participant_role CHECK (role IN ('BUYER', 'SELLER'))
);

----------------------------------------------------
-- MODIFICATION
----------------------------------------------------
-- Add foreign key constraints to link SALE_PARTICIPANT to PERSON and DEALER
ALTER TABLE SALE_PARTICIPANT
ADD CONSTRAINT fk_sale_participant_person
    FOREIGN KEY (participant_id)
    REFERENCES PERSON(person_id)
    DEFERRABLE INITIALLY DEFERRED
    DISABLE;

ALTER TABLE SALE_PARTICIPANT
ADD CONSTRAINT fk_sale_participant_dealer
    FOREIGN KEY (participant_id)
    REFERENCES DEALER(dealer_id)
    DEFERRABLE INITIALLY DEFERRED
    DISABLE;

----------------------------------------------------
-- TRIGGERS
----------------------------------------------------

-- Create a trigger to validate the proper reference based on participant_type
CREATE OR REPLACE TRIGGER trg_validate_sale_participant
BEFORE INSERT OR UPDATE ON SALE_PARTICIPANT
FOR EACH ROW
BEGIN
    IF :NEW.participant_type = 'PERSON' THEN
        -- Verify the participant_id exists in PERSON table
        DECLARE
            v_count NUMBER;
        BEGIN
            SELECT COUNT(*) INTO v_count FROM PERSON WHERE person_id = :NEW.participant_id;
            IF v_count = 0 THEN
                RAISE_APPLICATION_ERROR(-20001, 'Referenced person_id does not exist');
            END IF;
        END;
    ELSIF :NEW.participant_type = 'DEALER' THEN
        -- Verify the participant_id exists in DEALER table
        DECLARE
            v_count NUMBER;
        BEGIN
            SELECT COUNT(*) INTO v_count FROM DEALER WHERE dealer_id = :NEW.participant_id;
            IF v_count = 0 THEN
                RAISE_APPLICATION_ERROR(-20002, 'Referenced dealer_id does not exist');
            END IF;
        END;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_manufacturer_timestamps
BEFORE INSERT OR UPDATE ON MANUFACTURER
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    :NEW.created_at := COALESCE(:NEW.created_at, CURRENT_TIMESTAMP);
    :NEW.updated_at := CURRENT_TIMESTAMP;
  ELSIF UPDATING THEN
    :NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_car_model_timestamps
BEFORE INSERT OR UPDATE ON CAR_MODEL
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    :NEW.created_at := COALESCE(:NEW.created_at, CURRENT_TIMESTAMP);
    :NEW.updated_at := CURRENT_TIMESTAMP;
  ELSIF UPDATING THEN
    :NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_dealer_timestamps
BEFORE INSERT OR UPDATE ON DEALER
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    :NEW.created_at := COALESCE(:NEW.created_at, CURRENT_TIMESTAMP);
    :NEW.updated_at := CURRENT_TIMESTAMP;
  ELSIF UPDATING THEN
    :NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_person_timestamps
BEFORE INSERT OR UPDATE ON PERSON
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    :NEW.created_at := COALESCE(:NEW.created_at, CURRENT_TIMESTAMP);
    :NEW.updated_at := CURRENT_TIMESTAMP;
  ELSIF UPDATING THEN
    :NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_car_timestamps
BEFORE INSERT OR UPDATE ON CAR
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    :NEW.created_at := COALESCE(:NEW.created_at, CURRENT_TIMESTAMP);
    :NEW.updated_at := CURRENT_TIMESTAMP;
  ELSIF UPDATING THEN
    :NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_sale_timestamps
BEFORE INSERT OR UPDATE ON SALE
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    :NEW.created_at := COALESCE(:NEW.created_at, CURRENT_TIMESTAMP);
    :NEW.updated_at := CURRENT_TIMESTAMP;
  ELSIF UPDATING THEN
    :NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_sale_participant_timestamps
BEFORE INSERT OR UPDATE ON SALE_PARTICIPANT
FOR EACH ROW
BEGIN
  IF INSERTING THEN
    :NEW.created_at := COALESCE(:NEW.created_at, CURRENT_TIMESTAMP);
    :NEW.updated_at := CURRENT_TIMESTAMP;
  ELSIF UPDATING THEN
    :NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
END;
/

----------------------------------------------------
-- DATA
----------------------------------------------------

-- Insert data for MANUFACTURER
INSERT INTO MANUFACTURER (name, country) VALUES ('Toyota', 'Japan');
INSERT INTO MANUFACTURER (name, country) VALUES ('Mazda', 'Japan');
INSERT INTO MANUFACTURER (name, country) VALUES ('Honda', 'Japan');
INSERT INTO MANUFACTURER (name, country) VALUES ('Ford', 'USA');
INSERT INTO MANUFACTURER (name, country) VALUES ('Dodge', 'USA');
INSERT INTO MANUFACTURER (name, country) VALUES ('Hyundai', 'South Korea');
INSERT INTO MANUFACTURER (name, country) VALUES ('Volkswagen', 'Germany');
INSERT INTO MANUFACTURER (name, country) VALUES ('BMW', 'Germany');
INSERT INTO MANUFACTURER (name, country) VALUES ('Audi', 'Germany');
INSERT INTO MANUFACTURER (name, country) VALUES ('Porsche', 'Germany');
INSERT INTO MANUFACTURER (name, country) VALUES ('Jaguar', 'England');
INSERT INTO MANUFACTURER (name, country) VALUES ('Rolls-Royce', 'England');
INSERT INTO MANUFACTURER (name, country) VALUES ('McLaren', 'England');
INSERT INTO MANUFACTURER (name, country) VALUES ('Koenigsegg', 'Sweden');
INSERT INTO MANUFACTURER (name, country) VALUES ('Lamborghini', 'Italia');

-- Insert data for CAR_MODEL
-- For Toyota (ID 1)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (1, 'Corolla', 'Sedan', 20000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (1, 'Prius', 'Hatchback', 29000);

-- For Mazda (ID 2)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (2, '6', 'Sedan', 37000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (2, 'Mx-5', 'Coupe', 30000);

-- For Honda (ID 3)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (3, 'Accord', 'Sedan', 29500);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (3, 'Civic', 'Hatchback', 25000);

-- For Ford (ID 4)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (4, 'Mustang', 'Coupe', 31000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (4, 'Focus', 'Hatchback', 37000);

-- For Dodge (ID 5)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (5, 'Charger', 'Sedan', 40000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (5, 'Durango', 'SUV', 45000);

-- For Hyundai (ID 6)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (6, 'Sonata', 'Sedan', 30000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (6, 'Tucson', 'SUV', 35000);

-- For Volkswagen (ID 7)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (7, 'Golf', 'Hatchback', 27000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (7, 'Passat', 'Sedan', 33000);

-- For BMW (ID 8)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (8, '3 Series', 'Sedan', 40000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (8, 'X5', 'SUV', 60000);

-- For Audi (ID 9)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (9, 'A4', 'Sedan', 42000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (9, 'Q7', 'SUV', 65000);

-- For Porsche (ID 10)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (10, '911', 'Coupe', 80000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (10, 'Cayenne', 'SUV', 90000);

-- For Jaguar (ID 11)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (11, 'F-Type', 'Coupe', 65000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (11, 'XE', 'Sedan', 45000);

-- For Rolls-Royce (ID 12)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (12, 'Phantom', 'Sedan', 400000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (12, 'Cullinan', 'SUV', 350000);

-- For McLaren (ID 13)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (13, '720S', 'Coupe', 300000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (13, 'Artura', 'Coupe', 250000);

-- For Koenigsegg (ID 14)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (14, 'Agera RS', 'Coupe', 2700000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (14, 'Jesko', 'Coupe', 3000000);

-- For Lamborghini (ID 15)
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (15, 'Aventador', 'Coupe', 400000);
INSERT INTO CAR_MODEL (manufacturer_id, model_name, category, base_price) VALUES (15, 'Urus', 'SUV', 220000);

-- Insert data for DEALER
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Prime Autos', 'New York', 'contact@primeautos.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Fast Cars', 'Los Angeles', 'info@fastcars.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('AutoHub', 'Chicago', 'sales@autohub.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Mega Motors', 'Houston', 'service@megamotors.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('DreamCars', 'Phoenix', 'dreamcars@autos.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Luxury Wheels', 'Miami', 'contact@luxurywheels.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('City Motors', 'San Francisco', 'info@citymotors.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Elite Auto Sales', 'Dallas', 'sales@eliteautos.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Speedway Autos', 'Atlanta', 'contact@speedwayautos.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('AutoNation', 'Seattle', 'service@autonation.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Highway Motors', 'Las Vegas', 'info@highwaymotors.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('AutoWorld', 'Orlando', 'support@autoworld.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('DriveTime', 'Boston', 'sales@drivetime.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Car Mart', 'Denver', 'contact@carmart.com');
INSERT INTO DEALER (dealer_name, location, contact_info) VALUES ('Green Wheels', 'Portland', 'service@greenwheels.com');

-- Insert data for PERSON
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('John', 'Doe', '555-1234', 'john.doe@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Jane', 'Smith', '555-5678', 'jane.smith@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Michael', 'Johnson', '555-8765', 'michael.johnson@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Emily', 'Davis', '555-4321', 'emily.davis@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('David', 'Brown', '555-6789', 'david.brown@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Sophia', 'Wilson', '555-1122', 'sophia.wilson@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('James', 'Taylor', '555-3344', 'james.taylor@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Olivia', 'Miller', '555-5566', 'olivia.miller@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Daniel', 'Anderson', '555-7788', 'daniel.anderson@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Isabella', 'Thomas', '555-9900', 'isabella.thomas@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Alexander', 'Martinez', '555-2233', 'alexander.martinez@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Mia', 'Garcia', '555-4455', 'mia.garcia@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Benjamin', 'Rodriguez', '555-6677', 'benjamin.rodriguez@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('Ava', 'Hernandez', '555-8899', 'ava.hernandez@email.com');
INSERT INTO PERSON (first_name, last_name, contact_number, email) VALUES ('William', 'Lopez', '555-1011', 'william.lopez@email.com');

-- Insert data for CAR
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (1,  '1HGBH41JXMN109186', 'Red',    2022);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (22, '1HGCM82633A123456', 'Blue',   2021);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (13, '2T1BURHE0JC123456', 'Black',  2020);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (20, '3FADP4EJ5LM123456', 'White',  2023);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (25, '1C4RJFAG2KC123456', 'Silver', 2021);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (16, '1C4RJFAG2JC654321', 'Green',  2020);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (7,  '3CZRU6H75KG123456', 'Yellow', 2022);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (28, '1VWAA7A33KC123456', 'Orange', 2023);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (19, 'WAUZFAFC5KN123456', 'Purple', 2021);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (10, '1N4AL3AP2JC123456', 'Blue',   2022);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (30, '3LN6L5D95KR123456', 'Orange', 2021);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (2,  'WAUZFAFC5KN1SDFFF', 'Black',  2020);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (13, '1FMCU0GD1KU123456', 'White',  2023);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (8,  '2C3CDXASDAH123456', 'White',  2021);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (15, '1G6AH5SX4D0123456', 'Silver', 2022);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (16, '1FADP3F26JL123456', 'Red',    2020);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (21, '5NMS3CAD1KH123456', 'Black',  2021);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (18, '2C3CDXCT9LH123456', 'Green',  2022);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (19, '1C4PJMLB2KD123456', 'Blue',   2023);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (27, '1G1ZD5ST2JF123456', 'Yellow', 2021);
INSERT INTO CAR (model_id, vin, color, manufacturing_year) VALUES (11, '1HGBH41JXMN109187', 'White',  2021);

-- Insert data for SALE
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (1,  TO_DATE('2023-01-15', 'YYYY-MM-DD'), 43000.00, 'USA');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (12, TO_DATE('2023-02-20', 'YYYY-MM-DD'), 78000.00, 'Germany');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (3,  TO_DATE('2023-03-05', 'YYYY-MM-DD'), 30000.00, 'Japan');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (14, TO_DATE('2023-03-18', 'YYYY-MM-DD'), 32000.00, 'France');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (5,  TO_DATE('2023-04-12', 'YYYY-MM-DD'), 27000.00, 'Italy');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (16, TO_DATE('2023-05-25', 'YYYY-MM-DD'), 23000.00, 'Spain');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (7,  TO_DATE('2023-06-10', 'YYYY-MM-DD'), 26000.00, 'USA');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (8,  TO_DATE('2023-07-22', 'YYYY-MM-DD'), 32000.00, 'Canada');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (19, TO_DATE('2023-08-17', 'YYYY-MM-DD'), 34000.00, 'Australia');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (20, TO_DATE('2023-09-30', 'YYYY-MM-DD'), 48000.00, 'UK');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (11, TO_DATE('2023-10-15', 'YYYY-MM-DD'), 42000.00, 'Germany');
INSERT INTO SALE (car_id, sale_date, sale_price, country) VALUES  (10, TO_DATE('2023-11-28', 'YYYY-MM-DD'), 55000.00, 'USA');

-- Insert data for SALE_PARTICIPANT with participant_type column
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (1, 1, 'PERSON', 'BUYER');  -- John Doe as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (1, 1, 'DEALER', 'SELLER');  -- Prime Autos as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (2, 3, 'PERSON', 'BUYER');  -- Jane Smith as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (2, 2, 'DEALER', 'SELLER');  -- Fast Cars as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (3, 5, 'PERSON', 'BUYER');  -- Michael Johnson as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (3, 6, 'PERSON', 'SELLER');  -- Olivia Miller as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (4, 7, 'PERSON', 'BUYER');  -- Emily Davis as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (4, 3, 'DEALER', 'SELLER');  -- AutoHub as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (5, 9, 'PERSON', 'BUYER');  -- David Brown as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (5, 5, 'DEALER', 'SELLER');  -- DreamCars as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (6, 11, 'PERSON', 'BUYER');  -- Sophia Wilson as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (6, 12, 'PERSON', 'SELLER');  -- Isabella Thomas as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (7, 13, 'PERSON', 'BUYER');  -- James Taylor as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (7, 6, 'DEALER', 'SELLER');  -- Luxury Wheels as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (8, 8, 'PERSON', 'BUYER');  -- Olivia Miller as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (8, 8, 'DEALER', 'SELLER');  -- Elite Auto Sales as seller
--ERR
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (9, 9, 'PERSON', 'BUYER');  -- Daniel Anderson as buyer
--ERR
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (9, 11, 'PERSON', 'SELLER');  -- Alexander Martinez as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (10, 10, 'PERSON', 'BUYER');  -- Isabella Thomas as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (10, 10, 'DEALER', 'SELLER');  -- Another Dealer as seller
-- ERR
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (11, 11, 'PERSON', 'BUYER');  -- Another person as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (11, 4, 'DEALER', 'SELLER');  -- Another dealer as seller

INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (12, 15, 'PERSON', 'BUYER');  -- William Lopez as buyer
INSERT INTO SALE_PARTICIPANT (sale_id, participant_id, participant_type, role) VALUES (12, 7, 'DEALER', 'SELLER');  -- City Motors as seller