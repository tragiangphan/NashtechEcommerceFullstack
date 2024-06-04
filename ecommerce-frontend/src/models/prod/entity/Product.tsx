import { Images } from "./Images"

export type Product = {
  id: number,
  productName: string,
  productDesc: string,
  price: number,
  unit: string,
  quantity: number,
  images: Images[]
}