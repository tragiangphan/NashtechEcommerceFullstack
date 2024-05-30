import React from 'react'

export const ThumbSearch: React.FC<{}> = () => {
  return (
    <section className="bg-white relative dark:bg-gray-900 bg-[url('https://flowbite.s3.amazonaws.com/docs/jumbotron/hero-pattern.svg')] dark:bg-[url('https://flowbite.s3.amazonaws.com/docs/jumbotron/hero-pattern-dark.svg')]">
      <div className="py-8 px-4 mx-auto max-w-screen-xl text-center lg:py-16 z-10 relative">
        <h1 className="mb-4 text-4xl font-extrabold tracking-tight leading-none text-gray-900 md:text-5xl lg:text-6xl dark:text-white">We invest in the world’s potential</h1>
        <p className="mb-8 text-lg font-normal text-gray-500 lg:text-xl sm:px-16 lg:px-48 dark:text-gray-200">Lorem ipsum dolor sit amet consectetur adipisicing elit. Ab, rerum! Nobis officiis laboriosam aliquid, itaque quod aut aliquam blanditiis, eveniet aspernatur fugiat quasi sit quibusdam eligendi ipsam vero dolore dignissimos.</p>
        <form className="w-full max-w-md mx-auto">
          <label htmlFor="default-email" className="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Email sign-up</label>
          <div className="relative">
            {/* Email icon */}
            <div className="absolute inset-y-0 rtl:inset-x-0 start-0 flex items-center ps-3.5 me-3.5 pointer-events-none">
              <img src='public/search_icon.svg' alt='search' />
            </div>
            <input type="email" id="default-email" className="block w-full p-4 ps-12 text-md text-gray-900 border border-gray-300 rounded-lg bg-white focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-800 dark:border-gray-700 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search product here..." required />
            <button type="submit" className="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Search</button>
          </div>
        </form>
      </div>
      <div className="bg-gradient-to-b from-blue-50 to-transparent dark:from-blue-900 w-full h-full absolute top-0 left-0 z-0"></div>
    </section>

  )
}
