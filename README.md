# Document Exporter System (Lab 05 – Design Patterns & Refactoring)

Hệ thống xuất báo cáo tài liệu thu gọn thuộc học phần **CT240T – Nguyên lý xây dựng phần mềm** (Đại học Cần Thơ). Dự án minh họa cách kết hợp 3 mẫu thiết kế (GoF Design Patterns) gồm **Factory Method**, **Strategy**, và **Observer** để giải quyết bài toán đọc đa định dạng, nén dữ liệu linh hoạt và thông báo sự kiện tự động mà vẫn tuân thủ nguyên tắc **Open/Closed Principle (OCP)**.

---

## Thông tin sinh viên
* **Họ và tên:** Trần Thị Thúy Hiền
* **Trường:** Đại học Cần Thơ (CTU)
* **Học phần:** CT240T – Nguyên lý xây dựng phần mềm

---

## Kiến trúc & Design Patterns áp dụng

Quy trình xuất báo cáo gồm 3 công đoạn độc lập:

1. **Factory Method (`DocumentReader`, `DocumentReaderFactory`)**
   * **Mục đích:** Khởi tạo bộ đọc tài liệu tương ứng với loại file đầu vào (`PDF`, `DOCX`, `CSV`) mà không làm lớp dịch vụ phụ thuộc vào các lớp cụ thể (DIP).
   * **Mở rộng:** Đã bổ sung `CsvReader` và đăng ký vào `DocumentReaderFactory`.
2. **Strategy (`CompressionStrategy`)**
   * **Mục đích:** Cho phép thay đổi thuật toán nén dữ liệu (`ZIP`, `RAR`, `TAR`) linh hoạt ngay tại thời điểm chạy (runtime) mà không dùng cấu trúc `if-else`.
   * **Mở rộng:** Đã bổ sung `TarCompressionStrategy` và truyền trực tiếp vào service mà **không sửa đổi mã nguồn của `DocumentExporterService`** (kiểm chứng nguyên tắc OCP)[cite: 1].
3. **Observer (`ExportObserver`)**[cite: 1]
   * **Mục đích:** Phát sự kiện tự động đến các thành phần đăng ký lắng nghe (`AuditLogObserver`, `ConsoleNotificationObserver`) ngay khi quá trình xuất báo cáo hoàn tất, giảm sự phụ thuộc chặt chẽ (loose coupling)[cite: 1].

---

## Cấu trúc thư mục dự án

```text
lab5-design-patterns/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── com/ctu/lab5/exporter/
    │           ├── DocumentExporterService.java   # Service điều phối chính + Factory, Strategy, Observer
    │           ├── CsvReader.java                 # Concrete Product mở rộng (đọc file CSV)
    │           └── TarCompressionStrategy.java    # Concrete Strategy mở rộng (nén chuẩn TAR)
    └── test/

```
## Yêu cầu môi trường & Công nghệ
* **Ngôn ngữ:** Java 17 (hoặc 21)
* **Build Tool:** Apache Maven
* **Testing Framework:** JUnit 5 (`junit-jupiter` 5.10.2) + `maven-surefire-plugin` (3.2.5)
* **Cấu hình Encoding:** UTF-8 (thêm VM option `-Dstdout.encoding=UTF-8` nếu chạy trên Windows bị lỗi hiển thị tiếng Việt)

---

## Hướng dẫn cài đặt và chạy chương trình

### 1. Chạy ứng dụng chính (Demo Console)
Biên dịch và chạy phương thức `main` trong lớp `com.ctu.lab5.exporter.DocumentExporterService` từ IDE (IntelliJ IDEA) hoặc thông qua Maven:

```bash
mvn compile exec:java -Dexec.mainClass="com.ctu.lab5.exporter.DocumentExporterService"
        └── java/
            └── com/ctu/lab5/exporter/
                └── DocumentExporterTest.java      # Bộ kiểm thử đơn vị JUnit 5
```

**Kết quả đầu ra mẫu:**
```text
Đang tiến hành xuất file PDF...
Nén dữ liệu theo chuẩn ZIP...
[Audit Log] Đã ghi nhật ký xuất file: BaoCaoTaiChinh2026.pdf (Kích thước: 50 bytes)
[Thông báo] Xuất báo cáo thành công file BaoCaoTaiChinh2026.pdf
Đang tiến hành xuất file CSV...
Nén dữ liệu theo chuẩn TAR...
[Audit Log] Đã ghi nhật ký xuất file: DanhSachSinhVien.csv (Kích thước: 48 bytes)
[Thông báo] Xuất báo cáo thành công file DanhSachSinhVien.csv
```
