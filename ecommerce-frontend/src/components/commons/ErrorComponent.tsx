import React from 'react'
import { useNavigate } from 'react-router-dom'

export const ErrorComponent: React.FC<{ code: number, message: string }> = ({ code, message }) => {
  const navigator = useNavigate();
  const backToSignIn = () => { navigator('/') }
  return (
    <section className="bg-white dark:bg-gray-900" >
      <div className="py-8 px-4 mx-auto max-w-screen-xl lg:py-16 lg:px-6">
        <div className="mx-auto max-w-screen content-center text-center">
          <a className="grid content-center">
            <img className="mx-auto w-30" alt="404 Error Illustration" src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/404/404-computer.svg" />
          </a>

          <h1 className="mb-4 text-5xl tracking-tight font-extrabold lg:text-9xl text-primary-600 dark:text-primary-500">{code}</h1>
          <p className="mb-4 text-xl tracking-tight font-bold text-gray-900 md:text-4xl dark:text-white">{message}</p>
          <button onClick={backToSignIn} className="inline-flex text-white bg-primary-600 hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:focus:ring-primary-900 my-4">Back to Homepage</button>
        </div>
      </div>
    </section >
    // <Result
    //   status={code}
    //   title={code}
    //   subTitle={message}
    //   extra={<Button onClick={() => <Navigate to={'/sign_in'} />} type="primary">Back Home</Button>}
    // />
  )
}
