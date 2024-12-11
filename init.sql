USE order_service;
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root@$!!m';
CREATE TABLE `orders`
(
    `orderId`     VARCHAR(36) PRIMARY KEY,
    `customerId`  VARCHAR(36)    NOT NULL,
    `orderDate`   DATETIME       NOT NULL,
    `totalAmount` DECIMAL(10, 2) NOT NULL,
    `orderStatus` ENUM('PENDING', 'COMPLETED', 'CANCELLED') NOT NULL
);

CREATE TABLE `order_details`
(
    `detailId`  VARCHAR(36) PRIMARY KEY,
    `orderId`   VARCHAR(36)    NOT NULL,
    `productId` VARCHAR(20)    NOT NULL,
    `quantity`  INT            NOT NULL,
    `price`     DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`) ON DELETE CASCADE
);

INSERT INTO `orders` (`orderId`, `customerId`, `orderDate`, `totalAmount`, `orderStatus`)
VALUES ('1d23c456-7890-12ab-34cd-5678ef90gh12', 'a1b2c3d4-e5f6-7890-ab12-c34d56e78f90', '2024-12-01 14:30:00', 150.50,
        'COMPLETED'),
       ('2e34f567-8901-23ab-45cd-6789gh01ij23', 'b2c3d4e5-f6g7-8901-ab23-d45e67f89g01', '2024-12-02 10:15:00', 200.75,
        'COMPLETED'),
       ('3f45g678-9012-34bc-56de-7890hi12jk34', 'c3d4e5f6-g7h8-9012-ab34-e56f78g90h12', '2024-12-03 09:45:00', 320.00,
        'PENDING'),
       ('4g56h789-0123-45cd-67ef-8901ij23kl45', 'd4e5f6g7-h8i9-0123-ab45-f67g89h01i23', '2024-12-04 11:00:00', 180.00,
        'COMPLETED'),
       ('5h67i890-1234-56de-78fg-9012jk34lm56', 'e5f6g7h8-i9j0-1234-ab56-g78h90i12j34', '2024-12-05 15:20:00', 250.50,
        'CANCELLED'),
       ('6i78j901-2345-67ef-89gh-0123kl45mn67', 'f6g7h8i9-j0k1-2345-ab67-h89i01j23k45', '2024-12-06 08:30:00', 130.00,
        'COMPLETED'),
       ('7j89k012-3456-78fg-90hi-1234lm56no78', 'g7h8i9j0-k1l2-3456-ab78-i90j12k34l56', '2024-12-07 16:45:00', 95.75,
        'COMPLETED'),
       ('8k90l123-4567-89gh-01ij-2345mn67op89', 'h8i9j0k1-l2m3-4567-ab89-j01k23l45m67', '2024-12-08 12:00:00', 220.00,
        'PENDING'),
       ('9l01m234-5678-90hi-12jk-3456no78pq90', 'i9j0k1l2-m3n4-5678-ab90-k12l34m56n78', '2024-12-09 13:30:00', 300.25,
        'COMPLETED'),
       ('0m12n345-6789-01jk-23lm-4567op89qr01', 'j0k1l2m3-n4o5-6789-ab01-l23m45n67o89', '2024-12-10 09:00:00', 145.00,
        'COMPLETED');

INSERT INTO `order_details` (`detailId`, `orderId`, `productId`, `quantity`, `price`)
VALUES ('a1234567-b890-12cd-34ef-567890ghij12', '1d23c456-7890-12ab-34cd-5678ef90gh12', 'p12345', 2, 50.25),
       ('b2345678-c901-23de-45fg-678901hi23jk', '1d23c456-7890-12ab-34cd-5678ef90gh12', 'p67890', 1, 50.00),
       ('c3456789-d012-34ef-56gh-789012ij34kl', '2e34f567-8901-23ab-45cd-6789gh01ij23', 'p23456', 3, 66.75),
       ('d4567890-e123-45fg-67hi-890123jk45lm', '2e34f567-8901-23ab-45cd-6789gh01ij23', 'p34567', 2, 67.00),
       ('e5678901-f234-56gh-78ij-901234kl56mn', '3f45g678-9012-34bc-56de-7890hi12jk34', 'p45678', 4, 80.00),
       ('f6789012-g345-67hi-89jk-012345lm67no', '4g56h789-0123-45cd-67ef-8901ij23kl45', 'p56789', 1, 90.00),
       ('g7890123-h456-78ij-90kl-123456mn78op', '5h67i890-1234-56de-78fg-9012jk34lm56', 'p67890', 5, 50.10),
       ('h8901234-i567-89jk-01lm-234567op89qr', '6i78j901-2345-67ef-89gh-0123kl45mn67', 'p78901', 2, 65.00),
       ('i9012345-j678-90kl-12mn-345678pq90rs', '7j89k012-3456-78fg-90hi-1234lm56no78', 'p89012', 3, 30.25),
       ('j0123456-k789-01mn-23op-456789qr01tu', '8k90l123-4567-89gh-01ij-2345mn67op89', 'p90123', 2, 110.00),
       ('k1234567-l890-12op-34qr-567890st12uv', '9l01m234-5678-90hi-12jk-3456no78pq90', 'p01234', 1, 300.25),
       ('l2345678-m901-23qr-45st-678901tu23vw', '0m12n345-6789-01jk-23lm-4567op89qr01', 'p12345', 4, 36.25),
       ('m3456789-n012-34st-56uv-789012vw34xy', '0m12n345-6789-01jk-23lm-4567op89qr01', 'p23456', 2, 55.00),
       ('n4567890-o123-45uv-67wx-890123xy45yz', '1d23c456-7890-12ab-34cd-5678ef90gh12', 'p23456', 1, 75.00),
       ('o5678901-p234-56wx-78yz-901234yz56ab', '2e34f567-8901-23ab-45cd-6789gh01ij23', 'p34567', 3, 80.00),
       ('p6789012-q345-67yz-89ab-012345ab67cd', '3f45g678-9012-34bc-56de-7890hi12jk34', 'p45678', 2, 100.00),
       ('q7890123-r456-78ab-90cd-123456bc78de', '4g56h789-0123-45cd-67ef-8901ij23kl45', 'p56789', 4, 40.00),
       ('r8901234-s567-89cd-01ef-234567cd89ef', '5h67i890-1234-56de-78fg-9012jk34lm56', 'p67890', 3, 55.00),
       ('s9012345-t678-90ef-12gh-345678de90fg', '6i78j901-2345-67ef-89gh-0123kl45mn67', 'p78901', 1, 85.00),
       ('t0123456-u789-01gh-23ij-456789ef01gh', '7j89k012-3456-78fg-90hi-1234lm56no78', 'p89012', 5, 20.00);