USE mysqldb;

-- Create database
CREATE DATABASE IF NOT EXISTS mysqldb;
-- Switch to the newly created database
USE mysqldb;

SELECT DATABASE();
select user();

CREATE TABLE IF NOT EXISTS mb_member(
    member_id INT(11) NOT NULL AUTO_INCREMENT,
    member_nm VARCHAR(30) NOT NULL,
    cell_no VARCHAR(30) NOT NULL,
    skin_type VARCHAR(20),
    age varchar(3) NOT NULL,
    entry_date DATETIME,
    addr varchar(100),
    addr_detail varchar(200),
    CONSTRAINT MB_MEMBER_PK PRIMARY KEY(member_id)
);

select * from mb_member;

CREATE TABLE IF NOT EXISTS pv_goods(
       goods_no INT NOT NULL AUTO_INCREMENT,
       category VARCHAR(30) NOT NULL,
       goods_nm VARCHAR(30) NOT NULL,
       sale_price INT not null,
       market_price INT not null,
       supply_price INT not null,
       vendor_id INT not null,
       stock_qty INT not null,
       brand_nm VARCHAR(20) not null,
       image varchar(200),
       add_image varchar(200),
       sale_start_dtime DATETIME default now(),
       sale_end_dtime DATETIME default (DATE_ADD(now(), INTERVAL 1 DAY) - INTERVAL 1 SECOND),
       insert_dtime DATETIME default now(),
       update_dtime DATETIME DEFAULT now(),
       CONSTRAINT PV_GOODS_PK PRIMARY KEY(goods_no)
);

select (DATE_ADD(now(), INTERVAL 1 DAY) - INTERVAL 1 SECOND);

CREATE TABLE IF NOT EXISTS pv_item(
  item_no INT(11) NOT NULL AUTO_INCREMENT,
  goods_no INT(11) NOT NULL,
  item_nm VARCHAR(30) NOT NULL,
  item_qty INT,
  insert_dtime DATETIME default now(),
  update_dtime DATETIME DEFAULT now(),
  CONSTRAINT PV_ITEM_PK PRIMARY KEY(item_no),
  foreign key (goods_no) references pv_goods(goods_no)
);

CREATE TABLE IF NOT EXISTS pv_vend(
    vendor_id INT NOT NULL AUTO_INCREMENT,
    vendor_nm VARCHAR(30) NOT NULL,
    post_no VARCHAR(20) NOT NULL,
    addr VARCHAR(100) NOT NULL,
    addr_detail VARCHAR(200) NOT NULL,
    biz_no VARCHAR(30) NOT NULL,
    insert_dtime DATETIME DEFAULT now(),
    update_dtime DATETIME DEFAULT now(),
CONSTRAINT PV_VEND_PK PRIMARY KEY(vendor_id)
);


commit;
select * from pv_vend;
select * from pv_goods;
select * from pv_item;
select * from pv_goods where goods_no =1;

/* 주문자 관리 기본 정보 마스터 */
CREATE TABLE IF NOT EXISTS od_order(
    order_no INT(11) NOT NULL AUTO_INCREMENT,
    member_id INT(11) NOT NULL,
    order_dtime DATETIME DEFAULT now(),
    order_name VARCHAR(30) NOT NULL,
    cell_no VARCHAR(30) NOT NULL,
    insert_dtime DATETIME DEFAULT now(),
    update_dtime DATETIME DEFAULT now(),
    CONSTRAINT od_ord_master_pk PRIMARY KEY(order_no)
);

/* 상품단위 주문 정보 */
CREATE TABLE IF NOT EXISTS od_order_dtl(
    order_no INT(11) NOT NULL AUTO_INCREMENT,
    order_step VARCHAR(4) NOT NULL,  /* 단계 주문/취소/반품/ */
    order_status VARCHAR(4) NOT NULL,  /*주문상태 결제대기 결제중 */
    goods_no INT(11) NOT NULL,
    goods_nm VARCHAR(30) NOT NULL,
    item_no INT(11) NOT NULL,
    item_nm VARCHAR(30) NOT NULL,
    ord_qty INT NOT NULL,          /* 주문 수량 */
    sale_price INT NOT NULL,       /* 상품 가격 */
    market_price INT NOT NULL,     /* 추후에 상품에 시장가 공급가가 달라질 수 있어서 주문 당시 꺼를 가지고 있어야겠다*/
    supply_price INT NOT NULL,     /* 공급가 */
    discount_amt INT NOT NULL,     /* 할인 총액*/
    total_pay_amt INT NOT NULL,    /* 주문수량 * 상품가격 - 할인총액*/
    vendor_id INT NOT NULL,
    insert_dtime DATETIME DEFAULT now(),
    update_dtime DATETIME DEFAULT now(),
    CONSTRAINT od_order_dtl_pk PRIMARY KEY(order_no),
    foreign key (order_no) references od_order(order_no)
);
/*
select * from od_order;
select * from od_order_dtl;
select * from od_order_deli;
select * from od_order_pay;
select * from od_order_goods;
select * from od_order_bene;
drop table pv_item;
drop table pv_goods;commit;
 */
select count(*) over(), a.* from pv_goods a ;
select  a.goods_nm ,b.item_nm,a.* from pv_goods a, pv_item b where a.goods_no = b.goods_no ;
