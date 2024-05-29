import { CartItem } from "../entity/CartItem";


export type OrderRequest = {
  userId: number;
  cartItems: CartItem[];
}
