import axios from "axios";
import { CartItemRequest } from "../../models/cart/request/CartItemRequest";
import { Pagination } from "../../models/commons/Pagination";
import { CART_ITEM_REST_API_URL } from "../../utils/RestAPIConstants";

export const createCartItem = (cartItem: CartItemRequest) => axios.post(CART_ITEM_REST_API_URL, cartItem);
export const getAllCartItem = (pagination: Pagination) => axios.get(CART_ITEM_REST_API_URL + `?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`);
export const getCartItemById = (cartItemId: number) => axios.get(CART_ITEM_REST_API_URL + `?id=${cartItemId}`);
export const updateCartItem = (cartItemId: number, cartItem: CartItemRequest) => axios.put(CART_ITEM_REST_API_URL + `?id=${cartItemId}`, cartItem);
export const deleteCartItem = (cartItemId: number) => axios.delete(CART_ITEM_REST_API_URL + `?id=${cartItemId}`);
