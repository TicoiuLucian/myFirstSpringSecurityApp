insert into my_role(name) values ('ROLE_USER');
insert into my_role(name) values ('ROLE_ADMIN');

--password = password1
insert into customer (username, mail, password) values ('test1', 'user@mail.com' ,'$2a$12$t/RMRiFOsahmhdcEHvY4F.tlpKJ2ER/7vPV.RadHakkQqt50sWPcS');

insert into customer_roles(users_id, roles_id) values (1,1);
insert into customer_roles(users_id, roles_id) values (1,2);