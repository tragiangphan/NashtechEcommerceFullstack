import axios from 'axios';
import { CATEGORY_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';
import { PaginationModel } from '../../models/commons/PaginationModel';
import { CategoryRequest } from '../../models/prod/request/CategoryRequest';

export const createCategory = (category: CategoryRequest) => axios.post(CATEGORY_REST_API_URL, category);
export const getAllCategory = (pagination: PaginationModel) => axios.get(`${CATEGORY_REST_API_URL}?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getCategoryById = (categoryId: number) => axios.get(`${CATEGORY_REST_API_URL}?categoryId=${categoryId}`, CORS_CONFIG);
export const getCategoryByCategoryName = (categoryName: string) => axios.get(`${CATEGORY_REST_API_URL}?categoryName=${categoryName}`, CORS_CONFIG);
export const updateCategory = (categoryId: number, category: CategoryRequest) => axios.put(CATEGORY_REST_API_URL + `?id=${categoryId}`, category);