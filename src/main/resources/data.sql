/* 従業員初期データ */
INSERT INTO employee (id, name, age)
VALUES('1', 'Tom', 30);

/* ユーザーマスタ */
/* password = password */
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
) VALUES
('system1@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'システム管理者', '2000-01-01', 21, 1, 'システム管理者です', 1, 'ROLE_ADMIN')
,('system2@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'システム管理者2', '2000-01-01', 31, 2, 'ユーザーです', 1, 'ROLE_ADMIN')
, ('user1@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー1', '2000-01-01', 21, 2, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user2@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー2', '1980-04-01', 41, 2, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user3@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー3', '1990-10-10', 31, 1, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user4@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー4', '1997-01-01', 25, 1, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user5@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー5', '1984-11-11', 35, 2, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user6@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー6', '1984-11-01', 35, 2, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user7@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー7', '1995-06-21', 28, 2, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user8@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー8', '2000-01-01', 55, 1, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user9@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー9', '2000-01-01', 50, 1, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user10@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー10', '2000-01-01', 29, 1, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user11@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー11', '2000-01-01', 33, 2, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user12@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー12', '2000-01-01', 34, 2, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user13@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー13', '2000-01-01', 49, 1, 'ユーザーです', 1, 'ROLE_GENERAL')
, ('user14@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー14', '2000-01-01', 44, 1, 'ユーザーです', 1, 'ROLE_GENERAL')
, ('user15@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー15', '2000-01-01', 38, 1, 'ユーザーです', 1, 'ROLE_GENERAL')
, ('user16@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー16', '2000-01-01', 25, 1, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user17@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー17', '2000-01-01', 30, 1, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user18@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー18', '2000-01-01', 50, 1, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user19@co.jp', '$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG', 'ユーザー19', '2000-01-01', 43, 2, 'ユーザーです', 1, 'ROLE_GENERAL')
;

/* 部署マスタ */
INSERT INTO m_department (
    department_id
  , department_name
) VALUES
(1, 'システム管理部')
, (2, '営業部')
, (3, 'SI事業部')
;

/* 給料テーブル */
INSERT INTO t_salary (
    user_id
  , year_month
  , salary
) VALUES
('user@co.jp', '2020/11', 280000)
, ('user@co.jp', '2020/12', 290000)
, ('user@co.jp', '2021/01', 300000)
;

/* 汎用コード管理 */
INSERT INTO m_code (
    category
  , code
  , name
) VALUES
('gender', '1', '男性')
, ('gender', '2', '女性')
, ('gender', '3', 'その他')
;