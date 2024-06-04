import React, { useState } from 'react';
import { createProduct } from '../../../services/prod/ProductServices';
import { getCategoryByCategoryName } from '../../../services/prod/CategoryServices';
import { getSupplierBySupplierName } from '../../../services/prod/SupplierServices';
import { createImage } from '../../../services/prod/ImageServices';

type Column<T> = {
  header: string;
  accessor: (row: T) => React.ReactNode;
  width?: string;
};

type AdvancedTableComponentProps<T> = {
  data: T[];
  columns: Column<T>[];
  createModal: React.FC<{ closeModal: () => void, onSave: () => void }>;
  editModal: React.FC<{ row: T; closeModal: () => void, onSave: () => void }>;
};

export const AdvancedTableComponent = <T,>({
  data,
  columns,
  createModal: CreateModal,
  editModal: EditModal,
}: AdvancedTableComponentProps<T>) => {
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [selectedRow, setSelectedRow] = useState<T | null>(null);

  const onSaveModal = async (data: any) => {
    console.log(data);
    try {
      const categoryRes = await getCategoryByCategoryName(data.category);
      console.log(categoryRes.data);
      const supplierRes = await getSupplierBySupplierName(data.supplier);
      console.log(supplierRes.data);
      const tempSupp = [supplierRes.data.id];
      const uploadProduct: ProductRequest = {
        productName: data.productName,
        productDesc: data.productDesc,
        unit: data.unit,
        price: data.price,
        quantity: Number(data.quantity),
        featureMode: data.featureMode,
        categoryId: categoryRes.data.id,
        suppliers: tempSupp
      }
      console.log(uploadProduct);
      const createProdRes = await createProduct(uploadProduct);
      console.log(createProdRes.data);
      console.log(data.images);
      data.images.map(async (image: File) => {
        const formData = new FormData();
        formData.append('imageFile', image);
        formData.append('imageDesc', 'Image for ' + createProdRes.data.productName);
        formData.append('productId', createProdRes.data.id);

        try {
          const response = await createImage(formData);
          const fileUrl = response.data.fileName;
          console.log(fileUrl);
        } catch (error) {
          console.error('Error uploading file:', error);
        }
      })
    } catch (error) {
      console.error('Error saving product:', error);
    }
  };

  const openCreateModal = () => setIsCreateModalOpen(true);
  const closeCreateModal = () => setIsCreateModalOpen(false);
  const openEditModal = (row: T) => {
    setSelectedRow(row);
    setIsEditModalOpen(true);
  };
  const closeEditModal = () => {
    setSelectedRow(null);
    setIsEditModalOpen(false);
  };

  return (
    <div className="mx-auto max-w-screen">
      <div className="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
        <div className="flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0 md:space-x-4 p-4">
          <div className="w-full md:w-1/2">
            <form className="flex items-center">
              <label htmlFor="simple-search" className="sr-only">Search</label>
              <div className="relative w-full">
                <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                  <svg aria-hidden="true" className="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clipRule="evenodd" />
                  </svg>
                </div>
                <input
                  type="text"
                  id="simple-search"
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full pl-10 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                  placeholder="Search"
                  required
                />
              </div>
            </form>
          </div>
          <div className="w-full md:w-auto flex flex-col md:flex-row space-y-2 md:space-y-0 items-stretch md:items-center justify-end md:space-x-3 flex-shrink-0">
            <button
              type="button"
              onClick={openCreateModal}
              className="flex items-center justify-center text-white bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:ring-primary-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-primary-600 dark:hover:bg-primary-700 focus:outline-none dark:focus:ring-primary-800"
            >
              <svg className="h-3.5 w-3.5 mr-2" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path clipRule="evenodd" fillRule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" />
              </svg>
              Add product
            </button>
          </div>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                {columns.map((column, index) => (
                  <th key={index} scope="col" className="px-4 py-3">{column.header}</th>
                ))}
                <th scope="col" className="px-4 py-3"><span className="sr-only">Actions</span></th>
              </tr>
            </thead>
            <tbody>
              {data.map((row, rowIndex) => (
                <tr key={rowIndex} className="border-b dark:border-gray-700">
                  {columns.map((column, colIndex) => (
                    <td key={colIndex} className="px-4 py-3">{column.accessor(row)}</td>
                  ))}
                  <td className="px-4 py-3 flex items-center justify-end">
                    <button
                      id={`edit-${rowIndex}`}
                      className="inline-flex items-center p-0.5 text-sm font-medium text-center text-gray-500 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100"
                      type="button"
                      onClick={() => openEditModal(row)}
                    >
                      <svg className="w-6 h-6" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m14.304 4.844 2.852 2.852M7 7H4a1 1 0 0 0-1 1v10a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-4.5m2.409-9.91a2.017 2.017 0 0 1 0 2.853l-6.844 6.844L8 14l.713-3.565 6.844-6.844a2.015 2.015 0 0 1 2.852 0Z" />
                      </svg>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {isEditModalOpen && selectedRow && (
        <div className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div className="fixed inset-0 transition-opacity">
              <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
            </div>
            <span className="hidden sm:inline-block sm:align-middle sm:h-screen"></span>
            &#8203;
            <div
              className="inline-block align-bottom bg-white dark:bg-gray-800 rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
              aria-modal="true"
              aria-labelledby="modal-headline"
            >
              <div className="bg-white dark:bg-gray-800 px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <EditModal row={selectedRow} closeModal={closeEditModal} onSave={onSaveModal} />
              </div>
            </div>
          </div>
        </div>
      )}
      {isCreateModalOpen && (
        <div className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div className="fixed inset-0 transition-opacity">
              <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
            </div>
            <span className="hidden sm:inline-block sm:align-middle sm:h-screen"></span>
            &#8203;
            <div
              className="inline-block align-bottom bg-white dark:bg-gray-800 rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
              aria-modal="true"
              aria-labelledby="modal-headline"
            >
              <div className="bg-white dark:bg-gray-800 px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <CreateModal closeModal={closeCreateModal} onSave={onSaveModal} />
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
