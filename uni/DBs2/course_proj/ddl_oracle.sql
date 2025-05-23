

CREATE TABLE MYSCHEMA."Shop" (
	id number(10) GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1) NOT NULL,
	description varchar2(255 char) NULL,
	title varchar2(255 char) NULL,
	CONSTRAINT "Shop_pkey" PRIMARY KEY (id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE MYSCHEMA."Shop_seq" START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER MYSCHEMA."Shop_seq_tr"
 BEFORE INSERT ON MYSCHEMA."Shop" FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT MYSCHEMA."Shop_seq".NEXTVAL INTO :NEW.id FROM DUAL;
END;



CREATE TABLE MYSCHEMA."Warehouse" (
	id number(10) GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1) NOT NULL,
	address varchar2(255 char) NULL,
	CONSTRAINT "Warehouse_pkey" PRIMARY KEY (id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE MYSCHEMA."Warehouse_seq" START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER MYSCHEMA."Warehouse_seq_tr"
 BEFORE INSERT ON MYSCHEMA."Warehouse" FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT MYSCHEMA."Warehouse_seq".NEXTVAL INTO :NEW.id FROM DUAL;
END;



CREATE TABLE MYSCHEMA."User" (
	"birthDate" date NULL,
	id number(10) GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1) NOT NULL,
	"isAdmin" char(1) NULL,
	shop_id number(10) NULL,
	warehouse_id number(10) NULL,
	"DTYPE" varchar2(31 char) NOT NULL,
	"billingAddress" varchar2(255 char) NULL,
	"cardNumber" varchar2(255 char) NULL,
	login varchar2(255 char) NULL,
	"name" varchar2(255 char) NULL,
	"password" varchar2(255 char) NULL,
	"shippingAddress" varchar2(255 char) NULL,
	surname varchar2(255 char) NULL,
	CONSTRAINT "User_pkey" PRIMARY KEY (id),
	CONSTRAINT "FK2m63wygyjejunjawr2f5vicid" FOREIGN KEY (shop_id) REFERENCES MYSCHEMA."Shop"(id),
	CONSTRAINT "FKehs3bmxqyac4pjlryy7eudeag" FOREIGN KEY (warehouse_id) REFERENCES MYSCHEMA."Warehouse"(id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE MYSCHEMA."User_seq" START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER MYSCHEMA."User_seq_tr"
 BEFORE INSERT ON MYSCHEMA."User" FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT MYSCHEMA."User_seq".NEXTVAL INTO :NEW.id FROM DUAL;
END;



CREATE TABLE MYSCHEMA."Cart" (
	customer_id number(10) NULL,
	id number(10) GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1) NOT NULL,
	manager_id number(10) NULL,
	CONSTRAINT "Cart_pkey" PRIMARY KEY (id),
	CONSTRAINT "FK9ppk0usn74jlqusd4dxc2fh5u" FOREIGN KEY (manager_id) REFERENCES MYSCHEMA."User"(id),
	CONSTRAINT "FKdny3cm2c3ie03va9mvid9drjr" FOREIGN KEY (customer_id) REFERENCES MYSCHEMA."User"(id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE MYSCHEMA."Cart_seq" START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER MYSCHEMA."Cart_seq_tr"
 BEFORE INSERT ON MYSCHEMA."Cart" FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT MYSCHEMA."Cart_seq".NEXTVAL INTO :NEW.id FROM DUAL;
END;




CREATE TABLE MYSCHEMA."Product" (
	cart_id number(10) NULL,
	id number(10) GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1) NOT NULL,
	price binary_float NOT NULL,
	quantity number(10) NOT NULL,
	shop_id number(10) NULL,
	warehouse_id number(10) NULL,
	description varchar2(255 char) NULL,
	title varchar2(255 char) NULL,
	CONSTRAINT "Product_pkey" PRIMARY KEY (id),
	CONSTRAINT "FK9huqlgox8wqmseg52nd0t6kd2" FOREIGN KEY (cart_id) REFERENCES MYSCHEMA."Cart"(id),
	CONSTRAINT "FKbo85e8vrmk72ujd6yo1atbtqm" FOREIGN KEY (warehouse_id) REFERENCES MYSCHEMA."Warehouse"(id),
	CONSTRAINT "FKmrnf6ubkbldnxh3mh8wwf2f7b" FOREIGN KEY (shop_id) REFERENCES MYSCHEMA."Shop"(id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE MYSCHEMA."Product_seq" START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER MYSCHEMA."Product_seq_tr"
 BEFORE INSERT ON MYSCHEMA."Product" FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT MYSCHEMA."Product_seq".NEXTVAL INTO :NEW.id FROM DUAL;
END;





CREATE TABLE MYSCHEMA."Comment" (
	"commentOwner_id" number(10) NULL,
	id number(10) GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1) NOT NULL,
	"parentComment_id" number(10) NULL,
	rating binary_float NOT NULL,
	"whichProductCommented_id" number(10) NULL,
	"commentBody" varchar2(255 char) NULL,
	"commentTitle" varchar2(255 char) NULL,
	CONSTRAINT "Comment_pkey" PRIMARY KEY (id),
	CONSTRAINT "FK6p9no5puf4g8mi983isrpmwtq" FOREIGN KEY ("whichProductCommented_id") REFERENCES MYSCHEMA."Product"(id),
	CONSTRAINT "FKhqp8s1t3voefj0pvb07ytseil" FOREIGN KEY ("parentComment_id") REFERENCES MYSCHEMA."Comment"(id),
	CONSTRAINT "FKmvq25icouaiu0a8bu09ddesf7" FOREIGN KEY ("commentOwner_id") REFERENCES MYSCHEMA."User"(id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE MYSCHEMA."Comment_seq" START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER MYSCHEMA."Comment_seq_tr"
 BEFORE INSERT ON MYSCHEMA."Comment" FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT MYSCHEMA."Comment_seq".NEXTVAL INTO :NEW.id FROM DUAL;
END;
