import axios from 'axios';
// import { CategoryRequest } from '../../models/prod/request/CategoryRequest';
import { CATEGORY_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';

// export const createCategory = (category: CategoryRequest) => axios.post(CATEGORY_REST_API_URL, category);
export const getAllCategory = () => axios.get(CATEGORY_REST_API_URL, CORS_CONFIG);
export const getCategoryById = (categoryId: number) => axios.get(CATEGORY_REST_API_URL + `?id=${categoryId}`, CORS_CONFIG);
// export const updateCategory = (categoryId: number, category: CategoryRequest) => axios.put(CATEGORY_REST_API_URL + `?id=${categoryId}`, category);