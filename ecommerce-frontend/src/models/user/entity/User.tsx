import { Infor } from "./Infor"

export type User = {
  id: number,
  firstName: string,
  lastName: string,
  email: string,
  username: string,
  password: string,
  phoneNo: string,
  activeMode: string,
  roleId: number,
  infor: Infor,
  cartId: number,
  orders: number[],
  ratings: number[]
}