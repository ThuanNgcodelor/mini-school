import { Link, useLocation } from 'react-router-dom';
import { ReactNode } from 'react';

export default function Layout({ children }: { children: ReactNode }) {
  const location = useLocation();

  const navItems = [
    { path: '/dashboard', icon: 'dashboard', label: 'Tổng quan' },
    { path: '/class', icon: 'school', label: 'Lớp học' },
    { path: '/students', icon: 'groups', label: 'Học sinh' },
    { path: '/tuition', icon: 'payments', label: 'Học phí' },
    { path: '/settings', icon: 'settings', label: 'Cài đặt' },
  ];

  return (
    <div className="bg-white text-slate-900 font-display min-h-screen flex overflow-hidden">
      <aside className="w-64 bg-white border-r border-slate-200 flex-shrink-0 flex flex-col h-screen fixed lg:static z-20">
        <div className="h-20 flex items-center px-6 border-b border-slate-200">
          <div className="flex items-center gap-3 text-primary">
            <div className="size-8">
              <svg className="w-full h-full" fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
                <path d="M6 6H42L36 24L42 42H6L12 24L6 6Z" fill="currentColor"></path>
              </svg>
            </div>
            <h2 className="text-xl font-bold tracking-tight text-slate-900">EduManager</h2>
          </div>
        </div>
        <nav className="flex-1 overflow-y-auto py-6 px-4 space-y-2">
          {navItems.map((item, index) => {
            const isActive = location.pathname.startsWith(item.path);
            if (item.path === '/settings') {
              return (
                <div key={item.path}>
                  <div className="border-t border-slate-200 my-4"></div>
                  <Link
                    to={item.path}
                    className={`flex items-center gap-4 px-4 py-3 rounded-lg transition-colors group ${
                      isActive
                        ? 'bg-primary/10 text-primary'
                        : 'text-slate-600 hover:bg-slate-50 hover:text-primary'
                    }`}
                  >
                    <span className={`material-symbols-outlined ${isActive ? 'filled' : 'group-hover:text-primary transition-colors'}`}>
                      {item.icon}
                    </span>
                    <span className={`${isActive ? 'font-bold' : 'font-medium'} text-lg`}>{item.label}</span>
                  </Link>
                </div>
              );
            }
            return (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center gap-4 px-4 py-3 rounded-lg transition-colors group ${
                  isActive
                    ? 'bg-primary/10 text-primary'
                    : 'text-slate-600 hover:bg-slate-50 hover:text-primary'
                }`}
              >
                <span className={`material-symbols-outlined ${isActive ? 'filled' : 'group-hover:text-primary transition-colors'}`}>
                  {item.icon}
                </span>
                <span className={`${isActive ? 'font-bold' : 'font-medium'} text-lg`}>{item.label}</span>
              </Link>
            );
          })}
        </nav>
      </aside>
      <div className="flex-1 flex flex-col h-screen overflow-hidden">
        <header className="h-20 bg-white border-b border-slate-200 flex items-center justify-between px-6 lg:px-10 flex-shrink-0">
          <h1 className="text-2xl font-bold text-slate-800 hidden lg:block">
            {navItems.find(item => location.pathname.startsWith(item.path))?.label || 'Dashboard'}
          </h1>
          <div className="flex items-center gap-6 ml-auto">
            <button className="relative p-2 text-slate-500 hover:text-primary transition-colors">
              <span className="material-symbols-outlined">notifications</span>
              <span className="absolute top-1.5 right-1.5 size-2.5 bg-red-500 rounded-full border-2 border-white"></span>
            </button>
            <div className="flex items-center gap-3 pl-6 border-l border-slate-200">
              <img alt="Avatar" className="size-10 rounded-full ring-2 ring-primary/20" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAQaY-RHsQNqK-jTSY7_foXs5BlWqJE8_PaevspgGYOq1v1JzC2eizhEydU-a6XQRFVBp7nh_wLpyxN7DofPzaa6qfQfgCn0aFcGrZufG1lc87r1Rx5qkDdgpuBJbwMrTKQ_VBvRD5qNPLYvqdN9xEOIz6ZPG5_y6LqqWdmqTvoSirCvuuxYajMHgVNaLOBm2JUOKZTTE2HPgamX4seKwjizlJw4n2tZxOGFvDmclhGbmRE7FJHPmRCdjCI_FJ99gS4_UTcQpePbdw" />
              <div className="hidden md:block">
                <p className="text-base font-bold text-slate-900">Thầy Hùng</p>
                <p className="text-sm text-slate-500">Giáo viên Toán</p>
              </div>
            </div>
            <Link to="/login" className="flex items-center gap-2 px-4 py-2 text-slate-600 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors text-base font-medium">
              <span className="material-symbols-outlined text-xl">logout</span>
              <span className="hidden md:inline">Đăng xuất</span>
            </Link>
          </div>
        </header>
        <main className="flex-1 overflow-y-auto p-6 lg:p-10 bg-gray-50">
          {children}
        </main>
      </div>
    </div>
  );
}
