import { Link } from 'react-router-dom';

export default function ClassDetails() {
  return (
    <div className="max-w-7xl mx-auto space-y-6">
      <div className="bg-white rounded-2xl p-6 shadow-sm border border-slate-200">
        <div className="flex flex-col lg:flex-row justify-between items-start lg:items-center gap-4">
          <div>
            <div className="flex items-center gap-3 mb-2">
              <Link to="/dashboard" className="text-slate-500 hover:text-primary transition-colors">
                <span className="material-symbols-outlined text-2xl">arrow_back</span>
              </Link>
              <h2 className="text-3xl font-black text-slate-900">Toán 10A - Thầy Hùng</h2>
            </div>
            <div className="flex flex-wrap items-center gap-3 pl-9">
              <span className="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-slate-100 text-slate-700 font-mono text-base font-bold border border-slate-200">
                <span className="material-symbols-outlined text-lg text-slate-500">key</span>
                T10A-X7K
                <button className="text-slate-400 hover:text-primary ml-1" title="Sao chép mã"><span className="material-symbols-outlined text-sm">content_copy</span></button>
              </span>
              <span className="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-blue-50 text-blue-700 font-bold text-base border border-blue-100">
                <span className="material-symbols-outlined text-lg">groups</span>
                45/50 Học viên
              </span>
              <span className="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-orange-50 text-orange-700 font-bold text-base border border-orange-100">
                <span className="material-symbols-outlined text-lg">payments</span>
                500.000đ/tháng
              </span>
            </div>
          </div>
          <div className="flex items-center gap-3 w-full lg:w-auto mt-4 lg:mt-0">
            <div className="relative flex-1 lg:w-64">
              <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 material-symbols-outlined text-xl">search</span>
              <input className="pl-10 pr-4 py-3 rounded-xl border border-slate-300 bg-slate-50 text-slate-900 focus:ring-2 focus:ring-primary/20 focus:border-primary outline-none text-base w-full shadow-sm" placeholder="Tìm kiếm học sinh..." type="text" />
            </div>
            <button className="bg-green-600 hover:bg-green-700 text-white font-bold text-base px-5 py-3 rounded-xl shadow-lg shadow-green-500/20 flex items-center gap-2 transition-all active:scale-95 whitespace-nowrap">
              <span className="material-symbols-outlined">add</span>
              Thêm học sinh
            </button>
          </div>
        </div>
      </div>
      <div className="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-slate-100 text-slate-600 text-lg border-b border-slate-200">
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider w-16 text-center">STT</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider w-32">Mã HS</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider">Họ và Tên</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider">SĐT Phụ huynh</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider text-center">Trạng thái</th>
                <th className="py-5 px-8 font-bold uppercase text-sm tracking-wider text-right">Hành động</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-200 text-base">
              <tr className="bg-white hover:bg-slate-50 transition-colors">
                <td className="py-6 px-8 text-center font-medium text-slate-500">1</td>
                <td className="py-6 px-8 font-mono font-medium text-slate-600">HS001</td>
                <td className="py-6 px-8">
                  <div className="flex items-center gap-3">
                    <div className="size-10 rounded-full bg-indigo-100 text-indigo-600 flex items-center justify-center font-bold text-sm">NM</div>
                    <span className="font-bold text-slate-900 text-base">Nguyễn Văn Minh</span>
                  </div>
                </td>
                <td className="py-6 px-8 font-medium text-slate-600">0912 345 678</td>
                <td className="py-6 px-8 text-center">
                  <span className="inline-flex items-center px-3 py-2 rounded-full bg-emerald-100 text-emerald-700 font-bold text-sm">
                    Đang học
                  </span>
                </td>
                <td className="py-6 px-8 text-right">
                  <div className="flex items-center justify-end gap-2">
                    <button className="inline-flex items-center gap-1 px-4 py-2 bg-blue-50 text-blue-700 hover:bg-blue-100 rounded-lg font-semibold transition-colors text-sm">
                      <span className="material-symbols-outlined text-base">visibility</span>
                      Xem điểm
                    </button>
                    <button className="p-2 text-slate-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors" title="Xóa học sinh">
                      <span className="material-symbols-outlined text-xl">delete</span>
                    </button>
                  </div>
                </td>
              </tr>
              <tr className="bg-slate-50 hover:bg-slate-100 transition-colors">
                <td className="py-6 px-8 text-center font-medium text-slate-500">2</td>
                <td className="py-6 px-8 font-mono font-medium text-slate-600">HS002</td>
                <td className="py-6 px-8">
                  <div className="flex items-center gap-3">
                    <div className="size-10 rounded-full bg-pink-100 text-pink-600 flex items-center justify-center font-bold text-sm">TL</div>
                    <span className="font-bold text-slate-900 text-base">Trần Thị Lan</span>
                  </div>
                </td>
                <td className="py-6 px-8 font-medium text-slate-600">0987 654 321</td>
                <td className="py-6 px-8 text-center">
                  <span className="inline-flex items-center px-3 py-2 rounded-full bg-emerald-100 text-emerald-700 font-bold text-sm">
                    Đang học
                  </span>
                </td>
                <td className="py-6 px-8 text-right">
                  <div className="flex items-center justify-end gap-2">
                    <button className="inline-flex items-center gap-1 px-4 py-2 bg-blue-50 text-blue-700 hover:bg-blue-100 rounded-lg font-semibold transition-colors text-sm">
                      <span className="material-symbols-outlined text-base">visibility</span>
                      Xem điểm
                    </button>
                    <button className="p-2 text-slate-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors" title="Xóa học sinh">
                      <span className="material-symbols-outlined text-xl">delete</span>
                    </button>
                  </div>
                </td>
              </tr>
              <tr className="bg-white hover:bg-slate-50 transition-colors">
                <td className="py-6 px-8 text-center font-medium text-slate-500">3</td>
                <td className="py-6 px-8 font-mono font-medium text-slate-600">HS003</td>
                <td className="py-6 px-8">
                  <div className="flex items-center gap-3">
                    <div className="size-10 rounded-full bg-amber-100 text-amber-600 flex items-center justify-center font-bold text-sm">LH</div>
                    <span className="font-bold text-slate-900 text-base">Lê Văn Hùng</span>
                  </div>
                </td>
                <td className="py-6 px-8 font-medium text-slate-600">0909 123 456</td>
                <td className="py-6 px-8 text-center">
                  <span className="inline-flex items-center px-3 py-2 rounded-full bg-red-100 text-red-700 font-bold text-sm">
                    Nghỉ học
                  </span>
                </td>
                <td className="py-6 px-8 text-right">
                  <div className="flex items-center justify-end gap-2">
                    <button className="inline-flex items-center gap-1 px-4 py-2 bg-blue-50 text-blue-700 hover:bg-blue-100 rounded-lg font-semibold transition-colors text-sm">
                      <span className="material-symbols-outlined text-base">visibility</span>
                      Xem điểm
                    </button>
                    <button className="p-2 text-slate-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors" title="Xóa học sinh">
                      <span className="material-symbols-outlined text-xl">delete</span>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="p-6 border-t border-slate-200 flex items-center justify-between bg-white">
          <span className="text-sm text-slate-500">Hiển thị 3 trên 45 học sinh</span>
          <div className="flex gap-2">
            <button className="px-3 py-2 border border-slate-200 rounded hover:bg-slate-50 disabled:opacity-50 text-slate-600" disabled>Trước</button>
            <button className="px-3 py-2 bg-primary text-white rounded font-medium">1</button>
            <button className="px-3 py-2 border border-slate-200 rounded hover:bg-slate-50 text-slate-600">2</button>
            <button className="px-3 py-2 border border-slate-200 rounded hover:bg-slate-50 text-slate-600">3</button>
            <span className="px-2 py-2 text-slate-400">...</span>
            <button className="px-3 py-2 border border-slate-200 rounded hover:bg-slate-50 text-slate-600">9</button>
            <button className="px-3 py-2 border border-slate-200 rounded hover:bg-slate-50 disabled:opacity-50 text-slate-600">Sau</button>
          </div>
        </div>
      </div>
    </div>
  );
}
