import { Link } from 'react-router-dom';

export default function Login() {
  return (
    <div className="bg-background-light dark:bg-background-dark text-slate-900 dark:text-slate-100 font-display min-h-screen flex flex-col">
      <header className="flex items-center justify-between whitespace-nowrap border-b border-solid border-slate-200 dark:border-slate-800 bg-white dark:bg-slate-900 px-6 lg:px-10 py-4 shadow-sm">
        <div className="flex items-center gap-4 text-slate-900 dark:text-white">
          <div className="size-8 text-primary">
            <svg className="w-full h-full" fill="none" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
              <path d="M6 6H42L36 24L42 42H6L12 24L6 6Z" fill="currentColor"></path>
            </svg>
          </div>
          <h2 className="text-xl font-bold leading-tight tracking-tight">Quản lý Lớp học</h2>
        </div>
        <div className="hidden md:flex flex-1 justify-end gap-8 items-center">
          <div className="flex items-center gap-9">
            <a className="text-slate-600 dark:text-slate-300 text-sm font-medium hover:text-primary dark:hover:text-primary transition-colors" href="#">Trang chủ</a>
            <a className="text-slate-600 dark:text-slate-300 text-sm font-medium hover:text-primary dark:hover:text-primary transition-colors" href="#">Tính năng</a>
            <a className="text-slate-600 dark:text-slate-300 text-sm font-medium hover:text-primary dark:hover:text-primary transition-colors" href="#">Trợ giúp</a>
          </div>
          <Link to="/register" className="flex min-w-[84px] cursor-pointer items-center justify-center overflow-hidden rounded-lg h-10 px-6 bg-primary/10 hover:bg-primary/20 text-primary dark:text-blue-400 text-sm font-bold leading-normal tracking-wide transition-colors">
            <span className="truncate">Đăng ký</span>
          </Link>
        </div>
      </header>
      <main className="flex-grow flex items-center justify-center p-4 py-12">
        <div className="w-full max-w-xl bg-white dark:bg-slate-900 rounded-xl shadow-lg border border-slate-200 dark:border-slate-800 overflow-hidden">
          <div className="px-8 pt-10 pb-6 text-center">
            <h1 className="text-3xl lg:text-4xl font-black text-slate-900 dark:text-white mb-3 tracking-tight">Đăng nhập Giáo viên</h1>
            <p className="text-slate-500 dark:text-slate-400 text-lg">Tiếp tục quản lý lớp học của bạn</p>
          </div>
          <form className="px-6 lg:px-12 py-6 flex flex-col gap-6">
            <div className="flex flex-col gap-2">
              <label className="text-slate-900 dark:text-slate-100 text-base font-bold" htmlFor="phone">Số điện thoại</label>
              <input className="form-input w-full rounded-lg border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white focus:border-primary focus:ring-2 focus:ring-primary/20 h-14 px-4 text-lg placeholder:text-slate-400 dark:placeholder:text-slate-500 transition-all" id="phone" placeholder="0912 345 678" type="tel" />
            </div>
            <div className="flex flex-col gap-2">
              <label className="text-slate-900 dark:text-slate-100 text-base font-bold" htmlFor="password">Mật khẩu</label>
              <div className="relative">
                <input className="form-input w-full rounded-lg border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white focus:border-primary focus:ring-2 focus:ring-primary/20 h-14 pl-4 pr-12 text-lg placeholder:text-slate-400 dark:placeholder:text-slate-500 transition-all" id="password" placeholder="******" type="password" />
                <button className="absolute right-0 top-0 h-full px-4 text-slate-400 hover:text-primary transition-colors flex items-center justify-center" type="button">
                  <span className="material-symbols-outlined">visibility_off</span>
                </button>
              </div>
            </div>
            <div className="flex items-center justify-between mt-1">
              <div className="flex items-center gap-2">
                <input className="size-5 rounded border-slate-300 text-primary focus:ring-primary/20 dark:border-slate-600 dark:bg-slate-800" id="remember" type="checkbox" />
                <label className="text-base text-slate-600 dark:text-slate-300 font-medium select-none" htmlFor="remember">
                  Ghi nhớ đăng nhập
                </label>
              </div>
              <a className="text-primary hover:text-blue-700 dark:text-blue-400 dark:hover:text-blue-300 font-bold text-base hover:underline" href="#">
                Quên mật khẩu?
              </a>
            </div>
            <Link to="/dashboard" className="mt-4 w-full bg-primary hover:bg-blue-700 text-white font-bold text-lg h-14 rounded-lg shadow-lg shadow-blue-500/20 active:scale-[0.99] transition-all duration-200 flex items-center justify-center gap-2">
              <span>Đăng nhập ngay</span>
              <span className="material-symbols-outlined">arrow_forward</span>
            </Link>
          </form>
          <div className="px-8 pb-10 pt-2 text-center">
            <p className="text-base text-slate-600 dark:text-slate-400">
              Chưa có tài khoản?
              <Link className="text-primary dark:text-blue-400 font-bold hover:underline ml-1 text-lg" to="/register">Đăng ký tại đây</Link>
            </p>
          </div>
        </div>
      </main>
      <div className="h-2 w-full bg-gradient-to-r from-blue-400 via-primary to-blue-600"></div>
    </div>
  );
}
