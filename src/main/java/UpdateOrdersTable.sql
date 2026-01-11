-- Thêm cột Address vào bảng Orders
USE PhoneStoreDB;
GO

-- Kiểm tra xem cột Address đã tồn tại chưa, nếu chưa thì thêm
IF NOT EXISTS (
    SELECT * 
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'Orders' 
    AND COLUMN_NAME = 'Address'
)
BEGIN
    ALTER TABLE Orders
    ADD Address NVARCHAR(500);
END
GO
