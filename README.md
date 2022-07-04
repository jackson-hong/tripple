# Tripple

### 어플리케이션 작동 방법

😃 maven이 설치되어 있어야 합니다.

```bash
cd {application folder location}
mvn clean package
java -jar ./target/mileage-0.0.1-SNAPSHOT.jar
```

### DB 생성 DDL

```sql
CREATE DATABASE mileage default CHARACTER SET UTF8;
create user jackson@localhost identified by 'jackson12345';
GRANT ALL PRIVILEGES ON mileage.* TO jackson@localhost;

drop table IF EXISTS point_history;
drop table IF EXISTS photo_mst;
drop table IF EXISTS review_mst;
drop table IF EXISTS place_mst;
drop table IF EXISTS user_mst;

create table user_mst (
    user_id varchar(36) primary key,
    total_point int not null ,
    reg_id varchar(100) default 'anonymous',
    reg_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    mod_id varchar(100) default 'anonymous',
    mod_dtm TIMESTAMP default CURRENT_TIMESTAMP
);

create index idx_user_mst on user_mst(user_id);

create table place_mst (
	place_id varchar(36) primary key,
	reg_id varchar(100) default 'anonymous',
    reg_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    mod_id varchar(100) default 'anonymous',
    mod_dtm TIMESTAMP default CURRENT_TIMESTAMP
);

create index idx_place_mst on place_mst(place_id);

create table review_mst (
	review_id varchar(36) primary key,
	content varchar(600),
	use_yn varchar(1) not null,
	place_id varchar(36) not null,
	user_id varchar(36) not null,
	reg_id varchar(100) default 'anonymous',
    reg_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    mod_id varchar(100) default 'anonymous',
    mod_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    FOREIGN KEY (place_id) REFERENCES place_mst(place_id),
    FOREIGN KEY (user_id) REFERENCES user_mst(user_id)
);

create index idx_review_mst on review_mst(review_id);

create table photo_mst (
    photo_id varchar(36) primary key,
    use_yn varchar(1) not null,
    review_id varchar(36) not null,
    reg_id varchar(100) default 'anonymous',
    reg_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    mod_id varchar(100) default 'anonymous',
    mod_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES review_mst(review_id)
);

create index idx_photo_mst on photo_mst(photo_id);

create table point_history (
	point_history_id varchar(36) primary key,
	point_reason_type varchar(20) not null,
	action_type varchar(10) not null,
	point_amount int,
	user_id varchar(36) not null,
	review_id varchar(36) not null,
	reg_id varchar(100) default 'anonymous',
    reg_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    mod_id varchar(100) default 'anonymous',
    mod_dtm TIMESTAMP default CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id)
    REFERENCES user_mst(user_id),
    FOREIGN KEY (review_id)
    REFERENCES review_mst(review_id)
);

create index idx_point_history on point_history(point_history_id);
```