INSERT INTO pay.membership (membership_id, address, aggregate_identifier, email, is_valid, name)
VALUES (1, '서울시 강남구 1번지', 'agg_id_001', 'user1@example.com', 1, '홍길동'),
       (2, '부산시 해운대구 2번지', 'agg_id_002', 'user2@example.com', 1, '이순신'),
       (3, '대구시 중구 3번지', 'agg_id_003', 'user3@example.com', 1, '김유신'),
       (4, '인천시 남동구 4번지', 'agg_id_004', 'user4@example.com', 0, '강감찬'),
       (5, '광주시 북구 5번지', 'agg_id_005', 'user5@example.com', 1, '유관순'),
       (6, '대전시 유성구 6번지', 'agg_id_006', 'user6@example.com', 1, '세종대왕'),
       (7, '울산시 남구 7번지', 'agg_id_007', 'user7@example.com', 0, '이황'),
       (8, '경기도 수원시 8번지', 'agg_id_008', 'user8@example.com', 1, '장보고'),
       (9, '경기도 성남시 9번지', 'agg_id_009', 'user9@example.com', 1, '신사임당'),
       (10, '서울시 마포구 10번지', 'agg_id_010', 'user10@example.com', 0, '이율곡');
INSERT INTO pay.member_money (member_money_id, balance, membership_id)
VALUES (1, 10000, 1),
       (2, 20000, 2),
       (3, 15000, 3),
       (4, 30000, 4),
       (5, 25000, 5),
       (6, 50000, 6),
       (7, 12000, 7),
       (8, 8000, 8),
       (9, 27000, 9),
       (10, 18000, 10);
