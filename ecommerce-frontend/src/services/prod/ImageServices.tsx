import axios from 'axios';
import { IMAGE_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_IMG_CONFIG } from '../../utils/CORSConfig';
import { PaginationModel } from '../../models/commons/PaginationModel';

export const createImage = (image: FormData) => axios.post(IMAGE_REST_API_URL, image, CORS_IMG_CONFIG);
export const getAllImage = (pagination: PaginationModel) => axios.get(IMAGE_REST_API_URL + `?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_IMG_CONFIG);
export const getImageById = (imageId: number) => axios.get(IMAGE_REST_API_URL + `?id=${imageId}`, CORS_IMG_CONFIG);
export const getImageByProductId = (productId: number, pagination: PaginationModel) => axios.get(IMAGE_REST_API_URL + `?id=${productId}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_IMG_CONFIG);
export const updateImage = (imageId: number, image: FormData) => axios.put(IMAGE_REST_API_URL + `?id=${imageId}`, image, CORS_IMG_CONFIG);
export const deleteImage = (imageId: number) => axios.delete(IMAGE_REST_API_URL + `?id=${imageId}`, CORS_IMG_CONFIG);