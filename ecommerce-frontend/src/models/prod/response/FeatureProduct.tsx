import { ImageResponse } from "./ImageResponse"

export type FeatureProduct = {
  id: number,
  productName: string,
  productDesc: string,
  unit: string,
  price: number,
  quantity: number,
  featureMode: string,
  categoryId: number,
  suppliers: number[],
  images: ImageResponse[]
}