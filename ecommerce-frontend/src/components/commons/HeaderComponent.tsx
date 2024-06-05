import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import { User } from "../../models/user/entity/User";

export const HeaderComponent: React.FC<{}> = () => {
  const [currentPage, setCurrentPage] = useState('home');
  const [showButton, setShowButton] = useState(false);
  const [user, setUser] = useState<User>();
  const [cookies, setCookies, removeCookies] = useCookies(['username', 'accessToken', 'roleId'])
  const location = useLocation();
  const navigator = useNavigate();

  useEffect(() => {
    const currentPath = location.pathname;
    const userData = localStorage.getItem("userData");
    if (userData) {
      const parsedUser = JSON.parse(userData);
      setUser(parsedUser);
    } else {
      navigator('/')
    }
    if (currentPath === '/') {
      setShowButton(true);
    } else {
      setShowButton(false);
    }
  }, [location.pathname]);

  const handleNavigationClick = (page: string) => {
    setCurrentPage(page);
    navigator(`/${page}`);
  };

  const handleLogOut = () => {
    removeCookies('username');
    removeCookies('accessToken');
    removeCookies('roleId');
    setShowButton(true);
    navigator(`/`);
    localStorage.removeItem('userData');
    localStorage.removeItem('productDetail');
  }

  return (
    <nav className="bg-white border-gray-200 dark:bg-gray-900">
      <div className="flex flex-wrap items-center justify-between max-w-screen-xl mx-auto p-4">
        <a href="#" className="flex items-center space-x-3 rtl:space-x-reverse">
          <img src="/vite.svg" className="h-8" alt="Flowbite Logo" />
          <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">NRS Store</span>
        </a>
        {showButton ? (
          <div className="flex items-center md:order-2 space-x-1 md:space-x-2 rtl:space-x-reverse">
            <button className="text-gray-800 dark:text-white hover:bg-gray-50 focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 py-2 md:px-5 md:py-2.5 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800">Login</button>
            <button className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 md:px-5 md:py-2.5 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Sign up</button>
          </div>
        ) : (
          <div className="flex items-center md:order-2 space-x-1 md:space-x-2 rtl:space-x-reverse">
            <button onClick={() => handleNavigationClick(cookies.username)} className="text-gray-800 dark:text-white hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 rounded-lg text-base font-semibold px-4 py-3 md:px-5 md:py-3 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800">{cookies.username}</button>

            {!showButton && user?.roleId == 2 ? (
              <button type="button" className="relative inline-flex items-center me-3 p-3 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
              <svg className="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 16">
                <path d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z" />
                <path d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z" />
              </svg>
              <span className="sr-only">Notifications</span>
              <div className="absolute inline-flex items-center justify-center w-6 h-6 text-xs font-bold text-white bg-red-500 border-2 border-white rounded-full -top-2 -end-2 dark:border-gray-900">20</div>
            </button>
            ):(
              <div></div>
            )}

            <button onClick={handleLogOut} type="button" className="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-3.5 py-3 ms-5 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900">
              <svg className="w-5 h-5 mb-0.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M20 12H8m12 0-4 4m4-4-4-4M9 4H7a3 3 0 0 0-3 3v10a3 3 0 0 0 3 3h2" />
              </svg>
            </button>
          </div>
        )}
        {!showButton && user?.roleId == 2 ? (
          <div id="mega-menu" className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1">
            <ul className="flex flex-col mt-4 font-medium md:flex-row md:mt-0 md:space-x-8 rtl:space-x-reverse">
              <li>
                <button
                  onClick={() => handleNavigationClick('home')}
                  className={`block py-2 px-3 text-xl ${currentPage === 'home' ? 'text-blue-600' : 'text-gray-900'} border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-blue-500 md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700`}
                  aria-current={currentPage === 'home' ? 'page' : undefined}>
                  Home
                </button>
              </li>
              <li>
                <button
                  onClick={() => handleNavigationClick('store')}
                  className={`block py-2 px-3 text-xl ${currentPage === 'store' ? 'text-blue-600' : 'text-gray-900'} border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700`}
                  aria-current={currentPage === 'store' ? 'page' : undefined}>
                  Store
                </button>
              </li>
              <li>
                <button
                  onClick={() => handleNavigationClick('about')}
                  className={`block py-2 px-3 text-xl ${currentPage === 'about' ? 'text-blue-600' : 'text-gray-900'} border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700`}
                  aria-current={currentPage === 'about' ? 'page' : undefined}>
                  About
                </button>
              </li>
            </ul>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </nav>
  );
}
