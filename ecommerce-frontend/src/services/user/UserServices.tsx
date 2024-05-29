import axios from 'axios';
// import { UserRequest } from '../../models/user/request/UserRequest';
import { USER_REST_API_URL } from '../../utils/RestAPIConstants';

// export const createUser = (user: UserRequest) => axios.post(USER_REST_API_URL, user);
export const getAllUser = () => axios.get(USER_REST_API_URL);
export const getUserById = (userId: number) => axios.get(USER_REST_API_URL + `?id=${userId}`);
// export const updateUser = (userId: number, user: UserRequest) => axios.put(USER_REST_API_URL + `?id=${userId}`, user);