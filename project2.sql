show databases;
use project2;
show tables;

select * from dept;
insert into dept(d_depth, d_name, parent_d_no) values(1,'CP',1);
insert into dept(d_depth, d_name, parent_d_no) values(2,'VP',2);
insert into dept(d_depth, d_name, parent_d_no) values(3,'GA',3);
insert into dept(d_depth, d_name, parent_d_no) values(4,'MANAGER',4);
insert into dept(d_depth, d_name, parent_d_no) values(5,'MEMBER',5);

select * from chatmember;
insert into chatmember(c_addr, c_email, c_hire_date, c_name, c_phone, c_position, dept_d_no)
values('주소1','m1@gmail.com',now(),'김이름','010-1111-1111','ST',1);
insert into chatmember(c_addr, c_email, c_hire_date, c_name, c_phone, c_position, dept_d_no)
values('주소2','m2@gmail.com',now(),'박이름','010-1111-1112','MF',2);
insert into chatmember(c_addr, c_email, c_hire_date, c_name, c_phone, c_position, dept_d_no)
values('주소3','m3@gmail.com',now(),'최이름','010-1111-1113','DF',3);
insert into chatmember(c_addr, c_email, c_hire_date, c_name, c_phone, c_position, dept_d_no)
values('주소4','m4@gmail.com',now(),'이이름','010-1111-1114','GK',4);

select * from answer;
insert into answer(content, keyword) values('안녕하세요. KickOff 챗봇입니다','안녕');
insert into answer(content, keyword) values('답변이 준비되지 않았습니다 ㅠ','기타');
insert into answer(content, keyword) values('문의하신 회원의 전화번호입니다.','전화');
insert into answer(content, keyword) values('문의하신 회원의 주소입니다.','주소');
insert into answer(content, keyword) values('문의하신 회원의 포지션입니다.','포지션');
insert into answer(content, keyword) values('문의하신 회원의 이메일입니다.','이메일');

select * from intention;
insert into intention(name, answer_no) values('안녕',1);
insert into intention(name, answer_no) values('기타',2);
insert into intention(name, answer_no) values('전화',3);
insert into intention(name, answer_no) values('주소',4);
insert into intention(name, answer_no) values('포지션',5);
insert into intention(name, answer_no) values('이메일',6);


select * from member;
select * from profile;
select * from attend;
select * from member_schedule;
desc member_schedule;
select * from team_schedule;
select * from account;
select * from board;
select * from notice;
select * from approval;

delete from approval where approver_id = 3;