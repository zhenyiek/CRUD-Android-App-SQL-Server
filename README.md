# CRUD-Android-App-SQL-Server

CRUD Android App SQL Server
The project is developed for teaching purposes, it is a CRUD developed using Android Studio and SQL Server directly connected through the JTDS driver.

Driver used: http://www.java2s.com/Code/Jar/j/Downloadjtds131jar.htm

该项目是为教学目的而开发的，它是使用Android Studio和SQL Server直接通过JTDS驱动程序连接而开发的CRUD。

使用的驱动程序：http://www.java2s.com/Code/Jar/j/Downloadjtds131jar.htm

Database:

CREATE DATABASE DB_Android;

CREATE TABLE employee(

id INTEGER PRIMARY KEY NOT NULL,

name VARCHAR(100) NOT NULL,

phone VARCHAR(20) NOT NULL,

age INTEGER,

);
