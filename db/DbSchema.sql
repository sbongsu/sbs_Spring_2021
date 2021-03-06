# DB 생성
DROP DATABASE IF EXISTS sbs_s_2021_10;
CREATE DATABASE sbs_s_2021_10;
USE sbs_s_2021_10;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);


# 게시물 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED NOT NULL DEFAULT 3 COMMENT '권한레벨(3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL,
    `nickname` CHAR(20) NOT NULL,
    cellphoneNo CHAR(50) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전,1=탈퇴)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

# 회원 테스트 데이터 생성(관리자 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`authLevel` = 7,
`name` = '관리자',
`nickname` = '관리자',
cellphoneNo = '01012341234',
email = 'abcdef@gmail.com';

# 회원 테스트 데이터 생성(일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '사용자1',
`nickname` = '사용자1',
cellphoneNo = '01034341122',
email = 'fedcba@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '사용자2',
`nickname` = '사용자2',
cellphoneNo = '01098769876',
email = 'zxcvbnm@gmail.com';

# 게시물 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

# 기존 게시물의 작성자를 2번으로 수정
UPDATE article
SET memberId = 2
WHERE memberId = 0;

# Article/Member.nickname 조인 (extra__writerName)
#SELECT a.*, m.nickname as extra__writerName
#FROM article a
#LEFT JOIN `member` m
#ON a.memberId = m.id
#where a.id = 1;

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(20) NOT NULL UNIQUE COMMENT 'notice=(공지사항), free1=(자유게시판1), free2=(자유게시판2),...',
    `name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=삭제전,1=삭제)',
    delDate DATETIME COMMENT '삭제날짜'
);

#게시판 테이블에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

#1, 2번 게시물을 공지사항 게시물로 지정
UPDATE article
SET boardId = 1
WHERE id IN(1,2);
#3번 게시물을 자유게시판 게시물로 지정
UPDATE article
SET boardId = 2
WHERE id IN(3);

#기본 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유게시판';

#게시물 갯수 늘리기
/*
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 3 + 1), FLOOR(RAND() * 2 + 1), CONCAT('제목_',RAND()), CONCAT('내용_',RAND())
FROM article;

select count(*) from article;

*/

#SELECT CONCAT('%','하하','%');

#게시물 테이블에 hitCount 칼럼 추가
ALTER TABLE article ADD COLUMN hitCount INT(10)UNSIGNED NOT NULL DEFAULT 0;

# 리액션포인트  테이블
CREATE TABLE reactionPoint(
id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
memberId INT(10) UNSIGNED NOT NULL,
relTypeCode CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
`point` SMALLINT(2) NOT NULL
);

# 리액션포인트 테스트 데이터
# 1번 회원이 1번 article에 대해서 싫어요.
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 1번 회원이 2번 article에 대해서 좋아요.
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

# 2번 회원이 1번 article에 대해서 싫어요.
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 2번 회원이 2번 article에 대해서 좋아요.
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = 1;
# 3번 회원이 1번 article에 대해서 좋아요.
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;

SELECT *,
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
IFNULL(SUM(IF(RP.point > 0, RP.point,0)),0) AS extra__goodReactionPoint,
IFNULL(SUM(IF(RP.point < 0, RP.point,0)),0) AS extra__badReactionPoint
FROM(
SELECT a.*, m.nickname AS extra__writerName
FROM article a
LEFT JOIN `member` AS m
ON a.memberId = m.id
) AS a
LEFT JOIN reactionPoint AS RP
ON a.id = RP.relId
GROUP BY a.id;

#SELECT * FROM article;
#SELECT * FROM `member`;
#SELECT * FROM board;
SELECT * FROM reactionPoint;
# 각 게시물에서 사용자가 좋아요와 싫어요를 할 수 있는지 체크
/*
SELECT IFNULL(SUM(point),0) AS p
FROM reactionPoint AS RP
WHERE relTypeCode = 'article' AND
relId = 1 AND
RP.memberId = 1;
*/
#게시물 테이블에 goodReactionPoint 칼럼 추가
ALTER TABLE article ADD COLUMN goodReactionPoint INT(10)UNSIGNED NOT NULL DEFAULT 0;

#게시물 테이블에 badReactionPoint  칼럼 추가
ALTER TABLE article ADD COLUMN badReactionPoint  INT(10)UNSIGNED NOT NULL DEFAULT 0;

# 각 게시물 별, 좋아요, 싫어요 총합
/*
select RP.relId,
SUM(IF(RP.point > 0, RP.point, 0)) as goodReactionPoint,
SUM(IF(RP.point < 0, RP.point * -1, 0)) AS badReactionPoint
from reactionPoint AS RP
WHERE relTypeCode = 'article'
GROUP BY RP.relTypeCode, RP.relId
*/

# 기존 게시물의 goodReactionPoint 필드와 badReactionPoint 필드의 값 채우기
UPDATE article AS A
INNER JOIN (
    SELECT RP.relId,
    SUM(IF(RP.point > 0, RP.point, 0)) AS goodReactionPoint,
    SUM(IF(RP.point < 0, RP.point * -1, 0)) AS badReactionPoint
    FROM reactionPoint AS RP
    WHERE relTypeCode = 'article'
    GROUP BY RP.relTypeCode, RP.relId
) AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
A.badReactionPoint = RP_SUM.badReactionPoint;

# 댓글테이블생성
CREATE TABLE reply(
id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
memberId INT(10) UNSIGNED NOT NULL,
relTypeCode CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
`body` TEXT NOT NULL
);

#테스트 댓글 추가
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글1';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글2';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`body` = '댓글3';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 2,
`body` = '댓글4';

#댓글에 좋아요, 싫어요 추가
ALTER TABLE reply ADD COLUMN goodReactionPoint INT(10)UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE reply ADD COLUMN badReactionPoint INT(10)UNSIGNED NOT NULL DEFAULT 0;

#댓글 테이블 인덱스 추가
ALTER TABLE `reply` ADD INDEX (`relTypeCode` , `relId`);

# 부가정보테이블
# 댓글 테이블 추가
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `relTypeCode` CHAR(20) NOT NULL,
    `relId` INT(10) UNSIGNED NOT NULL,
    `typeCode` CHAR(30) NOT NULL,
    `type2Code` CHAR(70) NOT NULL,
    `value` TEXT NOT NULL
);

# attr 유니크 인덱스 걸기
## 중복변수 생성금지
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`);

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);

# attr에 만료날짜 추가
ALTER TABLE `attr` ADD COLUMN `expireDate` DATETIME NULL AFTER `value`;