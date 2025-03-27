/* ユーザーマスタ */
INSERT INTO m_user (
    user_id
  , password
  , user_name
  , birthday
  , age
  , gender
  , profile
  , department_id
  , role
  , ins_user_id
  , ins_date
  , upd_user_id
  , upd_date
) VALUES
('system1@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'システム管理者', '2000-01-01', 21, 1, 'システム管理者です', 1, 'ROLE_ADMIN', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
,('system2@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'システム管理者2', '2000-01-01', 31, 2, 'ユーザーです', 1, 'ROLE_ADMIN', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user1@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー1', '2000-01-01', 21, 2, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user2@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー2', '1980-04-01', 41, 2, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user3@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー3', '1990-10-10', 31, 1, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user4@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー4', '1997-01-01', 25, 1, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user5@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー5', '1984-11-11', 35, 2, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user6@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー6', '1984-11-01', 35, 2, 'ユーザーです', 3, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user7@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー7', '1995-06-21', 28, 2, 'ユーザーです', 3, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user8@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー8', '2000-01-01', 55, 1, 'ユーザーです', 3, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user9@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー9', '2000-01-01', 50, 1, 'ユーザーです', 3, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user10@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー10', '2000-01-01', 29, 1, 'ユーザーです', 3, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user11@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー11', '2000-01-01', 33, 2, 'ユーザーです', 3, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user12@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー12', '2000-01-01', 34, 2, 'ユーザーです', 3, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user13@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー13', '2000-01-01', 49, 1, 'ユーザーです', 1, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user14@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー14', '2000-01-01', 44, 1, 'ユーザーです', 1, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user15@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー15', '2000-01-01', 38, 1, 'ユーザーです', 1, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user16@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー16', '2000-01-01', 25, 1, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user17@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー17', '2000-01-01', 30, 1, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user18@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー18', '2000-01-01', 50, 1, 'ユーザーです', 2, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
, ('user19@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー19', '2000-01-01', 43, 2, 'ユーザーです', 1, 'ROLE_GENERAL', 'system1@co.jp', CURRENT_TIMESTAMP(), 'system1@co.jp', CURRENT_TIMESTAMP())
;
