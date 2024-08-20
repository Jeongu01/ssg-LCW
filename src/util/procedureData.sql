DELIMITER $$
CREATE PROCEDURE delivery_request_proc(IN manager_id varchar(20), IN waybill_id int)
BEGIN
   DECLARE _USER_ID VARCHAR(20);
    DECLARE _storage_id int;
    DECLARE _product_id int;
    -- declare _manager_id varchar(20);
   declare _now_date date;
    declare _stock_quantity int;
    declare _stock_variance int;
    declare _delivery_req_id int;


    set _now_date = now();

select delivery_request_id into _delivery_req_id from waybill w where w.waybill_id = waybill_id;

update delivery_request set complete_date = _now_date where delivery_request_id = _delivery_req_id;

select d.user_id, d.product_id, d.product_id, d.request_quantity
into _USER_ID, _product_id, _storage_id, _stock_variance
from delivery_request d
where d.delivery_request_id = _delivery_req_id;

set _stock_variance = _stock_variance * (-1);

    #stock 테이블 재고 변경
update stock set storage_quantity = storage_quantity + _stock_variance
where storage_id = _storage_id
  and user_id = _user_id
  and product_id = _product_id;

#stock 로그 생성
    insert into stock_history
    values(null, _user_id, _storage_id, _product_id, manager_id, _now_date,
      (select storage_quantity from stock where storage_id = _storage_id
      and user_id = _user_id
      and product_id = _product_id),
    _stock_variance);

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE stocking_request_proc(IN manager_id varchar(20), IN stocking_request_id int)
BEGIN
    DECLARE _USER_ID VARCHAR(20);
    DECLARE _storage_id int;
    DECLARE _product_id int;
    -- declare _manager_id varchar(20);
    declare _now_date date;
    declare _stock_quantity int;
    declare _stock_variance int;

    set _now_date = now();

    update stocking_request s set complete_date = _now_date where s.stocking_request_id = stocking_request_id;

    select s.user_id, s.product_id, s.product_id, s.request_quantity
    into _USER_ID, _product_id, _storage_id, _stock_variance
    from stocking_request s
    where s.stocking_request_id = stocking_request_id;

    #stock 테이블 재고 변경
    update stock set storage_quantity = storage_quantity + _stock_variance
    where storage_id = _storage_id
      and user_id = _user_id
      and product_id = _product_id;

    #stock 로그 생성
    insert into stock_history
    values(null, _user_id, _storage_id, _product_id, manager_id, _now_date,
           (select storage_quantity from stock where storage_id = _storage_id
                                                 and user_id = _user_id
                                                 and product_id = _product_id),
           _stock_variance);

END $$
DELIMITER ;