import { FormEvent, useEffect, useRef, useState } from "react";
import { signIn } from "../../services/user/AuthService";
import { useNavigate } from "react-router-dom";
import { useCookies } from 'react-cookie'
import { getInforById } from "../../services/user/InforServices";
import { getUserByUsername } from "../../services/user/UserServices";
import { Infor } from "../../models/user/entity/Infor";
import { User } from "../../models/user/entity/User";
import { PaginationModel } from "../../models/commons/PaginationModel";
import { message } from "antd";

export const SignInComponent: React.FC<{}> = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const [cookies, setCookies] = useCookies(['username', 'roleId', 'accessToken']);
  const [messageApi, contextHolder] = message.useMessage();
  const [pagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 5
  });

  const navigator = useNavigate();

  useEffect(() => {
    fetchUserData();
  }, [cookies.username, navigator]);

  const fetchUserData = async () => {
    if (cookies.username) {
      try {
        const user = await fetchUser(cookies.username);
        console.log(JSON.stringify(user));
        if (user) {
          localStorage.setItem('userData', JSON.stringify(user));
          if (cookies.roleId == 1) {
            navigator('/admin');
          } else {
            navigator('/home');
          }
        } else {
          console.error('User not found');
        }
      } catch (error) {
        console.error('Error fetching user:', error);
      }
    } else {
      console.error('Username not found in Cookies');
    }
  };

  const fetchUser = async (username: string): Promise<User | null> => {
    try {
      const res = await getUserByUsername(username, pagination);
      const userData = res.data;

      if (!userData) {
        throw new Error('User not found');
      }

      const infor = await fetchInfor(userData.inforId);

      const user: User = {
        id: userData.id,
        firstName: userData.firstName,
        lastName: userData.lastName,
        username: userData.username,
        email: userData.email,
        password: userData.password,
        phoneNo: userData.phoneNo,
        activeMode: userData.activeMode,
        roleId: userData.roleId,
        infor: {
          id: infor.id,
          address: infor.address,
          street: infor.street,
          ward: infor.ward,
          district: infor.district,
          city: infor.city,
          country: infor.country,
          postalCode: infor.postalCode
        },
        cartId: userData.cartId,
        orders: userData.orders,
        ratings: userData.ratings,
      };

      return user;
    } catch (err) {
      console.error(err);
      return null;
    }
  };

  const fetchInfor = async (inforId: number): Promise<Infor> => {
    try {
      const inforResponse = await getInforById(inforId);
      const inforData = inforResponse.data;

      if (!inforData) {
        throw new Error('Infor not found');
      }

      const infor: Infor = {
        id: inforData.id,
        address: inforData.address,
        street: inforData.street,
        ward: inforData.ward,
        district: inforData.district,
        city: inforData.city,
        country: inforData.country,
        postalCode: inforData.postalCode,
      };

      return infor;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const handleSignIn = async (event: FormEvent) => {
    event.preventDefault();
    messageApi
      .open({
        type: 'loading',
        content: 'Checking sign in...',
        // duration: 2.5,
      })
      .then(async () => {
        const signInData = {
          email: emailRef.current?.value ?? '',
          password: passwordRef.current?.value ?? '',
        };

        const res = await signIn(signInData);
        console.log(res.data);
        if (res.status == 200 || res.status == 201) {
          setCookies('username', res.data?.username);
          setCookies('roleId', res.data?.roleId);
          setCookies('accessToken', res.data?.accessToken);
          message.success('Sign in successful', 2);
        } else {
          console.log(res);
          message.error(res.statusText)
        }
      })

  };

  return (
    <div className="flex justify-center items-center h-dvh">
      <div className="w-full max-w-sm p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-6 md:p-8 dark:bg-gray-800 dark:border-gray-700">
        {contextHolder}
        <form onSubmit={handleSignIn} className="space-y-6" action="#">
          <h5 className="text-xl font-medium text-gray-900 dark:text-white">Sign in to our platform</h5>
          <div>
            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your email</label>
            <input ref={emailRef} type="email" name="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="name@company.com" required />
          </div>
          <div>
            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your password</label>
            <input ref={passwordRef} type="password" name="password" id="password" placeholder="••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
          </div>
          <button type="submit" className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Log in your account</button>
          <div className="text-sm font-medium text-gray-500 dark:text-gray-300">
            Not registered? <a href="#" className="text-blue-700 hover:underline dark:text-blue-500">Create account</a>
          </div>
        </form>
      </div>
    </div>
  );
}
