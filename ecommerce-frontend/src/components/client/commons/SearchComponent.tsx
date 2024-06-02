import { FormEvent, useRef } from 'react';

export const SearchComponent: React.FC<{ searchKeyword: (searchKey: string) => void }> = ({ searchKeyword }) => {
  const searchKeyRef = useRef<HTMLInputElement>(null);

  const handleSearch = (event: FormEvent) => {
    event.preventDefault();
    if (searchKeyRef.current) {
      searchKeyword(searchKeyRef.current.value);
    }
  };

  return (
    <form onSubmit={handleSearch} className="max-w-xl mt-5 mx-auto">
      <label htmlFor="default-search" className="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Search</label>
      <div className="relative">
        {/* Search Icon */}
        <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
          <img src='public/search_icon.svg' alt='search' />
        </div>
        <input ref={searchKeyRef} type="text" id="default-search" className="block w-full pe-24 py-4 ps-12 text-base text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Can I help you?" required />
        <button type="submit" className="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Search</button>
      </div>
    </form>
  )
}