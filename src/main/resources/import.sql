-- 테스트 상품 데이터 생성
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('가마야 프리미엄 수제어묵', 7500, now(), now());
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('수제 진액팩', 15000, now(), now());
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('꾸덕한 크럼블바 4', 20000, now(), now());
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('상견례 추석선물', 69900, now(), now());
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('엣지 태슬 롱 스카프', 12500, now(), now());
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('커스텀 구름 키링', 10000, now(), now());
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('청무화과 진액스틱 ', 76000, now(), now());
insert into idus_product (product_title, product_price, reg_date, upd_date) values ('블루밍 나비 압화 케이스', 5000, now(), now());

-- 테스트 주문 데이터 생성 (사용자 1번)
insert into idus_order (order_no, product_seq, buyer_seq, cancel_date, confirm_date, upd_date) values ('K123456789CD', 1, 1, null, null, null);
insert into idus_order (order_no, product_seq, buyer_seq, cancel_date, confirm_date, upd_date) values ('K023456789CD', 2, 1, null, null, null);
insert into idus_order (order_no, product_seq, buyer_seq, cancel_date, confirm_date, upd_date) values ('K024456789CD', 3, 1, null, null, null);