import React from 'react';

export const FooterComponent: React.FC<{}> = () => {
  return (
    <footer className="static bg-white dark:bg-gray-900">
      <div className="w-full max-w-screen-xl mx-auto md:py-4">
        <hr className="my-6 border-gray-200 sm:mx-auto dark:border-gray-700 lg:my-4" />
        <span className="block text-sm text-gray-500 sm:text-center dark:text-gray-400">© 2024 <a href="https://flowbite.com/" className="hover:underline">Flowbite™</a>. All Rights Reserved.</span>
      </div>
    </footer>
  )
};
