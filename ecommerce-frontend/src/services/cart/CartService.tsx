import axios from "axios";
import { CartItemRequest } from "../../models/cart/request/CartItemRequest";
import { CART_REST_API_URL } from "../../utils/RestAPIConstants";

export const createCart = (cart: CartItemRequest) => axios.post(CART_REST_API_URL, cart);
export const getAllCart = () => axios.get(CART_REST_API_URL);
export const getCartById = (cartId: number) => axios.get(CART_REST_API_URL + `?id=${cartId}`);
export const updateCart = (cartId: number, cart: CartItemRequest) => axios.put(CART_REST_API_URL + `?id=${cartId}`, cart);
