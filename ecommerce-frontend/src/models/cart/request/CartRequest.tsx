import { CartItem } from "../entity/CartItem";


export type CartRequest = {
  userId: number;
  cartItems: CartItem[];
}
