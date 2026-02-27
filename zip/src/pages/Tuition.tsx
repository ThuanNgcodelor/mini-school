import { useState } from 'react';

export default function Tuition() {
  const [isPaymentModalOpen, setIsPaymentModalOpen] = useState(false);

  return (
    <div className="space-y-8">
      <div className="space-y-4">
        <div className="flex flex-col md:flex-row md:items-end justify-between gap-4">
          <div>
            <h2 className="text-2xl font-black text-slate-900 uppercase tracking-tight">Chi tiết tình trạng thu phí</h2>
            <p className="text-slate-500 font-medium text-lg mt-1">Lớp: Toán 10A | Mức thu: <span className="text-primary font-bold">500.000đ / tháng</span></p>
          </div>
          <div className="flex gap-3">
            <button className="flex items-center gap-2 px-5 py-2.5 bg-white border border-slate-200 rounded-xl font-bold text-sm shadow-sm hover:bg-slate-50 transition-all">
              <span className="material-symbols-outlined text-lg">download</span>
              Xuất báo cáo
            </button>
            <button className="flex items-center gap-2 px-5 py-2.5 bg-primary text-white rounded-xl font-bold text-sm shadow-lg shadow-primary/30 hover:opacity-90 transition-all">
              <span className="material-symbols-outlined text-lg">add</span>
              Thêm học sinh
            </button>
          </div>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-primary p-8 rounded-2xl shadow-xl shadow-primary/20 relative overflow-hidden group">
            <div className="absolute -right-8 -bottom-8 text-white/10 group-hover:scale-110 transition-transform duration-500">
              <span className="material-symbols-outlined text-[160px]">payments</span>
            </div>
            <div className="relative z-10">
              <p className="text-white/80 font-semibold mb-2">Tổng học phí đã thu</p>
              <h3 className="text-4xl font-extrabold text-white">25.000.000đ</h3>
              <div className="mt-4 flex items-center gap-2 text-emerald-300">
                <span className="material-symbols-outlined text-lg">trending_up</span>
                <span className="font-bold">+12% so với tháng trước</span>
              </div>
            </div>
          </div>
          <div className="bg-rose-600 p-8 rounded-2xl shadow-xl shadow-rose-600/20 relative overflow-hidden group">
            <div className="absolute -right-8 -bottom-8 text-white/10 group-hover:scale-110 transition-transform duration-500">
              <span className="material-symbols-outlined text-[160px]">account_balance_wallet</span>
            </div>
            <div className="relative z-10">
              <p className="text-white/80 font-semibold mb-2">Tổng học phí còn nợ</p>
              <h3 className="text-4xl font-extrabold text-white">4.500.000đ</h3>
              <div className="mt-4 flex items-center gap-2 text-rose-200">
                <span className="material-symbols-outlined text-lg">warning</span>
                <span className="font-bold">9 học sinh chưa hoàn thành</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="bg-white rounded-2xl border border-slate-200 overflow-hidden table-shadow">
        <div className="p-6 border-b border-slate-100 flex justify-between items-center bg-slate-50/50">
          <h4 className="font-bold text-lg">Danh sách học phí chi tiết</h4>
          <div className="relative w-72">
            <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 text-xl">search</span>
            <input className="w-full pl-10 pr-4 py-2 bg-white border border-slate-200 rounded-xl focus:ring-primary focus:border-primary text-sm" placeholder="Tìm tên học sinh..." type="text" />
          </div>
        </div>
        <div className="overflow-x-auto scrollbar-hide">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-slate-50 text-slate-500 uppercase text-xs font-bold tracking-wider border-b border-slate-200">
                <th className="px-6 py-4 sticky-col bg-slate-50 z-20 border-r border-slate-200">STT</th>
                <th className="px-6 py-4 sticky-col left-16 bg-slate-50 z-20 w-64 border-r border-slate-200 shadow-[2px_0_5px_rgba(0,0,0,0.05)]">Họ và Tên</th>
                <th className="px-6 py-4 text-center">T1</th>
                <th className="px-6 py-4 text-center">T2</th>
                <th className="px-6 py-4 text-center">T3</th>
                <th className="px-6 py-4 text-center">T4</th>
                <th className="px-6 py-4 text-center">T5</th>
                <th className="px-6 py-4 text-center">T6</th>
                <th className="px-6 py-4 text-center">T7</th>
                <th className="px-6 py-4 text-center">T8</th>
                <th className="px-6 py-4 text-center">T9</th>
                <th className="px-6 py-4 text-center">T10</th>
                <th className="px-6 py-4 text-center">T11</th>
                <th className="px-6 py-4 text-center">T12</th>
                <th className="px-6 py-4 text-right">Thao tác</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-200">
              <tr className="hover:bg-slate-50/80 transition-colors">
                <td className="px-6 py-5 font-semibold text-slate-400 sticky-col bg-white border-r border-slate-200">01</td>
                <td className="px-6 py-5 font-bold text-slate-900 sticky-col left-16 bg-white border-r border-slate-200 shadow-[2px_0_5px_rgba(0,0,0,0.05)]">Nguyễn Văn An</td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-emerald-500 text-3xl" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-emerald-500 text-3xl" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-right whitespace-nowrap">
                  <button onClick={() => setIsPaymentModalOpen(true)} className="bg-primary/10 text-primary hover:bg-primary hover:text-white px-4 py-1.5 rounded-lg text-sm font-bold transition-all">Ghi nhận</button>
                </td>
              </tr>
              <tr className="hover:bg-slate-50/80 transition-colors">
                <td className="px-6 py-5 font-semibold text-slate-400 sticky-col bg-white border-r border-slate-200">02</td>
                <td className="px-6 py-5 font-bold text-slate-900 sticky-col left-16 bg-white border-r border-slate-200 shadow-[2px_0_5px_rgba(0,0,0,0.05)]">Trần Thị Bình</td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-emerald-500 text-3xl" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-emerald-500 text-3xl" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-emerald-500 text-3xl" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-emerald-500 text-3xl" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-rose-500 text-3xl" style={{ fontVariationSettings: "'FILL' 1" }}>cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-center">
                  <span className="material-symbols-outlined text-slate-300 text-3xl">cancel</span>
                </td>
                <td className="px-6 py-5 text-right whitespace-nowrap">
                  <button onClick={() => setIsPaymentModalOpen(true)} className="bg-primary/10 text-primary hover:bg-primary hover:text-white px-4 py-1.5 rounded-lg text-sm font-bold transition-all">Ghi nhận</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="p-6 border-t border-slate-100 bg-slate-50/30">
          <div className="flex flex-col md:flex-row items-center justify-between gap-4">
            <p className="text-sm text-slate-500 font-medium">Hiển thị 1-2 trên tổng số 45 học sinh</p>
            <div className="flex items-center gap-2">
              <button className="p-2 rounded-lg border border-slate-200 hover:bg-white transition-all disabled:opacity-50" disabled>
                <span className="material-symbols-outlined">chevron_left</span>
              </button>
              <button className="size-10 rounded-lg bg-primary text-white font-bold text-sm">1</button>
              <button className="size-10 rounded-lg border border-slate-200 hover:bg-white transition-all font-bold text-sm">2</button>
              <button className="size-10 rounded-lg border border-slate-200 hover:bg-white transition-all font-bold text-sm">3</button>
              <button className="p-2 rounded-lg border border-slate-200 hover:bg-white transition-all">
                <span className="material-symbols-outlined">chevron_right</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-wrap gap-8 px-2 py-4 border-t border-slate-200">
        <div className="flex items-center gap-2">
          <span className="material-symbols-outlined text-emerald-500 text-2xl" style={{ fontVariationSettings: "'FILL' 1" }}>check_circle</span>
          <span className="text-sm font-semibold text-slate-600">Đã hoàn thành đóng phí</span>
        </div>
        <div className="flex items-center gap-2">
          <span className="material-symbols-outlined text-rose-500 text-2xl" style={{ fontVariationSettings: "'FILL' 1" }}>cancel</span>
          <span className="text-sm font-semibold text-slate-600">Đang nợ phí (Quá hạn)</span>
        </div>
        <div className="flex items-center gap-2">
          <span className="material-symbols-outlined text-slate-300 text-2xl">cancel</span>
          <span className="text-sm font-semibold text-slate-600">Chưa đến kỳ đóng/Chưa đóng</span>
        </div>
      </div>

      {isPaymentModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center modal-overlay p-4">
          <div className="bg-white w-full max-w-[520px] rounded-xl shadow-2xl flex flex-col overflow-hidden">
            <div className="p-6 border-b border-slate-100 flex justify-between items-center">
              <h2 className="text-2xl font-bold text-slate-900 leading-tight">Ghi nhận đóng tiền</h2>
              <button onClick={() => setIsPaymentModalOpen(false)} className="text-slate-400 hover:text-slate-600">
                <span className="material-symbols-outlined">close</span>
              </button>
            </div>
            <div className="p-6">
              <div className="mb-6">
                <p className="text-slate-500 text-sm font-medium uppercase tracking-wider mb-2">Học sinh</p>
                <div className="flex items-center gap-3">
                  <div className="w-12 h-12 rounded-full bg-primary/10 flex items-center justify-center text-primary">
                    <span className="material-symbols-outlined text-2xl">account_circle</span>
                  </div>
                  <p className="text-xl font-bold text-slate-900">Nguyễn Văn Minh</p>
                </div>
              </div>
              <div className="space-y-1 mb-8">
                <p className="text-slate-500 text-sm font-medium uppercase tracking-wider mb-3">Chọn tháng cần thanh toán</p>
                <label className="flex items-center justify-between p-4 rounded-lg border border-slate-200 hover:bg-slate-50 cursor-pointer transition-colors">
                  <div className="flex items-center gap-3">
                    <div className="w-10 h-10 rounded-lg bg-slate-100 flex items-center justify-center">
                      <span className="material-symbols-outlined text-slate-500">calendar_month</span>
                    </div>
                    <div>
                      <p className="font-semibold text-slate-900">Học phí Tháng 3</p>
                      <p className="text-sm text-slate-500">Hạn chót: 31/03/2024</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-4">
                    <span className="font-bold text-slate-900">500.000đ</span>
                    <input defaultChecked className="w-6 h-6 rounded border-slate-300 text-primary focus:ring-primary" type="checkbox" />
                  </div>
                </label>
                <label className="flex items-center justify-between p-4 rounded-lg border border-slate-200 hover:bg-slate-50 cursor-pointer transition-colors">
                  <div className="flex items-center gap-3">
                    <div className="w-10 h-10 rounded-lg bg-slate-100 flex items-center justify-center">
                      <span className="material-symbols-outlined text-slate-500">calendar_month</span>
                    </div>
                    <div>
                      <p className="font-semibold text-slate-900">Học phí Tháng 4</p>
                      <p className="text-sm text-slate-500">Hạn chót: 30/04/2024</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-4">
                    <span className="font-bold text-slate-900">500.000đ</span>
                    <input defaultChecked className="w-6 h-6 rounded border-slate-300 text-primary focus:ring-primary" type="checkbox" />
                  </div>
                </label>
                <label className="flex items-center justify-between p-4 rounded-lg border border-slate-200 hover:bg-slate-50 cursor-pointer transition-colors">
                  <div className="flex items-center gap-3">
                    <div className="w-10 h-10 rounded-lg bg-slate-100 flex items-center justify-center">
                      <span className="material-symbols-outlined text-slate-500">calendar_month</span>
                    </div>
                    <div>
                      <p className="font-semibold text-slate-900">Học phí Tháng 5</p>
                      <p className="text-sm text-slate-500">Hạn chót: 31/05/2024</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-4">
                    <span className="font-bold text-slate-900">500.000đ</span>
                    <input defaultChecked className="w-6 h-6 rounded border-slate-300 text-primary focus:ring-primary" type="checkbox" />
                  </div>
                </label>
              </div>
              <div className="flex justify-between items-center p-5 bg-primary/5 rounded-xl border border-primary/10">
                <span className="text-lg font-medium text-slate-700">Tổng tiền thanh toán</span>
                <span className="text-2xl font-black text-primary">1.500.000đ</span>
              </div>
            </div>
            <div className="p-6 pt-2 flex flex-col gap-3">
              <button onClick={() => setIsPaymentModalOpen(false)} className="w-full bg-primary hover:bg-primary/90 text-white font-bold py-4 rounded-lg text-lg shadow-lg shadow-primary/20 transition-all flex items-center justify-center gap-2">
                <span className="material-symbols-outlined">payments</span>
                Xác nhận đã thu tiền
              </button>
              <button onClick={() => setIsPaymentModalOpen(false)} className="w-full bg-transparent hover:bg-slate-100 text-slate-600 font-semibold py-3 rounded-lg transition-all">
                Hủy bỏ
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
