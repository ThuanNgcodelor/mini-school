# MINI-SCHOOL MANAGEMENT SYSTEM - PROJECT PLAN v3.0

> Hệ thống quản lý lớp học thêm / trung tâm dạy thêm.
> Giáo viên tự chủ tạo lớp, quản lý học sinh, nhập điểm, thu học phí.

---

## 🏗️ TECH STACK

| Layer | Technology |
|-------|-----------|
| Backend | Java 21 + Spring Boot 4.0.3 |
| Security | Spring Security (form-based login) |
| Database | Spring Data JPA + MySQL 8.0 |
| Frontend | Thymeleaf (SSR) |
| Utility | Lombok |
| Infra | Docker + Docker Compose |
| Cache | Redis (Phase 4) |
| Messaging | Kafka - nhắc đóng tiền (Phase 4) |

---

## 🔐 LUỒNG ĐĂNG KÝ & ĐĂNG NHẬP (QUAN TRỌNG NHẤT)

### Vấn đề cần giải quyết:
1. Giáo viên đăng ký như thế nào để được cấp role TEACHER?
2. Học sinh có cần tự đăng ký không hay giáo viên cấp tài khoản?
3. Nếu 1 học sinh học 2 lớp khác nhau (2 GV khác nhau) → xử lý trùng thế nào?

### ✅ GIẢI PHÁP ĐỀ XUẤT: Mô hình "Google Classroom"

```
┌─────────────────────────────────────────────────────────────────┐
│                    LUỒNG ĐĂNG KÝ GIÁO VIÊN                     │
│                                                                 │
│  1. GV vào trang /register → chọn "Tôi là Giáo viên"          │
│  2. Điền thông tin + nhập INVITE CODE (mã mời)                 │
│  3. Hệ thống check invite code hợp lệ → tạo account TEACHER   │
│  4. GV đăng nhập → tự tạo lớp, quản lý mọi thứ               │
│                                                                 │
│  📌 INVITE CODE do Admin tạo sẵn (VD: "TEACHER-2025-XYZ")     │
│     → Tránh ai cũng đăng ký làm giáo viên được                │
│     → Admin chỉ cần phát code, KHÔNG cần tạo tài khoản cho GV │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                 LUỒNG TẠO TÀI KHOẢN HỌC SINH                   │
│                                                                 │
│  CÁCH 1: GIÁO VIÊN TẠO HÀNG LOẠT (Recommended ✅)             │
│  ─────────────────────────────────────────────────              │
│  1. GV tạo lớp "Toán 10A" → hệ thống tạo MÃ LỚP: "T10A-X7K" │
│  2. GV vào lớp → bấm "Thêm học sinh"                          │
│  3. Nhập danh sách: Tên, SĐT phụ huynh                        │
│  4. Hệ thống TỰ ĐỘNG:                                          │
│     a. Tạo tài khoản HS (username = SĐT, password = random)   │
│     b. Kiểm tra SĐT đã tồn tại chưa:                          │
│        - CHƯA CÓ → tạo account mới + gán vào lớp              │
│        - ĐÃ CÓ → chỉ gán vào lớp (không tạo trùng)           │
│     c. In ra danh sách username/password để phát cho HS         │
│                                                                 │
│  CÁCH 2: HỌC SINH TỰ ĐĂNG KÝ (Optional)                      │
│  ─────────────────────────────────────────                      │
│  1. HS vào /register → chọn "Tôi là Học sinh"                 │
│  2. Nhập SĐT + Tên + đặt mật khẩu                             │
│  3. Nhập MÃ LỚP để tham gia lớp                               │
│  4. Hệ thống check:                                            │
│     - SĐT đã có account → link account cũ vào lớp mới        │
│     - SĐT chưa có → tạo mới                                   │
│                                                                 │
│  📌 CẢ 2 CÁCH ĐỀU DÙNG SĐT LÀM KEY CHỐNG TRÙNG              │
└─────────────────────────────────────────────────────────────────┘
```

### 🧩 XỬ LÝ TRÙNG HỌC SINH (Deduplication)

```
Tình huống: Nguyễn Văn A (SĐT: 0901234567)
  - Học Toán lớp GV Hùng
  - Học Lý lớp GV Lan

Cách xử lý:
  ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
  │   users      │     │  students    │     │ enrollments  │
  │──────────────│     │──────────────│     │──────────────│
  │ id: 1        │────→│ id: 1        │────→│ student: 1   │
  │ phone: 0901..│     │ user_id: 1   │     │ class: Toán  │
  │ name: Văn A  │     │              │     ├──────────────│
  │ role: STUDENT│     │              │     │ student: 1   │
  └──────────────┘     └──────────────┘     │ class: Lý    │
                                            └──────────────┘
  → 1 USER, 1 STUDENT, NHIỀU ENROLLMENTS
  → HS dùng 1 tài khoản, login vào thấy TẤT CẢ lớp đang học
```

---

## 👥 PHÂN QUYỀN (ROLES) - ĐÃ CẬP NHẬT

| Role | Quyền hạn |
|------|-----------|
| **ADMIN** | Tạo invite code cho GV, xem tổng quan hệ thống, xem báo cáo tài chính tổng, quản lý môn học/kỳ học, khóa/mở tài khoản |
| **TEACHER** | Tự đăng ký (bằng invite code), **tự tạo lớp**, thêm HS vào lớp, nhập điểm thi, xem tình trạng đóng tiền, ghi nhận thanh toán |
| **STUDENT** | Đăng nhập bằng tài khoản được cấp, xem lớp đang học, xem điểm, xem tình trạng học phí |

> ⚠️ **Thay đổi lớn so với v2.0**: Admin KHÔNG tạo giáo viên, KHÔNG gán GV vào lớp.
> Mỗi GV tự chủ hoàn toàn lớp của mình.

---

## 📊 DATABASE DESIGN (ERD)

### Bảng `users` (Tài khoản chung - đăng nhập)
```
users
├── id (PK, BIGINT, AUTO)
├── phone (UNIQUE) ← KEY chống trùng HS
├── email (UNIQUE, nullable) ← cho GV
├── password (BCrypt)
├── full_name
├── role (ENUM: ADMIN, TEACHER, STUDENT)
├── active (BOOLEAN, default true)
├── created_at
└── updated_at
```

### Bảng `invite_codes` (Mã mời cho GV đăng ký)
```
invite_codes
├── id (PK)
├── code (UNIQUE, VD: "TEACHER-2025-ABC")
├── created_by (FK → users.id, admin tạo)
├── used_by (FK → users.id, nullable, GV đã dùng)
├── used_at (nullable)
├── expires_at
└── active (BOOLEAN)
```

### Bảng `teachers` (Thông tin mở rộng GV)
```
teachers
├── id (PK)
├── user_id (FK → users.id, UNIQUE)
├── specialization (VD: "Toán", "Lý")
└── bio (Giới thiệu ngắn)
```

### Bảng `students` (Thông tin mở rộng HS)
```
students
├── id (PK)
├── user_id (FK → users.id, UNIQUE)
├── student_code (AUTO: "HS-00001")
├── date_of_birth (nullable)
├── parent_name
├── parent_phone
└── address (nullable)
```

### Bảng `subjects` (Môn học)
```
subjects
├── id (PK)
├── name (VD: "Toán", "Lý", "Hóa")
├── code (UNIQUE, VD: "MATH", "PHYS")
└── description
```

### Bảng `classrooms` (Lớp học - GV TỰ TẠO)
```
classrooms
├── id (PK)
├── name (VD: "Toán 10A - Thầy Hùng")
├── class_code (UNIQUE, AUTO, VD: "T10A-X7K") ← MÃ LỚP để HS tham gia
├── teacher_id (FK → teachers.id) ← GV sở hữu lớp này
├── subject_id (FK → subjects.id)
├── max_students (default 30)
├── schedule (VD: "T2-T4 18:00-20:00")
├── tuition_fee (học phí / tháng, DECIMAL)
├── status (ENUM: ACTIVE, CLOSED)
├── created_at
└── updated_at
```

### Bảng `enrollments` (HS ↔ Lớp, bảng trung gian)
```
enrollments
├── id (PK)
├── student_id (FK → students.id)
├── classroom_id (FK → classrooms.id)
├── enrolled_at
├── status (ENUM: ACTIVE, DROPPED, FINISHED)
└── UNIQUE(student_id, classroom_id) ← tránh gán trùng
```

### Bảng `exams` (Bài thi / Kiểm tra)
```
exams
├── id (PK)
├── classroom_id (FK → classrooms.id)
├── name (VD: "Kiểm tra 15 phút #1")
├── exam_type (ENUM: QUIZ_15, TEST_45, MIDTERM, FINAL)
├── exam_date
├── max_score (default 10)
└── created_at
```

### Bảng `exam_scores` (Điểm thi)
```
exam_scores
├── id (PK)
├── exam_id (FK → exams.id)
├── student_id (FK → students.id)
├── score (DECIMAL)
├── note (nullable)
├── graded_at
└── UNIQUE(exam_id, student_id)
```

### Bảng `payments` (Học phí)
```
payments
├── id (PK)
├── student_id (FK → students.id)
├── classroom_id (FK → classrooms.id)
├── amount (DECIMAL)
├── payment_month (VD: "2025-03")
├── payment_date (nullable, ngày thực trả)
├── method (ENUM: CASH, TRANSFER, MOMO)
├── status (ENUM: PAID, PENDING, OVERDUE)
├── note (nullable)
└── created_at
```

### Sơ đồ quan hệ tổng quát:
```
Admin ──creates──→ invite_codes
                       │
                    uses│
                       ▼
User(TEACHER) ──extends──→ teachers ──owns──→ classrooms
                                                  │
                                          ┌───────┼───────┐
                                          ▼       ▼       ▼
                                     enrollments exams  payments
                                          │       │       │
                                          ▼       ▼       ▼
User(STUDENT) ──extends──→ students ──────┴───────┴───────┘
```

---

## 📁 PACKAGE STRUCTURE

```
com.example.minischool
│
├── config/
│   ├── SecurityConfig.java
│   └── WebConfig.java
│
├── entity/
│   ├── User.java
│   ├── InviteCode.java
│   ├── Teacher.java
│   ├── Student.java
│   ├── Subject.java
│   ├── Classroom.java
│   ├── Enrollment.java
│   ├── Exam.java
│   ├── ExamScore.java
│   └── Payment.java
│
├── repository/
│   ├── UserRepository.java
│   ├── InviteCodeRepository.java
│   ├── TeacherRepository.java
│   ├── StudentRepository.java
│   ├── SubjectRepository.java
│   ├── ClassroomRepository.java
│   ├── EnrollmentRepository.java
│   ├── ExamRepository.java
│   ├── ExamScoreRepository.java
│   └── PaymentRepository.java
│
├── service/
│   ├── AuthService.java           # Đăng ký/đăng nhập
│   ├── InviteCodeService.java     # Tạo/validate invite code
│   ├── ClassroomService.java      # GV tạo/quản lý lớp
│   ├── StudentService.java        # Tạo/tìm HS, dedup
│   ├── EnrollmentService.java     # Gán HS vào lớp
│   ├── ExamService.java           # Tạo bài thi + nhập điểm
│   ├── PaymentService.java        # Ghi nhận thanh toán
│   └── DashboardService.java      # Thống kê
│
├── controller/
│   ├── AuthController.java        # /login, /register
│   ├── DashboardController.java   # /dashboard (redirect by role)
│   ├── AdminController.java       # /admin/**
│   ├── TeacherController.java     # /teacher/**
│   └── StudentController.java     # /student/**
│
├── dto/
│   ├── RegisterRequest.java
│   ├── StudentCreateDTO.java      # Khi GV thêm HS
│   ├── ClassroomDTO.java
│   ├── ExamScoreDTO.java
│   ├── PaymentDTO.java
│   └── DashboardStats.java
│
├── enums/
│   ├── RoleName.java              # ADMIN, TEACHER, STUDENT
│   ├── ExamType.java              # QUIZ_15, TEST_45, MIDTERM, FINAL
│   ├── PaymentStatus.java         # PAID, PENDING, OVERDUE
│   ├── PaymentMethod.java         # CASH, TRANSFER, MOMO
│   └── EnrollmentStatus.java      # ACTIVE, DROPPED, FINISHED
│
├── util/
│   ├── ClassCodeGenerator.java    # Tạo mã lớp random
│   └── PasswordGenerator.java    # Tạo mật khẩu random cho HS
│
└── exception/
    ├── GlobalExceptionHandler.java
    └── ResourceNotFoundException.java
```

---

## 🗂️ THYMELEAF TEMPLATES

```
resources/templates/
│
├── layout/
│   └── main.html                    # sidebar + navbar + footer
│
├── auth/
│   ├── login.html
│   └── register.html                # Form có 2 tab: GV / HS
│
├── dashboard/
│   ├── admin.html
│   ├── teacher.html
│   └── student.html
│
├── admin/
│   ├── invite-codes.html            # Tạo/xem invite code
│   ├── teachers.html                # Xem danh sách GV (read-only)
│   ├── all-students.html            # Xem tất cả HS
│   ├── all-classrooms.html          # Xem tất cả lớp
│   ├── subjects.html                # CRUD Môn học
│   └── reports.html                 # Báo cáo tài chính
│
├── teacher/
│   ├── my-classes.html              # DS lớp GV sở hữu
│   ├── class-detail.html            # Chi tiết lớp (HS, sĩ số)
│   ├── add-students.html            # Form thêm HS (đơn/hàng loạt)
│   ├── student-accounts.html        # In username/password HS
│   ├── exams.html                   # DS bài thi trong lớp
│   ├── create-exam.html             # Tạo bài thi
│   ├── enter-scores.html            # Nhập điểm
│   ├── score-report.html            # Bảng điểm tổng hợp
│   ├── payment-status.html          # Ai đóng / chưa đóng
│   └── record-payment.html          # Ghi nhận thanh toán
│
├── student/
│   ├── my-classes.html              # Lớp đang học
│   ├── my-scores.html               # Điểm thi
│   ├── join-class.html              # Nhập mã lớp để tham gia
│   └── my-payments.html             # Tình trạng học phí
│
└── fragments/
    ├── header.html
    ├── sidebar.html
    └── footer.html
```

---

## 🎯 TÍNH NĂNG CHI TIẾT THEO ROLE

### 🔴 ADMIN (Nhẹ - chỉ giám sát)

| # | Tính năng | Mô tả |
|---|-----------|-------|
| 1 | Dashboard | Tổng GV, HS, lớp, doanh thu tháng |
| 2 | Tạo Invite Code | Tạo mã mời cho GV đăng ký, xem lịch sử sử dụng |
| 3 | Xem GV | Danh sách GV (read-only), xem GV đang dạy lớp nào |
| 4 | Xem HS | Danh sách toàn bộ HS trong hệ thống |
| 5 | Xem Lớp | Danh sách toàn bộ lớp, sĩ số |
| 6 | CRUD Môn học | Thêm/sửa/xóa môn (Toán, Lý, Hóa...) |
| 7 | Báo cáo TC | Tổng thu theo tháng, DS nợ học phí |

### 🔵 TEACHER (Chủ lực - tự quản lý mọi thứ)

| # | Tính năng | Mô tả |
|---|-----------|-------|
| 1 | Dashboard | Số lớp, số HS, bài thi, HS nợ tiền |
| 2 | Tạo lớp | Đặt tên, chọn môn, lịch học, học phí → hệ thống tạo mã lớp |
| 3 | Thêm HS | Nhập tên + SĐT → hệ thống tạo account hoặc link HS đã có |
| 4 | In tài khoản | In danh sách username/password cho HS mới |
| 5 | Xem lớp | DS HS, sĩ số X/max, thông tin liên lạc |
| 6 | Tạo bài thi | KT 15p, 45p, giữa kỳ, cuối kỳ |
| 7 | Nhập điểm | Nhập điểm cho từng HS theo bài thi |
| 8 | Bảng điểm | Bảng tổng hợp: ĐTB, max, min, ranking |
| 9 | Xem học phí | Ai đóng rồi, ai chưa, trễ bao lâu |
| 10 | Ghi nhận TT | Bấm "đã đóng" khi HS nộp tiền |

### 🟢 STUDENT (Xem thông tin)

| # | Tính năng | Mô tả |
|---|-----------|-------|
| 1 | Dashboard | Số lớp, ĐTB tổng, tình trạng tiền |
| 2 | Xem lớp | DS lớp đang học, GV, lịch học |
| 3 | Tham gia lớp | Nhập mã lớp để đăng ký vào lớp mới |
| 4 | Xem điểm | Điểm từng bài theo lớp, ĐTB môn |
| 5 | Xem học phí | Đã đóng / chưa đóng / trễ hạn |

---

## 🔌 API ENDPOINTS

### Auth
```
GET  /login                              → Trang đăng nhập
POST /login                              → Xử lý login
GET  /register                           → Trang đăng ký (2 tab: GV/HS)
POST /register/teacher                   → Đăng ký GV (cần invite code)
POST /register/student                   → Đăng ký HS (cần mã lớp)
GET  /logout                             → Đăng xuất
```

### Dashboard
```
GET  /dashboard                          → Auto-redirect theo role
```

### Admin
```
GET  /admin/dashboard                    → Dashboard admin
GET  /admin/invite-codes                 → DS invite codes
POST /admin/invite-codes                 → Tạo invite code mới
GET  /admin/teachers                     → DS giáo viên (read-only)
GET  /admin/students                     → DS học sinh (read-only)
GET  /admin/classrooms                   → DS lớp (read-only)
GET  /admin/subjects                     → CRUD môn học
POST /admin/subjects                     → Thêm môn
POST /admin/subjects/{id}/delete         → Xóa môn
GET  /admin/reports                      → Báo cáo tài chính
```

### Teacher
```
GET  /teacher/dashboard                  → Dashboard GV
GET  /teacher/classes                    → DS lớp mình tạo
GET  /teacher/classes/new                → Form tạo lớp
POST /teacher/classes                    → Lưu lớp mới
GET  /teacher/classes/{id}               → Chi tiết lớp (DS HS, sĩ số)
GET  /teacher/classes/{id}/add-students  → Form thêm HS
POST /teacher/classes/{id}/add-students  → Thêm HS (tạo account nếu cần)
GET  /teacher/classes/{id}/accounts      → In username/password HS
POST /teacher/classes/{id}/remove/{sid}  → Xóa HS khỏi lớp

GET  /teacher/classes/{id}/exams         → DS bài thi
GET  /teacher/classes/{id}/exams/new     → Form tạo bài thi
POST /teacher/classes/{id}/exams         → Lưu bài thi
GET  /teacher/exams/{id}/scores          → Form nhập/xem điểm
POST /teacher/exams/{id}/scores          → Lưu điểm
GET  /teacher/classes/{id}/score-report  → Bảng điểm tổng hợp

GET  /teacher/classes/{id}/payments      → Tình trạng đóng tiền
POST /teacher/payments/{id}/confirm      → Xác nhận đã thanh toán
```

### Student
```
GET  /student/dashboard                  → Dashboard HS
GET  /student/classes                    → Lớp đang học
GET  /student/join-class                 → Form nhập mã lớp
POST /student/join-class                 → Xử lý tham gia lớp
GET  /student/scores                     → Điểm thi tổng hợp
GET  /student/payments                   → Tình trạng học phí
```

---

## 📅 LỘ TRÌNH PHÁT TRIỂN

### PHASE 1: NỀN TẢNG (1-2 tuần)
- [ ] Setup package structure
- [ ] Entity classes (tất cả bảng)
- [ ] Repository layer
- [ ] Enums
- [ ] Spring Security config (form login, role-based access)
- [ ] Base Thymeleaf layout (sidebar theo role)
- [ ] AuthController: login + register GV (invite code)
- [ ] Admin: tạo invite code
- [ ] Dashboard cơ bản 3 roles

### PHASE 2: TEACHER CORE (2-3 tuần)
- [ ] Teacher tạo lớp (auto-gen class code)
- [ ] Teacher thêm HS vào lớp (auto-create account, dedup by phone)
- [ ] In danh sách tài khoản HS
- [ ] Xem chi tiết lớp (DS HS, sĩ số)
- [ ] Admin CRUD môn học
- [ ] Student login + xem lớp
- [ ] Student tham gia lớp bằng mã

### PHASE 3: ĐIỂM + HỌC PHÍ (1-2 tuần)
- [ ] Teacher tạo bài thi
- [ ] Teacher nhập điểm
- [ ] Bảng điểm tổng hợp (ĐTB, max, min)
- [ ] Student xem điểm
- [ ] Teacher xem tình trạng đóng tiền
- [ ] Teacher ghi nhận thanh toán
- [ ] Student xem học phí
- [ ] Admin báo cáo tài chính

### PHASE 4: NÂNG CAO (Optional)
- [ ] Redis cache cho dashboard
- [ ] Kafka notification nhắc đóng tiền
- [ ] Export Excel (bảng điểm, DS nợ)
- [ ] Phân trang + tìm kiếm
- [ ] OAuth2 Google Login
- [ ] Responsive design

---

## 💡 GHI CHÚ KIẾN TRÚC

1. **SĐT là UNIQUE KEY** của bảng `users` → dùng để chống trùng HS.
   Khi GV thêm HS bằng SĐT, hệ thống check: đã có → chỉ enroll, chưa có → tạo mới.

2. **Mã lớp (class_code)** auto-generated, 6-8 ký tự, dùng để HS tự join.

3. **Invite code** cho GV có thời hạn + chỉ dùng 1 lần. Admin tạo trước, phát cho GV.

4. **GV sở hữu lớp** → GV chỉ xem/sửa lớp của mình, KHÔNG thấy lớp GV khác.

5. **Password HS** được generate random, GV in ra giấy phát cho HS.
   HS có thể đổi password sau khi login lần đầu.

6. **Security**: Mỗi endpoint check quyền sở hữu.
   VD: `/teacher/classes/5` → check class #5 có thuộc GV đang login không.

---