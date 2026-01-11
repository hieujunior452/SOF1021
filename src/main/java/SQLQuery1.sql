CREATE TABLE Users (
    UserId INT IDENTITY PRIMARY KEY,
    Username NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    FullName NVARCHAR(100),
    Phone NVARCHAR(15),
    Email NVARCHAR(100),
    Role NVARCHAR(20) DEFAULT 'USER'
);
CREATE TABLE Brands (
    BrandId INT IDENTITY PRIMARY KEY,
    BrandName NVARCHAR(50) NOT NULL
);
CREATE TABLE Phones (
    PhoneId INT IDENTITY PRIMARY KEY,
    PhoneName NVARCHAR(100) NOT NULL,
    Price BIGINT NOT NULL,
    Image NVARCHAR(255),
    Description NVARCHAR(MAX),
    BrandId INT,
    Stock INT DEFAULT 0,
    FOREIGN KEY (BrandId) REFERENCES Brands(BrandId)
);
CREATE TABLE Cart (
    CartId INT IDENTITY PRIMARY KEY,
    UserId INT,
    PhoneId INT,
    Quantity INT DEFAULT 1,
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (PhoneId) REFERENCES Phones(PhoneId)
);
CREATE TABLE Orders (
    OrderId INT IDENTITY PRIMARY KEY,
    UserId INT,
    OrderDate DATETIME DEFAULT GETDATE(),
    Total BIGINT,
    Status NVARCHAR(50) DEFAULT N'Đã đặt hàng',
    Address NVARCHAR(500),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);
CREATE TABLE OrderDetails (
    OrderDetailId INT IDENTITY PRIMARY KEY,
    OrderId INT,
    PhoneId INT,
    Quantity INT,
    Price BIGINT,
    FOREIGN KEY (OrderId) REFERENCES Orders(OrderId),
    FOREIGN KEY (PhoneId) REFERENCES Phones(PhoneId)
);
INSERT INTO Brands VALUES 
(N'Apple'), (N'Samsung'), (N'Xiaomi');
INSERT INTO Phones VALUES
(N'iPhone 15', 25000000, 'iphone15.jpg', N'iPhone mới nhất', 1, 10),
(N'Samsung S23', 20000000, 's23.jpg', N'Samsung cao cấp', 2, 15);
INSERT INTO Users (Username, Password, FullName)
VALUES ('admin', '123', N'Quản trị');
