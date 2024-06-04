import axios from 'axios';
import { USER_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';
import { PaginationModel } from '../../models/commons/PaginationModel';
import { UserRequest } from '../../models/user/request/UserRequest';

export const createUser = (user: UserRequest) => axios.post(USER_REST_API_URL, user, CORS_CONFIG);
export const getAllUser = (pagination: PaginationModel) => axios.get(USER_REST_API_URL + `?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getUserById = (userId: number, pagination: PaginationModel) => axios.get(USER_REST_API_URL + `?id=${userId}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getUserByUsername = (username: string, pagination: PaginationModel) => axios.get(USER_REST_API_URL + `?username=${username}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const updateUser = (userId: number, user: UserRequest) => axios.put(USER_REST_API_URL + `?id=${userId}`, user, CORS_CONFIG);