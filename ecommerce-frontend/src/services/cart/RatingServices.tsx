import axios from 'axios';
import { RatingRequest } from '../../models/cart/request/RatingRequest';
import { RATING_REST_API_URL } from '../../utils/RestAPIConstants';

export const createRating = (rating: RatingRequest) => axios.post(RATING_REST_API_URL, rating);
export const getAllRating = () => axios.get(RATING_REST_API_URL);
export const getRatingById = (ratingId: number) => axios.get(RATING_REST_API_URL + `?id=${ratingId}`);
export const getAverageRatingByProductId = (productId: number) => axios.get(RATING_REST_API_URL + `?productId=${productId}`);
export const updateRating = (ratingId: number, rating: RatingRequest) => axios.put(RATING_REST_API_URL + `?id=${ratingId}`, rating);