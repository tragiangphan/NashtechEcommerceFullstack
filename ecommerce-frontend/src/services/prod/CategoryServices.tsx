import axios from 'axios';
// import { CategoryRequest } from '../../models/prod/request/CategoryRequest';
import { CATEGORY_REST_API_URL } from '../../utils/RestAPIConstants';

// export const createCategory = (category: CategoryRequest) => axios.post(CATEGORY_REST_API_URL, category);
export const getAllCategory = () => axios.get(CATEGORY_REST_API_URL);
export const getCategoryById = (categoryId: number) => axios.get(CATEGORY_REST_API_URL + `?id=${categoryId}`);
// export const updateCategory = (categoryId: number, category: CategoryRequest) => axios.put(CATEGORY_REST_API_URL + `?id=${categoryId}`, category);