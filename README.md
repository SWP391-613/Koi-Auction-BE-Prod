scratch note:
lam page chi tiet dau gia ca koi
du thong tin tren 

lam full giao dien tren figma
khong du thong tin (dung quan he 3 ngoi cho auction - koi - member)


mình là sàn đấu giá trung gian
Bán   ==== Sàn ==== Mua
1. quy tắc nghiệp vụ của đấu giá
- cá koi ở bên bán
	- bên bán trả trước 1 số tiền để cá lên sàn
- bên mua sẽ nhìn thấy đấu giá liên tục

Ledger-Account
===> ví tiền trong hệ thống ------------> Transaction
- nạp tiền trước vào ví, đấu giá sẽ trừ tiền trong ví ảo ==> tránh đấu giá ảo

2. Thông tin đấu giá
Giá khởi điểm: N dong
Giá mua ngay: N dong
Buoc gia: N dong

Category (phan loai)

KoiFish(Id, Name, OwnerId,...)
KoiImg (Id, KoiFishId, MediaUrl,....)

KoiAuction(Id, KoiFishId, StartTime, EndTime, Status, StepPrice, BasePrice, BuyPrice,...)

KoiBidding(Id, DateTime, Price, BidderId, Status,...)

Transaction
Wallet

==================
KoiOrder(Id, BiddingId, DeliveryAddress, DeliveryStatus,...)

==================
Nền tảng của mình sẽ là chốt giữ tiền trung gian, đến khi hoàn thành việc giao cá mới chuyển tiền tài khoản

==================
MoneyExchange (Nạp rút tiền vào ví)

==================
Bussiness Rules

==================
Business: cong ty, ma so thue, trach nhiem,....
Customer: chưa đủ thông tin thuế, ví định danh,...

Chuyển tiền từ sàn về customer chưa hỗ trợ ngoài thực tế
====> nên tạo lệnh rút tiền và kế toán sẽ thực hiện thủ công rút tiền về bên customer ==> tạo transaction để trừ tiền trong ví của sàn. (hình thức của sàn cược) :D


C2C Customer to Customer (ta là sàn trung gian cho 2 customer)
