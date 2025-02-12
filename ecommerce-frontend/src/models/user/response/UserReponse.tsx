export type UserResponse = {
  id: number,
  firstName: string,
  lastName: string,
  email: string,
  username: string,
  password: string,
  phoneNo: string,
  activeMode: string,
  roleId: number,
  inforId: number,
  cartId: number,
  orders: number[],
  ratings: number[]
}