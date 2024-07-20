CREATE TABLE BOARD(
    SEQ LONG NOT NULL AUTO_INCREMENT,
    TITLE VARCHAR2(200),
    WRITER VARCHAR2(20),
    CONTENT VARCHAR2(2000),
    REGDATE TIMESTAMP DEFAULT NOW(),
    CNT NUMBER(5) DEFAULT 0,
    PRIMARY KEY(SEQ)
)

insert into board(title,writer,content) values('질문','홍길동','내용입니다')

alter table BOARD alter column SEQ  restart with 1
