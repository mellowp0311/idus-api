-- (1) 사용자
create table wow.idus_user
(
    user_seq bigint unsigned auto_increment comment '사용자 시퀀스' primary key,
    user_id varchar(100) not null comment '사용자 아이디(이메일)',
    user_password varchar(30) not null comment '사용자 패스워드',
    user_name varchar(20) not null comment '사용자 이름',
    nick_name varchar(30) not null comment '사용자 닉네임(별명)',
    phone_no varchar(20) not null comment '사용자 연락처(전화번호)',
    user_type tinyint default 0 not null comment '사용자 유형(0: 일반, 1:관리자, 2:기타)',
    user_gender char null comment '사용자 성별 (F:여성, M:남성, E:기타, N: 미입력)',
    user_status tinyint default 0 not null comment '사용자 상태 (0:정상 1:회원탈퇴 2:블랙 3:정지 4:관리자삭제,5:기타)',
    user_jwt varchar(1000) null comment '사용자 JWT 토큰',
    reg_date datetime default current_timestamp() not null comment '등록일',
    upd_date datetime default current_timestamp() not null comment '수정일',
    constraint idx_idus_user_01 unique (user_id)
)
comment 'IDUS 사용자 정보';

-- (2) 주문
create table wow.idus_order
(
    order_no varchar(12) not null primary key,
    product_seq bigint not null comment '상품번호',
    buyer_seq bigint not null comment '구매자 시퀀스',
    pay_date datetime default current_timestamp() not null comment '결제일시',
    cancel_date datetime null comment '취소일시',
    confirm_date datetime null comment '구매확정일시',
    upd_date datetime null comment '수정일'
)
comment '주문 정보';
create index idx_order_01 on wow.idus_order (buyer_seq);

-- (3) 상품
create table wow.idus_product
(
    product_seq bigint unsigned auto_increment comment '상품 시퀀스' primary key,
    product_title varchar(100) not null comment '상품명',
    product_price bigint(10) not null comment '상품 가격',
    reg_date datetime default current_timestamp() not null comment '등록일',
    upd_date datetime default current_timestamp() not null comment '수정일'
)
comment '상품 정보';

