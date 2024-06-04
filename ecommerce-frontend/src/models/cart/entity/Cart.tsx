import { CartItem } from "./CartItem";

export type Cart = {
  id: number;
  userId: number;
  cartItems: CartItem[];
}
