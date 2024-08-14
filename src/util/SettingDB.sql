##################################################
#
#   LCW DATABASE BASIC SETTING
#
##################################################

DROP DATABASE IF EXISTS LCWMSDB;

CREATE DATABASE LCWMSDB DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

USE LCWMSDB;

CREATE TABLE user
(
    user_id         varchar(20)  NOT NULL,
    password        varchar(30)  NOT NULL,
    name            varchar(30)  NOT NULL,
    birth           Date         NOT NULL,
    email           varchar(30)  NOT NULL,
    tel             varchar(15)  NOT NULL,
    role            varchar(20) NOT NULL,
    status          varchar(20) NOT NULL,
    address         varchar(100) NOT NULL,
    business_name   varchar(50)  NULL,
    business_number varchar(30)  NULL,
    primary key (user_id)
);

CREATE TABLE board
(
    board_id        int unsigned NOT NULL AUTO_INCREMENT,
    title           varchar(50)  NOT NULL,
    contents        varchar(255) NULL,
    board_date      Date         NOT NULL,
    user_id         varchar(20)  NULL,
    answer_board_id int unsigned NULL,
    PRIMARY KEY (board_id)
);

CREATE TABLE product
(
    product_id        int unsigned NOT NULL AUTO_INCREMENT,
    product_brand     varchar(30)  NOT NULL,
    product_name      varchar(30)  NOT NULL,
    area_per_product  int unsigned NULL,
    small_category_id int unsigned NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE small_category
(
    small_category_id   int unsigned NOT NULL AUTO_INCREMENT,
    middle_category_id  int unsigned NOT NULL,
    small_category_name varchar(30)  NOT NULL,
    PRIMARY KEY (small_category_id)
);

CREATE TABLE stock
(
    user_id          varchar(20)  NOT NULL,
    storage_id       int unsigned NOT NULL,
    product_id       int unsigned NOT NULL,
    storage_quantity int unsigned NOT NULL,
    PRIMARY KEY (user_id,storage_id,product_id)
);

CREATE TABLE storage
(
    storage_id     int unsigned NOT NULL AUTO_INCREMENT,
    storage_name   varchar(30)  NOT NULL,
    address        varchar(30)  NOT NULL,
    address_detail varchar(50)  NOT NULL,
    zipcode        int unsigned NOT NULL,
    storage_area   int unsigned NOT NULL,
    manager_id     varchar(20)  NOT NULL,
    PRIMARY KEY (storage_id)
);

CREATE TABLE profit_history
(
    profit_history_id  int unsigned NOT NULL AUTO_INCREMENT,
    profit_date        Date         NOT NULL,
    profit_cost        int unsigned NOT NULL,
    profit_category_id int unsigned NOT NULL,
    storage_id         int unsigned NOT NULL,
    PRIMARY KEY (profit_history_id)
);

CREATE TABLE profit_category
(
    profit_category_id int unsigned NOT NULL AUTO_INCREMENT,
    profit_type        varchar(30)  NOT NULL,
    PRIMARY KEY (profit_category_id)
);

CREATE TABLE stocking_request
(
    stocking_request_id int unsigned NOT NULL AUTO_INCREMENT,
    user_id             varchar(20)  NOT NULL,
    product_id          int unsigned NOT NULL,
    storage_id          int unsigned NOT NULL,
    request_id          Date         NOT NULL,
    approved_date       Date         NULL,
    complete_date       Date         NULL,
    request_quantity    int unsigned NOT NULL,
    request_comment     varchar(50)  NULL,
    PRIMARY KEY (stocking_request_id)
);

CREATE TABLE dispatch
(
    waybill_id int unsigned NOT NULL,
    car_number varchar(15)  NOT NULL,
    PRIMARY KEY (waybill_id, car_number)
);

CREATE TABLE delivery_request
(
    delivery_request_id int unsigned NOT NULL AUTO_INCREMENT,
    user_id             varchar(20)  NOT NULL,
    product_id          int unsigned NOT NULL,
    request_date        Date         NOT NULL,
    approved_date       Date         NULL,
    complete_date       Date         NULL,
    delivery_address    varchar(50)  NOT NULL,
    address_detail      varchar(50)  NOT NULL,
    zipcode             int unsigned NOT NULL,
    request_quantity    int unsigned NOT NULL,
    request_comment     varchar(100) NULL,
    PRIMARY KEY (delivery_request_id)
);

CREATE TABLE contract
(
    contract_id     int unsigned NOT NULL AUTO_INCREMENT,
    user_id         varchar(20)  NOT NULL,
    contract_area   int unsigned NOT NULL,
    contract_cost   int unsigned NOT NULL,
    contract_date   Date         NOT NULL,
    expiration_date Date         NOT NULL,
    PRIMARY KEY (contract_id, user_id)
);

CREATE TABLE loss_history
(
    loss_history_id  int unsigned NOT NULL AUTO_INCREMENT,
    loss_date        Date         NOT NULL,
    loss_cost        int unsigned NOT NULL,
    storage_id       int unsigned NOT NULL,
    loss_category_id int unsigned NOT NULL,
    PRIMARY KEY (loss_history_id)
);

CREATE TABLE loss_category
(
    loss_category_id int unsigned NOT NULL AUTO_INCREMENT,
    loss_type        varchar(30)  NOT NULL,
    PRIMARY KEY (loss_category_id)
);

CREATE TABLE car
(
    car_number varchar(15)  NOT NULL,
    car_type   varchar(20)  NULL,
    max_load   int unsigned NOT NULL,
    PRIMARY KEY (car_number)
);

CREATE TABLE major_category
(
    major_category_id   int unsigned NOT NULL AUTO_INCREMENT,
    major_category_name varchar(30)  NOT NULL,
    PRIMARY KEY (major_category_id)
);

CREATE TABLE middle_category
(
    middle_category_id   int unsigned NOT NULL AUTO_INCREMENT,
    major_category_id    int unsigned NOT NULL,
    middle_category_name varchar(30)  NOT NULL,
    PRIMARY KEY (middle_category_id)
);

CREATE TABLE login_history
(
    login_history_id int unsigned NOT NULL AUTO_INCREMENT,
    user_id          varchar(20)  NOT NULL,
    login_time       Date         NOT NULL,
    PRIMARY KEY (login_history_id)
);

CREATE TABLE stock_history
(
    stock_history_id int unsigned NOT NULL AUTO_INCREMENT,
    user_id          varchar(20)  NOT NULL,
    storage_id       int unsigned NOT NULL,
    product_id       int unsigned NOT NULL,
    manager_id       varchar(20)  NOT NULL,
    stock_time       Date         NOT NULL,
    stock_quantity   int unsigned NOT NULL,
    stock_variance   int          NOT NULL,
    PRIMARY KEY (stock_history_id)
);

CREATE TABLE notice
(
    notice_id   int unsigned NOT NULL AUTO_INCREMENT,
    title       varchar(50)  NOT NULL,
    contents    varchar(255) NULL,
    notice_date Date         NOT NULL,
    user_id     varchar(20)  NULL,
    PRIMARY KEY (notice_id)
);

CREATE TABLE answer_board
(
    answer_board_id int unsigned NOT NULL AUTO_INCREMENT,
    title           varchar(50)  NOT NULL,
    contents        varchar(255) NULL,
    board_date      Date         NOT NULL,
    user_id         varchar(20)  NULL,
    PRIMARY KEY (answer_board_id)
);

CREATE TABLE waybill
(
    waybill_id            int unsigned NOT NULL AUTO_INCREMENT,
    delivery_request_id   int unsigned NOT NULL,
    depart_date           Date         NOT NULL,
    product_id            int unsigned NOT NULL,
    product_name          varchar(30)  NOT NULL,
    delivery_quantity     int unsigned NOT NULL,
    business_name         varchar(50)  NOT NULL,
    business_tel          varchar(15)  NOT NULL,
    start_address         varchar(100) NOT NULL,
    arrive_address        varchar(50)  NOT NULL,
    arrive_address_detail varchar(50)  NOT NULL,
    request_comment       varchar(100) NOT NULL,
    PRIMARY KEY (waybill_id, delivery_request_id)
);

ALTER TABLE stock
    ADD CONSTRAINT FK_user_TO_stock_1 FOREIGN KEY (
                                                   user_id
        )
        REFERENCES user (
                         user_id
            );

ALTER TABLE stock
    ADD CONSTRAINT FK_storage_TO_stock_1 FOREIGN KEY (
                                                      storage_id
        )
        REFERENCES storage (
                            storage_id
            );

ALTER TABLE stock
    ADD CONSTRAINT FK_product_TO_stock_1 FOREIGN KEY (
                                                      product_id
        )
        REFERENCES product (
                            product_id
            );

ALTER TABLE dispatch
    ADD CONSTRAINT FK_waybill_TO_dispatch_1 FOREIGN KEY (
                                                         waybill_id
        )
        REFERENCES waybill (
                            waybill_id
            );

ALTER TABLE dispatch
    ADD CONSTRAINT FK_car_TO_dispatch_1 FOREIGN KEY (
                                                     car_number
        )
        REFERENCES car (
                        car_number
            );

ALTER TABLE contract
    ADD CONSTRAINT FK_user_TO_contract_1 FOREIGN KEY (
                                                      user_id
        )
        REFERENCES user (
                         user_id
            );

ALTER TABLE waybill
    ADD CONSTRAINT FK_delivery_request_TO_waybill_1 FOREIGN KEY (
                                                                 delivery_request_id
        )
        REFERENCES delivery_request (
                                     delivery_request_id
            );

