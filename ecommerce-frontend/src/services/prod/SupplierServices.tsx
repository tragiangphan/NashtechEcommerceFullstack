import axios from 'axios';
import { SUPPLIER_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';
import { PaginationModel } from '../../models/commons/PaginationModel';
import { SupplierRequest } from '../../models/prod/request/SupplierRequest';

// export const createSupplier = (supplier: SupplierRequest) => axios.post(SUPPLIER_REST_API_URL, supplier);
export const getAllSupplier = (pagination: PaginationModel) => axios.get(SUPPLIER_REST_API_URL + `?direction=${pagination.direction}&pageNum=${pagination.currentPage}&pageSize=${pagination.pageSize}`, CORS_CONFIG);
export const getSupplierById = (supplierId: number) => axios.get(SUPPLIER_REST_API_URL + `?id=${supplierId}`, CORS_CONFIG);
export const updateSupplier = (supplierId: number, supplier: SupplierRequest) => axios.put(SUPPLIER_REST_API_URL + `?id=${supplierId}`, supplier);