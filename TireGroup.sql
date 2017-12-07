drop database if exists `TireGroup`;
create database `TireGroup`;
use TireGroup;

drop table if exists `Staging`;
create table Staging(
	tireName nvarchar(200),
	tirePrice nvarchar(50),
	tireRating nvarchar(50),
	tireBrand nvarchar(50),
	tireRimDiameter nvarchar(50)
);

load data local infile "C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Tires.csv"
into table Staging
fields terminated by ','
lines terminated by '\n'
ignore 1 lines;

drop table if exists `Tires`;
create table Tires
(
	tireID int not null auto_increment,
	`name` nvarchar(200),
	price decimal(10,2),
	rating decimal(2,1),
	brand nvarchar(50),
	rimDiameter int,
    primary key (tireID)
);

insert ignore into Tires
						(`name`, price, rating, brand, rimDiameter)
	select distinct		tireName, tirePrice, tireRating, tireBrand, tireRimDiameter
		from Staging where tireName is not null;


drop table if exists `Employees`;
create table Employees
(
	employeeID int not null AUTO_INCREMENT,
	firstName varchar(50),
	lastName varchar(50),
	startDate date,
    `password` varchar(255),
    isAdmin bool default false,
    primary key(employeeID)
);
    
delimiter //
drop procedure if exists `proc_InsertEmployee`//
create procedure proc_InsertEmployee(
	in FN varchar(50),
    in LN varchar(50),
    in SD date,
    in Admin tinyint(1),
    out made tinyint(1) 
)

begin
	set made = 0;
	if not exists ( select  * from employees where firstName = FN and lastName = LN and
	startDate = SD and isAdmin = Admin limit 1) then
		insert into employees (firstName, lastName, startDate, password, isAdmin)
		values (FN, LN, SD, null, Admin);
		set made = 1;
	end if;
end//
delimiter ;

drop table if exists `Customers`;
create table Customers
(
	customerID int not null auto_increment,
	firstName varchar(50),
	lastName varchar(50),
    phoneNumber varchar(50),
    email varchar(100),
    primary key (customerID)
);

delimiter //
drop procedure if exists `proc_InsertCustomer`//
create procedure proc_InsertCustomer(
	in FN varchar(50),
    in LN varchar(50),
    in PN varchar(50),
    in EM varchar(100),
    out id int(11)
)

begin
	if not exists ( select  * from customers where firstName = FN and lastName = LN and
	phoneNumber = PN and email = EM limit 1) then
		insert into customers (firstName, lastName, phoneNumber, email)
		values (FN, LN, PN, EM);
	end if;
   SET id = (select customerID from customers where firstName = FN and lastName = LN and
    phoneNumber = PN and email = EM limit 1);
end//
delimiter ;


drop table if exists `Orders`;
create table `Orders`
(
	orderID int not null auto_increment,
	tireID int not null,
	orderDate date,
    quantity int,
    laborCost double(10,2),
    primary key(orderID),
    constraint fk_tires foreign key (tireID)
    references tires(tireID)
    on delete cascade
    on update cascade
);
    
drop table if exists `Invoices`;
create table `Invoices`
(
	invoiceID int not null auto_increment,
    employeeID int not null,
    customerID int not null,
    orderID int not null,
    invoiceDate date,
    primary key(invoiceID),
    constraint fk_invoices_to_employees foreign key(employeeID)
    references employees(employeeID)
    on delete cascade
    on update cascade,
    constraint fk_invoices_to_customers foreign key(customerID)
    references customers(customerID)
    on delete cascade
    on update cascade,
    constraint fk_orders foreign key(orderID)
    references orders(orderID)
    on delete cascade
    on update cascade
);
    
drop table if exists `Appointments`;
create table `Appointments`
(
	appointmentID int not null auto_increment,
	EmployeeID int not null,
    CustomerID int not null,
    appointmentDate date,
    appointmentTime time,
    primary key(appointmentID),
	constraint fk_appointments_to_employees foreign key(employeeID)
    references employees(employeeID)
    on delete cascade
    on update cascade,
	constraint fk_appointments_to_customers foreign key(customerID)
    references customers(customerID)
    on delete cascade
    on update cascade
);

insert ignore into Employees Values(null, 'John', 'Doe', '2012-02-04', md5('Password123'), true);
insert ignore into Employees Values(null, 'Jake', 'Wills', '2012-02-05', md5('Test123'), false);
insert ignore into Employees Values(null, 'Jill', 'Doe', '2012-02-06', null, true);
insert ignore into Employees Values(null, 'Gabe', 'Wills', '2012-02-07', null, false);
insert ignore into Employees Values(null, 'Albert', 'Doe', '2012-02-08', null, true);
insert ignore into Employees Values(null, 'Bob', 'Wills', '2012-02-09', null, false);
insert ignore into Employees Values(null, 'Blake', 'Doe', '2012-02-10', null, true);
insert ignore into Employees Values(null, 'George', 'Wills', '2012-02-11', null, false);
insert ignore into Employees Values(null, 'Nick', 'Doe', '2012-02-12', null, true);
insert ignore into Employees Values(null, 'Noah', 'Wills', '2012-02-13', null, false);

insert into Customers Values(null,'Dylan', 'Doe', '212-555-2222', 'test1@test.com');
insert into Customers Values(null,'Jake', 'Doe', '212-555-3333', 'test2@test.com');
insert into Customers Values(null,'Jill', 'Doe', '212-555-4444', 'test3@test.com');
insert into Customers Values(null,'Gabe', 'Doe', '212-555-5555', 'test4@test.com');
insert into Customers Values(null,'Albert', 'Doe', '212-555-6666', 'test5@test.com');
insert into Customers Values(null,'Bob', 'Doe', '212-555-7777', 'test6@test.com');
insert into Customers Values(null,'George', 'Doe', '213-555-2222', 'test7@test.com');
insert into Customers Values(null,'Nick', 'Doe', '214-555-2222', 'test8@test.com');
insert into Customers Values(null,'Noah', 'Doe', '215-555-2222', 'test9@test.com');
insert into Customers Values(null,'John', 'Doe', '216-555-2222', 'test10@test.com');

insert into Orders Values(null, 1, '2012-02-11', 1, 0);
insert into Orders Values(null, 1, '2012-02-12', 2, 0);
insert into Orders Values(null, 1, '2012-02-13', 3, 0);
insert into Orders Values(null, 1, '2012-02-14', 1, 0);
insert into Orders Values(null, 1, '2012-02-15', 2, 0);
insert into Orders Values(null, 1, '2012-02-16', 3, 0);
insert into Orders Values(null, 1, '2012-02-17', 4, 0);
insert into Orders Values(null, 1, '2012-02-18', 2, 0);
insert into Orders Values(null, 1, '2012-02-19', 3, 0);
insert into Orders Values(null, 1, '2012-02-20', 4, 0);

insert into Invoices Values(null, 1, 1, 1, '2012-02-10');
insert into Invoices Values(null, 2, 2, 2, '2012-02-11');
insert into Invoices Values(null, 3, 3, 3, '2012-02-12');
insert into Invoices Values(null, 4, 4, 4, '2012-02-13');
insert into Invoices Values(null, 5, 5, 5, '2012-02-14');
insert into Invoices Values(null, 6, 6, 6, '2012-02-15');
insert into Invoices Values(null, 7, 7, 7, '2012-02-16');
insert into Invoices Values(null, 8, 8, 8, '2012-02-17');
insert into Invoices Values(null, 9, 9, 9, '2012-02-18');
insert into Invoices Values(null, 10, 10, 10, '2012-02-19');
