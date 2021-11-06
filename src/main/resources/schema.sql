create table IF NOT EXISTS blog_article_tag(
    article_tag_id int not null primary key auto_increment,
    article_id int not null,
    tag_id int not null
)engine=InnoDB;

create table IF NOT EXISTS blog_article(
    article_id int not null primary key auto_increment,
    article_preview text null,
    article_preview_text text null,
    article_title varchar(64) not null,
    article_create_time bigint not null,
    article_update_time bigint null,
    article_user_id int not null,
    article_browsed_count int not null,
    article_comment_count int not null,
    article_access_pass varchar(32) null,
    article_is_top int not null,
    article_cover varchar(255) null,
    article_content_id int not null,
    article_status int not null,
	article_category_id int not null,
    
    article_custom_url varchar(127) null unique
)engine=InnoDB;

CREATE TABLE IF NOT EXISTS blog_browsed(
    browsed_id int not null PRIMARY KEY auto_increment,
    browsed_ip char(16) not null,
    browsed_position varchar(128) null,
    browsed_user_id int null,
    browsed_time bigint not null
)engine=InnoDB;

create table IF NOT EXISTS blog_category(
    category_id int not null primary key auto_increment,
    category_name varchar(64) not null unique,
    category_order int not null,
    category_parent_id int null,
    category_article_num int null
)engine=InnoDB;

create table IF NOT EXISTS blog_comment(
    comment_id int not null primary key auto_increment,
    comment_body text not null,
    comment_user_id int not null,
    comment_root_id int not null,
    comment_parent_id int null,
    comment_article_id int not null,
    comment_status int not null,
    comment_create_time bigint not null
)engine=InnoDB;

create table IF NOT EXISTS blog_content(
    content_id int not null primary key auto_increment,
    content_body text not null,
    content_body_text text null,
    content_type int not null,
    content_rollback_flag int null,
    content_describe varchar(255) null
    
)engine=InnoDB;

create table IF NOT EXISTS blog_page(
    page_id int not null primary key auto_increment,
    page_name varchar(64) not null unique,
    page_content_id int null,
    page_order int not null,
    page_type int not null,
 
    page_url varchar(255) null unique
)engine=InnoDB;

create table IF NOT EXISTS blog_property(
    property_id int not null primary key auto_increment,
    property_key varchar(255) not null unique,
    property_value varchar(255) null
)engine=InnoDB;

create table IF NOT EXISTS property(
    property_id int not null primary key auto_increment,
    property_key varchar(255) not null unique,
    property_value varchar(255) null
)engine=InnoDB;

create table IF NOT EXISTS blog_tag(
    tag_id int primary key auto_increment,
    tag_name varchar(64) unique,
	tag_article_num int not null
)engine=InnoDB;

create table IF NOT EXISTS blog_user_setting(
    user_id int not null,
    user_setting_key varchar(255) not null,
    user_setting_value varchar(255) null
)engine=InnoDB;


CREATE TABLE IF NOT EXISTS blog_user(
    user_id int not null PRIMARY KEY auto_increment,
    user_name varchar(32) not null unique,
    user_nickname varchar(32) not null unique,
    user_pass int null,
    user_role int not null default 2,
    user_email varchar(255) null,
    user_avatar_url varchar(255)  null,
    github_id bigint null

)engine=InnoDB;

