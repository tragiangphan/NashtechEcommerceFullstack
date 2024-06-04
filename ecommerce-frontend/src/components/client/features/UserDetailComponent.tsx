import { useEffect, useRef, useState } from 'react'
import { User } from '../../../models/user/entity/User';
import { useNavigate } from 'react-router-dom';
import { updateInfor } from '../../../services/user/InforServices';
import { InforRequest } from '../../../models/user/request/InforRequest';
import { updateUser } from '../../../services/user/UserServices';
import { UserRequest } from '../../../models/user/request/UserRequest';

export const UserDetailComponent: React.FC<{}> = () => {
  const [editable, setEditable] = useState<boolean>(false);
  const [userData, setUserData] = useState<User>();
  const firstnameRef = useRef<HTMLInputElement>(null);
  const lastnameRef = useRef<HTMLInputElement>(null);
  const emailRef = useRef<HTMLInputElement>(null);
  const phoneNoRef = useRef<HTMLInputElement>(null);
  const addressRef = useRef<HTMLInputElement>(null);
  const streetRef = useRef<HTMLInputElement>(null);
  const wardRef = useRef<HTMLInputElement>(null);
  const districtRef = useRef<HTMLInputElement>(null);
  const cityRef = useRef<HTMLInputElement>(null);
  const countryRef = useRef<HTMLInputElement>(null);
  const postalCodeRef = useRef<HTMLInputElement>(null);
  const navigator = useNavigate();

  useEffect(() => {
    const userDatas = localStorage.getItem('userData');
    if (userDatas) {
      const parsedUser = JSON.parse(userDatas);
      setUserData(parsedUser);
    } else {
      navigator('/');
    }
  }, [])

  useEffect(() => {
    console.log(editable);
  }, [editable]);

  const handleOnEditable = () => {
    setEditable(!editable);
  };

  const handleSubmitUpdate = (event: React.FormEvent) => {
    event.preventDefault();
    if (userData && userData.infor) {
      const inforReq: InforRequest = {
        userId: userData.id,
        address: addressRef.current?.value ?? userData.infor.address,
        street: streetRef.current?.value ?? userData.infor.street,
        ward: wardRef.current?.value ?? userData.infor.ward,
        district: districtRef.current?.value ?? userData.infor.district,
        city: cityRef.current?.value ?? userData.infor.city,
        country: countryRef.current?.value ?? userData.infor.country,
        postalCode: postalCodeRef.current?.value ?? userData.infor.postalCode
      };

      updateInfor(userData.infor.id, inforReq).then((res) => {
        console.log('Update successful', res.data);
      }).catch((err) => {
        console.error('Update failed', err);
      });

      const userReq: UserRequest = {
        firstName: firstnameRef.current?.value ?? userData?.firstName,
        lastName: lastnameRef.current?.value ?? userData?.lastName,
        email: emailRef.current?.value ?? userData?.email,
        password: userData?.password,
        phoneNo: phoneNoRef.current?.value ?? userData?.phoneNo,
        activeMode: userData?.activeMode,
        roleId: 2
      };

      updateUser(userData?.id, userReq).then((res) => {
        console.log('Update successful', res.data);
        setEditable(!editable);
      }).catch((err) => {
        console.error('Update failed', err);
      });
    }
  };

  return (
    <form onSubmit={handleSubmitUpdate} className="container mx-auto p-6">
      <div className="flex flex-row items-center justify-end w-full h-full my-3">
        <button onClick={handleOnEditable} type="button" className={editable ? "text-white bg-blue-400 dark:bg-blue-500 cursor-not-allowed font-medium rounded-lg text-sm px-10 py-2.5 me-2 text-center" : "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-10 py-2.5 me-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"} disabled={editable} >EDIT</button>
        <button type="submit" className={!editable ? "text-white bg-blue-400 dark:bg-blue-500 cursor-not-allowed font-medium rounded-lg text-sm px-7 py-2.5 text-center" : "text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-7 py-2.5 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"} disabled={!editable} >UPDATE</button>
      </div>

      <div className="grid gap-6 mb-6 md:grid-cols-2">
        <div>
          <label htmlFor="firstName" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First name</label>
          <input ref={firstnameRef} disabled={!editable} readOnly={!editable} type="text" id="firstName" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.firstName} required />
        </div>
        <div>
          <label htmlFor="lastName" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last name</label>
          <input ref={lastnameRef} disabled={!editable} readOnly={!editable} type="text" id="lastName" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.lastName} required />
        </div>
        <div>
          <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
          <input ref={emailRef} disabled={!editable} readOnly={!editable} type="text" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.email} required />
        </div>
        <div>
          <label htmlFor="phone" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone number</label>
          <input ref={phoneNoRef} disabled={!editable} readOnly={!editable} type="tel" id="phone" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.phoneNo} pattern="[0-9]{4}-[0-9]{3}-[0-9]{3}" required />
        </div>
        <div>
          <label htmlFor="address" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Address</label>
          <input ref={addressRef} disabled={!editable} readOnly={!editable} type="text" id="address" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.infor.address} required />
        </div>
        <div>
          <label htmlFor="street" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Street</label>
          <input ref={streetRef} disabled={!editable} readOnly={!editable} type="text" id="street" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.infor.street} required />
        </div>
        <div>
          <label htmlFor="ward" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Ward</label>
          <input ref={wardRef} disabled={!editable} readOnly={!editable} type="text" id="ward" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.infor?.ward} required />
        </div>
        <div>
          <label htmlFor="district" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">District</label>
          <input ref={districtRef} disabled={!editable} readOnly={!editable} type="text" id="district" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.infor?.district} required />
        </div>
        <div>
          <label htmlFor="city" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">City</label>
          <input ref={cityRef} disabled={!editable} readOnly={!editable} type="text" id="city" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.infor?.city} required />
        </div>
        <div>
          <label htmlFor="country" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Country</label>
          <input ref={countryRef} disabled={!editable} readOnly={!editable} type="text" id="country" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={userData?.infor?.country} required />
        </div>
      </div>
    </form>
  )
}
