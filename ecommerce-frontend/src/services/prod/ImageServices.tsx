import axios from 'axios';
import { IMAGE_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_IMG_CONFIG } from '../../utils/CORSConfig';

export const createImage = (image: FormData) => axios.post(IMAGE_REST_API_URL, image, CORS_IMG_CONFIG);
export const getAllImage = () => axios.get(IMAGE_REST_API_URL, CORS_IMG_CONFIG);
export const getImageById = (imageId: number) => axios.get(IMAGE_REST_API_URL + `?id=${imageId}`, CORS_IMG_CONFIG);
export const updateImage = (imageId: number, image: FormData) => axios.put(IMAGE_REST_API_URL + `?id=${imageId}`, image, CORS_IMG_CONFIG);
export const deleteImage = (imageId: number) => axios.delete(IMAGE_REST_API_URL + `?id=${imageId}`, CORS_IMG_CONFIG);