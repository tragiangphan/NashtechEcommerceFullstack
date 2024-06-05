import React, { useRef } from 'react';
import { ActiveModeEnum } from '../../../models/utils/ActiveModeEnum';

interface UserModalProps {
  row: any;
  mode: string;
  closeModal: () => void;
  onSave: (data: any) => void;
}

export const UserModal: React.FC<UserModalProps> = ({ row, mode, closeModal, onSave }) => {
  const firstnameRef = useRef<HTMLInputElement>(null);
  const lastnameRef = useRef<HTMLInputElement>(null);
  const activeModeRef = useRef<HTMLSelectElement>(null);
  const phoneRef = useRef<HTMLInputElement>(null);
  const usernameRef = useRef<HTMLInputElement>(null);
  const emailRef = useRef<HTMLInputElement>(null);
  // const addressRef = useRef<HTMLInputElement>(null);
  // const streetRef = useRef<HTMLInputElement>(null);
  // const wardRef = useRef<HTMLInputElement>(null);
  // const cityRef = useRef<HTMLInputElement>(null);
  // const countryRef = useRef<HTMLInputElement>(null);
  // const postalCodeRef = useRef<HTMLInputElement>(null);

  console.log(row);
  

  const handleSubmitUpdate = async (event: React.FormEvent) => {
    event.preventDefault();
    const updatedData = {
      firstname: firstnameRef.current?.value,
      lastname: lastnameRef.current?.value,
      activeMode: activeModeRef.current?.value,
      phoneNo: phoneRef.current?.value,
      username: usernameRef.current?.value,
      email: emailRef.current?.value,
      // address: addressRef.current?.value,
      // street: streetRef.current?.value,
      // ward: wardRef.current?.value,
      // city: cityRef.current?.value,
      // country: countryRef.current?.value,
      // postalCode: postalCodeRef.current?.value,
    };
    onSave(updatedData);
    closeModal();
  };

  return (
    <form id="updateUserModal" aria-hidden="true" onSubmit={handleSubmitUpdate}>
      <div className="bg-white rounded-lg shadow dark:bg-gray-700">
        <div className="flex justify-between items-start p-4 rounded-t border-b dark:border-gray-600">
          <h3 className="text-xl font-semibold text-gray-900 dark:text-white">{mode == "EDIT" ? 'Update User' : 'Create User'}</h3>
          <button
            type="button"
            className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white"
            data-modal-toggle="updateUserModal"
            onClick={closeModal}>
            <svg
              aria-hidden="true"
              className="w-5 h-5"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg">
              <path
                fillRule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 011.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                clipRule="evenodd"
              />
            </svg>
            <span className="sr-only">Close modal</span>
          </button>
        </div>
        <div className="p-6 space-y-6">
          <div className="grid gap-4 mb-4 sm:grid-cols-2">
            <div>
              <label htmlFor="firstname" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First Name</label>
              <input type="text" ref={firstnameRef} name="firstname" id="firstname" defaultValue={row.firstName} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Ex. Valley Technology Inc. &ldquo;" />
            </div>
            <div>
              <label htmlFor="lastname" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
              <input type="text" ref={lastnameRef} name="lastname" id="lastname" defaultValue={row.lastName} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Ex. Valley Technology Inc. &ldquo;" />
            </div>
            <div>
              <label htmlFor="username" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
              <input type="text" ref={usernameRef} name="username" id="username" defaultValue={row.username} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Ex. 123/45&ldquo;" />
            </div>
            <div>
              <label htmlFor="mode" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Active Mode</label>
              <select ref={activeModeRef} id="mode" defaultValue={row.activeMode} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                {Object.keys(ActiveModeEnum).map(mode => (
                  <option key={mode} value={mode}>{mode}</option>
                ))}
              </select>
            </div>
            <div>
              <label htmlFor="phone" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone</label>
              <input type="tel" ref={phoneRef} name="phone" id="phone" defaultValue={row.phoneNo} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Ex. 0123-456-789&ldquo;" pattern="[0][0-9]{3}-[0-9]{3}-[0-9]{3}" />
            </div>
            <div>
              <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
              <input type="email" ref={emailRef} name="email" id="email" defaultValue={row.email} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Ex. supplier@example.com&ldquo;" />
            </div>

          </div>
        </div>
      </div>
      <div className="flex items-center p-6 space-x-2 rounded-b border-t border-gray-200 dark:border-gray-600">
        <button type="submit" className="text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Save</button>
        <button type="button" onClick={() => closeModal} className="text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Cancel</button>
      </div>
    </form>
  );
};
