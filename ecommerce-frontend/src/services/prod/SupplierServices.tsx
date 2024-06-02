import axios from 'axios';
import { SUPPLIER_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';

// export const createSupplier = (supplier: SupplierRequest) => axios.post(SUPPLIER_REST_API_URL, supplier);
export const getAllSupplier = () => axios.get(SUPPLIER_REST_API_URL, CORS_CONFIG);
export const getSupplierById = (supplierId: number) => axios.get(SUPPLIER_REST_API_URL + `?id=${supplierId}`, CORS_CONFIG);
// export const updateSupplier = (supplierId: number, supplier: SupplierRequest) => axios.put(SUPPLIER_REST_API_URL + `?id=${supplierId}`, supplier);