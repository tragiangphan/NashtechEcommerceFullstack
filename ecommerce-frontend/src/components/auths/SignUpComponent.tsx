import { message } from 'antd';
import { FormEvent, useRef } from 'react'
import { useNavigate } from 'react-router-dom';
import { signUp } from '../../services/user/AuthService';

export const SignUpComponent: React.FC<{}> = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const confirmPassRef = useRef<HTMLInputElement>(null);
  const [messageApi, contextHolder] = message.useMessage();
  const navigator = useNavigate();

  const handleSignUp = async (event: FormEvent) => {
    event.preventDefault();
    messageApi
      .open({
        type: 'loading',
        content: 'Create new account...',
        // duration: 2.5,
      })
      .then(async () => {
        const signUpData = {
          email: emailRef.current?.value ?? '',
          password: passwordRef.current?.value ?? '',
          role: 'ROLE_USER'
        };
        console.log(signUpData);
        
        if (passwordRef.current?.value != confirmPassRef.current?.value) {
          message.error("Confirm password not right!")
        } else {
          const signUpForm = await signUp(signUpData);
          console.log(signUpForm);
          if(signUpForm.status == 201 || signUpForm.status == 200) {
            message.success("Create new account success");
            navigator('/sign_in');
          } else if (signUpForm.status == 409) {
            message.error("Email already exists!");
          } else {
            message.error("Create new account failure! Please sign up again!")
          }
        }
      })
    
  }

  return (
    <div className="flex justify-center items-center h-dvh">
      {contextHolder}
      <div className="w-full max-w-sm p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-6 md:p-8 dark:bg-gray-800 dark:border-gray-700">
        <form onSubmit={handleSignUp} className="space-y-6" action="#">
          <h5 className="text-xl font-medium text-gray-900 dark:text-white">Let's create your Account</h5>
          <div>
            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your email</label>
            <input ref={emailRef} type="email" name="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="name@company.com" required />
          </div>
          <div>
            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your password</label>
            <input ref={passwordRef} type="password" name="password" id="password" placeholder="••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
          </div>
          <div>
            <label htmlFor="passwordConfirm" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Confirm password</label>
            <input ref={confirmPassRef} type="password" name="passwordConfirm" id="passwordConfirm" placeholder="••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
          </div>
          <button type="submit" className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Register your account</button>
          <div className="text-sm font-medium text-gray-500 dark:text-gray-300">
            Already have an account? <a href="http://localhost:4000/" className="text-blue-700 hover:underline dark:text-blue-500">Log in</a>
          </div>
        </form>
      </div>
    </div>
  )
}
