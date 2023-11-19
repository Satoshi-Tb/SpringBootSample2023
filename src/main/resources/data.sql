/* 従業員初期データ */
INSERT INTO employee (id, name, age)
VALUES('1', 'Tom', 30);

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
) VALUES
('system1@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'システム管理者', '2000-01-01', 21, 1, 'システム管理者です', 1, 'ROLE_ADMIN')
, ('user1@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー1', '2000-01-01', 21, 2, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user2@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー2', '2000-01-01', 41, 2, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user3@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー3', '2000-01-01', 31, 1, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user4@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー4', '2000-01-01', 25, 1, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user5@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー5', '2000-01-01', 35, 2, 'ユーザーです', 2, 'ROLE_GENERAL')
, ('user6@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー6', '2000-01-01', 35, 2, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user7@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー7', '2000-01-01', 28, 2, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user8@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー8', '2000-01-01', 55, 1, 'ユーザーです', 3, 'ROLE_GENERAL')
, ('user9@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'ユーザー9', '2000-01-01', 50, 1, 'ユーザーです', 3, 'ROLE_GENERAL')
,('system2@co.jp', '$2a$10$rJyapIrvsHARwCNgporWLO6QIKXXezOpRrdb..7X0ea0VwZ5IldSy', 'システム管理者2', '2000-01-01', 31, 2, 'ユーザーです', 1, 'ROLE_ADMIN')
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