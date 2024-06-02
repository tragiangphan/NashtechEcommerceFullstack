import axios from 'axios';
import { RatingRequest } from '../../models/cart/request/RatingRequest';
import { RATING_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';
import { PaginationModel } from '../../models/commons/PaginationModel';

export const createRating = (rating: RatingRequest) => axios.post(RATING_REST_API_URL, rating, CORS_CONFIG);
export const getAllRating = (pagination: PaginationModel) => axios.get(RATING_REST_API_URL + `?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getRatingById = (ratingId: number) => axios.get(RATING_REST_API_URL + `?id=${ratingId}`, CORS_CONFIG);
export const getRatingByProductId = (productId: number, pagination: PaginationModel) => axios.get(RATING_REST_API_URL + `?productId=${productId}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getAverageRatingByProductId = (productId: number, pagination: PaginationModel) => axios.get(RATING_REST_API_URL + `?productId=${productId}&average=true&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const updateRating = (ratingId: number, rating: RatingRequest) => axios.put(RATING_REST_API_URL + `?id=${ratingId}`, rating, CORS_CONFIG);