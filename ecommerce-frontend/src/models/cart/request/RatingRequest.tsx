import { CartItem } from "../entity/CartItem";


export type RatingRequest = {
  userId: number;
  cartItems: CartItem[];
}
