drop table if exists account;
drop table if exists bank;
drop table if exists contract;
drop table if exists device_token;
drop table if exists notification;
drop table if exists repayment;
drop table if exists transaction;
drop table if exists user;

create table account (
    account_id bigint not null auto_increment,
    account_number varchar(255) not null,
    balance bigint not null,
    bank_code varchar(255) not null,
    created_time datetime(6),
    user_id bigint not null,
    primary key (account_id)
) engine=InnoDB;

create table bank (
    bank_id bigint not null auto_increment,
    bank_code varchar(3) not null,
    bank_name varchar(255),
    primary key (bank_id)
) engine=InnoDB;

create table contract (
    contract_id bigint not null auto_increment,
    amount bigint not null,
    amount_in_korean varchar(255) not null,
    created_time datetime(6),
    due_date datetime(6) not null,
    lessee_id bigint,
    lessor_id bigint,
    pdf_path varchar(255),
    rate float,
    start_date datetime(6) not null,
    status varchar(255) not null,
    use_auto_transfer varchar(255),
    primary key (contract_id)
) engine=InnoDB;

create table device_token (
    device_token_id bigint not null auto_increment,
    device_token varchar(255) not null,
    user_id bigint not null,
    primary key (device_token_id)
) engine=InnoDB;

create table notification (
    notification_id bigint not null auto_increment,
    contract_id bigint not null,
    created_time datetime(6),
    notification_type varchar(255) not null,
    user_id bigint not null,
    primary key (notification_id)
) engine=InnoDB;

create table repayment (
    repayment_id bigint not null auto_increment,
    amount bigint not null,
    balance bigint not null,
    contract_id bigint not null,
    repayment_count integer not null,
    repayment_date datetime(6) not null,
    status varchar(255) not null,
    transaction_id bigint,
    primary key (repayment_id)
) engine=InnoDB;
  
create table transaction (
    transaction_id bigint not null auto_increment,
    amount bigint not null,
    receiver_account_number varchar(255) not null,
    sender_account_number varchar(255) not null,
    transaction_time datetime(6),
    primary key (transaction_id)
) engine=InnoDB;

create table user (
    user_id bigint not null auto_increment,
    address varchar(100),
    created_time datetime(6),
    issue_date datetime(6),
    name varchar(20),
    password varchar(6),
    phone_number varchar(11),
    role varchar(20),
    rrn varchar(13),
    signature_url varchar(255),
    social_id varchar(512),
    social_type varchar(20),
    wallet_address varchar(512),
    wallet_key varchar(255),
    withdrawal_time datetime(6),
    primary key (user_id)
) engine=InnoDB;

alter table account 
    add constraint UK_66gkcp94endmotfwb8r4ocxm9 unique (account_number);

alter table bank 
    add constraint UK_r03df0r93i07xw7u6cm066ub7 unique (bank_code);

alter table user 
    add constraint UK_m3tiv2iugdximo00e50thjcbh unique (social_id);

alter table user 
    add constraint UK_9ga8tj7f3cpyntf1xebugca74 unique (wallet_address);

insert into bank (bank_code, bank_name)
    values
    ('001', 'HUICKBANK');