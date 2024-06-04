
export const FooterComponent: React.FC<{}> = () => {
  return (
    <footer className="bg-white rounded-lg shadow sm:flex sm:items-center sm:justify-between p-4 sm:p-6 xl:p-8 dark:bg-gray-800 antialiased">
      <p className="mb-4 text-sm text-center mx-auto text-gray-500 dark:text-gray-400 sm:mb-0">
        &copy; 2024 <a href="https://flowbite.com/" className="hover:underline font-semibold" target="_blank">NRStore.com</a>. All rights reserved.
      </p>
    </footer>
  )
};
