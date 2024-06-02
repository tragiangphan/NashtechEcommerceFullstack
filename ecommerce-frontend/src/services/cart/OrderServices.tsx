import axios from 'axios';
import { OrderRequest } from '../../models/cart/request/OrderRequest';
import { ORDER_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';

export const createOrder = (order: OrderRequest) => axios.post(ORDER_REST_API_URL, order, CORS_CONFIG);
export const getAllOrder = () => axios.get(ORDER_REST_API_URL, CORS_CONFIG);
export const getOrderById = (orderId: number) => axios.get(ORDER_REST_API_URL + `?id=${orderId}`, CORS_CONFIG);
export const updateOrder = (orderId: number, order: OrderRequest) => axios.put(ORDER_REST_API_URL + `?id=${orderId}`, order, CORS_CONFIG);