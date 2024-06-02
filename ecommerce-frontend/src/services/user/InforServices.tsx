import axios from 'axios';
import { INFOR_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';
import { InforRequest } from '../../models/user/request/InforRequest';

export const createInfor = (infor: InforRequest) => axios.post(INFOR_REST_API_URL, infor, CORS_CONFIG);
export const getAllInfor = () => axios.get(INFOR_REST_API_URL, CORS_CONFIG);
export const getInforById = (inforId: number) => axios.get(INFOR_REST_API_URL + `?id=${inforId}`, CORS_CONFIG);
export const updateInfor = (inforId: number, infor: InforRequest) => axios.put(INFOR_REST_API_URL + `?id=${inforId}`, infor, CORS_CONFIG);