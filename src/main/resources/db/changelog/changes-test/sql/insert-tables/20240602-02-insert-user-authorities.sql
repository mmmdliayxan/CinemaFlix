insert into cinema_database.user_authorities(user_id, authority_name)
VALUES ((select id from cinema_database.users where username='Admin'),'ADMIN');