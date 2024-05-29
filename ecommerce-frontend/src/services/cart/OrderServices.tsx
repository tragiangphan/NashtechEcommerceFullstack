import axios from 'axios';
import { OrderRequest } from '../../models/cart/request/OrderRequest';
import { ORDER_REST_API_URL } from '../../utils/RestAPIConstants';

export const createOrder = (order: OrderRequest) => axios.post(ORDER_REST_API_URL, order);
export const getAllOrder = () => axios.get(ORDER_REST_API_URL);
export const getOrderById = (orderId: number) => axios.get(ORDER_REST_API_URL + `?id=${orderId}`);
export const updateOrder = (orderId: number, order: OrderRequest) => axios.put(ORDER_REST_API_URL + `?id=${orderId}`, order);