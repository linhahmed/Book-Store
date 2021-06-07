drop schema if exists library;
Create Schema library;
Use library;

create table book (
ISBN varchar(20) primary key not null,
Title varchar (30) not null,
Publisher varchar(20) ,
Publication_Year date,
Quantity int ,
Min_Quantity int,
Price int,
Category varchar(10)
);

create table bookauthor (
AuthorName varchar(20) not null,
ISBN varchar(20) not null,
constraint bookauthor_pk primary key(AuthorName , ISBN),
constraint bookauthor_fk foreign key (ISBN) references book(ISBN)
On delete cascade
On update cascade
);

create table publisher (
PName varchar(20) primary key not null,
Address varchar(30),
Phone varchar(20)
);

create table users(
userName varchar(100) primary key not null,
pass varchar(100) not null,
lname varchar(100) not null,
fname varchar(100) not null,
email varchar(100) not null,
phonenum varchar(20) not null,
shippingAddress varchar(100) not null,
ismanager bit not null /*0 -> customer , 1 -> manager*/
);

create table sales(
userName varchar(20) not null,
ISBN varchar(20) not null,
num_of_cpies int not null,
orderDate datetime not null,
constraint sales_pk primary key (userName, orderDate),
constraint sales_fk1 foreign key (ISBN) references book(ISBN)
On delete cascade
On update cascade,
constraint sales_fk2 foreign key (userName) references users(userName)
On delete cascade
On update cascade
);


create table orders(
ISBN varchar(20) not null,
quantity int not null,
constraint orders_pk primary key (ISBN),
constraint orders_fk foreign key (ISBN) references book(ISBN) 
On delete cascade
On update cascade
);


Alter table book Add constraint Publisherfk foreign key(Publisher) 
references  publisher (PName) on delete cascade on update cascade;

delimiter $$
CREATE TRIGGER negQuantity BEFORE UPDATE ON book 
FOR EACH ROW
begin
	declare msg varchar(128);
    if new.Quantity < 0 then
        set msg = concat('Error: Number of books can not be negative!!');
        signal sqlstate '45000' set message_text = msg;
    end if;
end $$
delimiter ;


delimiter $$
CREATE TRIGGER placeOrders AFTER UPDATE ON book 
FOR EACH ROW
begin
    if new.Quantity < old.Min_Quantity then
		insert into orders values (old.ISBN, old.Min_Quantity - new.Quantity);
    end if;
end $$
delimiter ;


delimiter $$
CREATE TRIGGER confirmOrders BEFORE DELETE ON orders 
FOR EACH ROW
begin
    update book set book.Quantity = book.Quantity + old.Quantity
    where book.ISBN = old.ISBN;
end $$ 
delimiter ;


insert into publisher values ('Aya','10 mahmodya st vectoria','0115698714');
insert into publisher values ('Nada','11 mahmodya st vectoria','0102369741');
insert into publisher values ('Linh','13 mahmodya st vectoria','0120955526');
insert into book values ('Nada','11 mahmodya st vectoria','0102369741');