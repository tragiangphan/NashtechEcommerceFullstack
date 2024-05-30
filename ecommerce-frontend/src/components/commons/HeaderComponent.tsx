import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export const HeaderComponent: React.FC<{}> = () => {
  const [currentPage, setCurrentPage] = useState('home'); // Initial state
  const navigator = useNavigate();

  const handleNavigationClick = (page: string) => {
    setCurrentPage(page);
    navigator(`/${page}`);
  };

  return (
    <nav className="bg-white border-gray-200 dark:bg-gray-900">
      <div className="flex flex-wrap items-center justify-between max-w-screen-xl mx-auto p-4">
        <a href="#" className="flex items-center space-x-3 rtl:space-x-reverse">
          <img src="public/vite.svg" className="h-8" alt="Flowbite Logo" />
          <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">NRS Store</span>
        </a>
        <div className="flex items-center md:order-2 space-x-1 md:space-x-2 rtl:space-x-reverse">
          <button className="text-gray-800 dark:text-white hover:bg-gray-50 focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 py-2 md:px-5 md:py-2.5 dark:hover:bg-gray-700 focus:outline-none dark:focus:ring-gray-800">Login</button>
          <button className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 md:px-5 md:py-2.5 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Sign up</button>
        </div>
        <div id="mega-menu" className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1">
          <ul className="flex flex-col mt-4 font-medium md:flex-row md:mt-0 md:space-x-8 rtl:space-x-reverse">
            <li>
              <button
                onClick={() => handleNavigationClick('home')}
                className={`block py-2 px-3 ${currentPage === 'home' ? 'text-blue-600' : 'text-gray-900'} border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-blue-500 md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700`}
                aria-current={currentPage === 'home' ? 'page' : undefined}>
                Home
              </button>
            </li>
            <li>
              <button
                onClick={() => handleNavigationClick('store')}
                className={`block py-2 px-3 ${currentPage === 'store' ? 'text-blue-600' : 'text-gray-900'} border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700`}
                aria-current={currentPage === 'store' ? 'page' : undefined}>
                Store
              </button>
            </li>
            <li>
              <button
                onClick={() => handleNavigationClick('about')}
                className={`block py-2 px-3 ${currentPage === 'about' ? 'text-blue-600' : 'text-gray-900'} border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-600 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-blue-500 md:dark:hover:bg-transparent dark:border-gray-700`}
                aria-current={currentPage === 'about' ? 'page' : undefined}>
                About
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
