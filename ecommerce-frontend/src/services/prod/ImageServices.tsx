import axios from 'axios';
import { IMAGE_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';
// import { ImageRequest } from '../../models/prod/request/ImageRequest';

// export const createImage = (image: ImageRequest) => axios.post(IMAGE_REST_API_URL, image);
export const getAllImage = () => axios.get(IMAGE_REST_API_URL);
export const getImageById = (imageId: number) => axios.get(IMAGE_REST_API_URL + `?id=${imageId}`, CORS_CONFIG);
// export const updateImage = (imageId: number, image: ImageRequest) => axios.put(IMAGE_REST_API_URL + `?id=${imageId}`, image);
// export const deleteImage = (imageId: number, image: ImageRequest) => axios.put(IMAGE_REST_API_URL + `?id=${imageId}`, image);