import { User } from "../../user/entity/User"

export type Rating = {
  id: number,
  createOn: Date,
  updateOn: Date,
  comment: string,
  rateScore: number,
  prodId: number,
  user: User
}