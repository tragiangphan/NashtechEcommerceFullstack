import axios from "axios";
import { PaginationModel } from "../../models/commons/PaginationModel";
import { PRODUCT_REST_API_URL } from "../../utils/RestAPIConstants";
import { CORS_CONFIG } from "../../utils/CORSConfig";

export const createProduct = async (productRequest: ProductRequest) => await axios.post(
  PRODUCT_REST_API_URL, productRequest, CORS_CONFIG);
export const getAllProduct = async (pagination: PaginationModel) => await axios.get(
  `${PRODUCT_REST_API_URL}?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getProductByFeatureMode = async (featureMode: string, pagination: PaginationModel) => await axios.get(
  `${PRODUCT_REST_API_URL}?featureMode=${featureMode}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getProductById = async (productId: number) => await axios.get(
  PRODUCT_REST_API_URL + `?id=${productId}`, CORS_CONFIG);
export const getProductByCategoryName = async (categoryName: string, pagination: PaginationModel) => await axios.get(
  PRODUCT_REST_API_URL + `?categoryName=${categoryName}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getProductByProductName = async (productName: string, pagination: PaginationModel) => await axios.get(
  PRODUCT_REST_API_URL + `?productName=${productName}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getProductByPriceRange = async (minPrice: number, maxPrice: number, pagination: PaginationModel) => await axios.get(PRODUCT_REST_API_URL + `?maxPrice=${maxPrice}&minPrice=${minPrice}&direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const updateProduct = async (productId: number, productRequest: ProductRequest) => await axios.put(
  PRODUCT_REST_API_URL + `?id=${productId}`, productRequest, CORS_CONFIG);
