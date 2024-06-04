import axios from "axios";
import { CartItemRequest } from "../../models/cart/request/CartItemRequest";
import { PaginationModel } from "../../models/commons/PaginationModel";
import { CART_ITEM_REST_API_URL } from "../../utils/RestAPIConstants";
import { CORS_CONFIG } from "../../utils/CORSConfig";

export const createCartItem = (cartItem: CartItemRequest) => axios.post(CART_ITEM_REST_API_URL, cartItem, CORS_CONFIG);
export const getAllCartItem = (pagination: PaginationModel) => axios.get(CART_ITEM_REST_API_URL + `?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getCartItemById = (cartItemId: number) => axios.get(CART_ITEM_REST_API_URL + `?id=${cartItemId}`, CORS_CONFIG);
export const updateCartItem = (cartItemId: number, cartItem: CartItemRequest) => axios.put(CART_ITEM_REST_API_URL + `?id=${cartItemId}`, cartItem, CORS_CONFIG);
export const deleteCartItem = (cartItemId: number) => axios.delete(CART_ITEM_REST_API_URL + `?id=${cartItemId}`, CORS_CONFIG);
