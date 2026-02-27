import { useState } from 'react';
import { Link } from 'react-router-dom';

export default function Dashboard() {
  const [isAddStudentModalOpen, setIsAddStudentModalOpen] = useState(false);

  return (
    <div className="max-w-7xl mx-auto space-y-8">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-6">
        <div>
          <h2 className="text-3xl font-black text-slate-900 mb-2">Chào mừng, Thầy Hùng! 👋</h2>
          <p className="text-lg text-slate-600">Dưới đây là tổng quan lớp học của thầy.</p>
        </div>
        <button 
          onClick={() => setIsAddStudentModalOpen(true)}
          className="bg-primary hover:bg-blue-700 text-white font-bold text-lg px-6 py-3 rounded-xl shadow-lg shadow-blue-500/30 flex items-center gap-3 transition-all active:scale-95"
        >
          <span className="material-symbols-outlined">add_circle</span>
          Tạo lớp học mới
        </button>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 flex items-center gap-5 hover:shadow-md transition-shadow">
          <div className="size-16 rounded-xl bg-blue-100 flex items-center justify-center text-primary">
            <span className="material-symbols-outlined text-4xl">cast_for_education</span>
          </div>
          <div>
            <p className="text-slate-500 font-medium text-lg">Lớp đang mở</p>
            <h3 className="text-3xl font-black text-slate-900 mt-1">4 Lớp</h3>
          </div>
        </div>
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 flex items-center gap-5 hover:shadow-md transition-shadow">
          <div className="size-16 rounded-xl bg-emerald-100 flex items-center justify-center text-emerald-600">
            <span className="material-symbols-outlined text-4xl">groups</span>
          </div>
          <div>
            <p className="text-slate-500 font-medium text-lg">Tổng học sinh</p>
            <h3 className="text-3xl font-black text-slate-900 mt-1">120 Học sinh</h3>
          </div>
        </div>
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 flex items-center gap-5 hover:shadow-md transition-shadow">
          <div className="size-16 rounded-xl bg-orange-100 flex items-center justify-center text-orange-600">
            <span className="material-symbols-outlined text-4xl">currency_exchange</span>
          </div>
          <div>
            <p className="text-slate-500 font-medium text-lg">Học phí chưa thu</p>
            <h3 className="text-3xl font-black text-orange-600 mt-1">5.000.000đ</h3>
          </div>
        </div>
      </div>
      <div className="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden">
        <div className="p-6 border-b border-slate-200 flex flex-col md:flex-row md:items-center justify-between gap-4">
          <h3 className="text-xl font-bold text-slate-900 flex items-center gap-2">
            <span className="material-symbols-outlined text-primary">table_chart</span>
            Lớp học của tôi
          </h3>
          <div className="flex items-center gap-3">
            <div className="relative">
              <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 material-symbols-outlined text-xl">search</span>
              <input className="pl-10 pr-4 py-2 rounded-lg border border-slate-300 bg-slate-50 text-slate-900 focus:ring-2 focus:ring-primary/20 focus:border-primary outline-none text-base w-full md:w-64" placeholder="Tìm kiếm lớp..." type="text" />
            </div>
            <button className="p-2 text-slate-500 hover:bg-slate-100 rounded-lg transition-colors">
              <span className="material-symbols-outlined">filter_list</span>
            </button>
          </div>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-slate-50 text-slate-600 text-lg border-b border-slate-200">
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider">Tên lớp</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider">Mã tham gia</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider text-center">Sĩ số</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider">Lịch học</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider text-right">Hành động</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-200">
              <tr className="hover:bg-slate-50 transition-colors">
                <td className="py-6 px-8">
                  <div className="flex items-center gap-3">
                    <div className="size-10 rounded-lg bg-indigo-100 text-indigo-600 flex items-center justify-center font-bold text-lg">10</div>
                    <div>
                      <p className="font-bold text-slate-900 text-lg">Toán 10 - Nâng cao</p>
                      <p className="text-sm text-slate-500">Phòng A101</p>
                    </div>
                  </div>
                </td>
                <td className="py-6 px-8">
                  <span className="inline-flex items-center gap-1.5 px-3 py-2 rounded-md bg-slate-100 text-slate-700 font-mono text-base font-medium">
                    MATH10NC
                    <button className="text-slate-400 hover:text-primary"><span className="material-symbols-outlined text-sm">content_copy</span></button>
                  </span>
                </td>
                <td className="py-6 px-8 text-center">
                  <span className="inline-block px-3 py-2 rounded-full bg-blue-50 text-blue-700 font-bold text-base">32/40</span>
                </td>
                <td className="py-6 px-8">
                  <div className="flex flex-col gap-1">
                    <span className="text-base text-slate-700 font-medium">T2 - T4 - T6</span>
                    <span className="text-sm text-slate-500">17:30 - 19:00</span>
                  </div>
                </td>
                <td className="py-6 px-8 text-right">
                  <Link to="/class/1" className="inline-flex items-center gap-2 px-4 py-2 bg-white border border-slate-300 rounded-lg text-slate-700 font-bold hover:bg-slate-50 transition-colors shadow-sm">
                    <span className="material-symbols-outlined text-lg">settings</span>
                    Quản lý lớp
                  </Link>
                </td>
              </tr>
              <tr className="hover:bg-slate-50 transition-colors">
                <td className="py-6 px-8">
                  <div className="flex items-center gap-3">
                    <div className="size-10 rounded-lg bg-pink-100 text-pink-600 flex items-center justify-center font-bold text-lg">12</div>
                    <div>
                      <p className="font-bold text-slate-900 text-lg">Luyện thi ĐH Khối A</p>
                      <p className="text-sm text-slate-500">Phòng B202</p>
                    </div>
                  </div>
                </td>
                <td className="py-6 px-8">
                  <span className="inline-flex items-center gap-1.5 px-3 py-2 rounded-md bg-slate-100 text-slate-700 font-mono text-base font-medium">
                    KHOIA2024
                    <button className="text-slate-400 hover:text-primary"><span className="material-symbols-outlined text-sm">content_copy</span></button>
                  </span>
                </td>
                <td className="py-6 px-8 text-center">
                  <span className="inline-block px-3 py-2 rounded-full bg-emerald-50 text-emerald-700 font-bold text-base">45/45</span>
                </td>
                <td className="py-6 px-8">
                  <div className="flex flex-col gap-1">
                    <span className="text-base text-slate-700 font-medium">T3 - T5 - T7</span>
                    <span className="text-sm text-slate-500">19:30 - 21:00</span>
                  </div>
                </td>
                <td className="py-6 px-8 text-right">
                  <Link to="/class/2" className="inline-flex items-center gap-2 px-4 py-2 bg-white border border-slate-300 rounded-lg text-slate-700 font-bold hover:bg-slate-50 transition-colors shadow-sm">
                    <span className="material-symbols-outlined text-lg">settings</span>
                    Quản lý lớp
                  </Link>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="p-6 border-t border-slate-200 flex items-center justify-between bg-white">
          <span className="text-sm text-slate-500">Hiển thị 2 trên 4 lớp</span>
          <div className="flex gap-2">
            <button className="px-3 py-2 border border-slate-200 rounded hover:bg-slate-50 disabled:opacity-50 text-slate-600" disabled>Trước</button>
            <button className="px-3 py-2 bg-primary text-white rounded font-medium">1</button>
            <button className="px-3 py-2 border border-slate-200 rounded hover:bg-slate-50 disabled:opacity-50 text-slate-600" disabled>Sau</button>
          </div>
        </div>
      </div>

      {isAddStudentModalOpen && (
        <div aria-modal="true" className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/50 backdrop-blur-sm" role="dialog">
          <div className="w-full max-w-2xl bg-white rounded-2xl shadow-2xl transform transition-all flex flex-col max-h-[90vh]">
            <div className="p-6 border-b border-slate-200 flex items-center justify-between">
              <h3 className="text-2xl font-bold text-slate-900">Thêm danh sách học sinh</h3>
              <button onClick={() => setIsAddStudentModalOpen(false)} className="text-slate-400 hover:text-slate-500 transition-colors">
                <span className="material-symbols-outlined text-2xl">close</span>
              </button>
            </div>
            <div className="p-6 overflow-y-auto">
              <div className="space-y-6">
                <div>
                  <label className="block text-lg font-medium text-slate-900 mb-2">
                    Nhập danh sách học sinh
                  </label>
                  <p className="text-sm text-slate-500 mb-4">
                    Nhập tên và số điện thoại, mỗi học sinh một dòng. Hệ thống sẽ tự động tạo tài khoản cho các số điện thoại chưa tồn tại.
                  </p>
                  <div className="relative">
                    <textarea className="w-full h-64 p-4 rounded-xl border border-slate-300 bg-slate-50 text-slate-900 placeholder:text-slate-400 focus:ring-2 focus:ring-primary/20 focus:border-primary outline-none resize-none text-base leading-relaxed font-mono" placeholder="Ví dụ: Nguyễn Văn A - 0901234567"></textarea>
                    <div className="absolute bottom-4 right-4 text-xs text-slate-400 flex items-center gap-1 bg-white/80 px-2 py-1 rounded">
                      <span className="material-symbols-outlined text-sm">info</span>
                      Hỗ trợ dán từ Excel
                    </div>
                  </div>
                </div>
                <div className="bg-blue-50 p-4 rounded-xl border border-blue-100 flex gap-3">
                  <span className="material-symbols-outlined text-primary">tips_and_updates</span>
                  <div className="text-sm text-slate-700">
                    <p className="font-bold mb-1">Mẹo nhanh:</p>
                    <p>Bạn có thể copy trực tiếp cột "Họ tên" và "SĐT" từ file Excel danh sách lớp và dán vào đây.</p>
                  </div>
                </div>
              </div>
            </div>
            <div className="p-6 border-t border-slate-200 bg-slate-50 rounded-b-2xl flex justify-end gap-3">
              <button onClick={() => setIsAddStudentModalOpen(false)} className="px-6 py-3 rounded-xl border border-slate-300 text-slate-700 font-bold hover:bg-slate-100 transition-colors text-base">
                Hủy bỏ
              </button>
              <button onClick={() => setIsAddStudentModalOpen(false)} className="px-6 py-3 rounded-xl bg-primary text-white font-bold hover:bg-blue-700 shadow-lg shadow-blue-500/30 transition-all active:scale-95 text-base flex items-center gap-2">
                <span className="material-symbols-outlined">person_add</span>
                Tạo tài khoản & Thêm vào lớp
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
