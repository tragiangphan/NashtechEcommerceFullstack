import axios from "axios";
import { CartItemRequest } from "../../models/cart/request/CartItemRequest";
import { CART_REST_API_URL } from "../../utils/RestAPIConstants";
import { CORS_CONFIG } from "../../utils/CORSConfig";

export const createCart = (cart: CartItemRequest) => axios.post(CART_REST_API_URL, cart, CORS_CONFIG);
export const getAllCart = () => axios.get(CART_REST_API_URL, CORS_CONFIG);
export const getCartById = (cartId: number) => axios.get(CART_REST_API_URL + `?id=${cartId}`, CORS_CONFIG);
export const updateCart = (cartId: number, cart: CartItemRequest) => axios.put(CART_REST_API_URL + `?id=${cartId}`, cart, CORS_CONFIG);
