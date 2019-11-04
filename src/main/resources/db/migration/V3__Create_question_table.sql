create table question
(
	id BIGINT AUTO_INCREMENT NOT NULL ,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modified bigint,
	creator BIGINT NOT NULL,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256),
	constraint question_pk
		primary key (id)
);

