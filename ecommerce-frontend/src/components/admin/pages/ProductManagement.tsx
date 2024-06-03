import React, { useEffect, useState } from 'react';
import { createProduct, getAllProduct, updateProduct } from '../../../services/prod/ProductServices';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { ProductResponse } from '../../../models/prod/response/ProductResponse';
import { TableComponent } from '../features/TableComponent';
import { Pagination } from 'antd';
import { createImage } from '../../../services/prod/ImageServices';

export const ProductManagement: React.FC = () => {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [totalElement, setTotalElement] = useState(0);
  const [updateProducts, setUpdateProducts] = useState();
  const [newProducts, setNewProducts] = useState();
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchProducts();
  }, [pagination, updateProducts, newProducts]);

  const fetchProducts = async () => {
    try {
      const res = await getAllProduct(pagination);
      setProducts(res.data.products);
      setTotalElement(res.data.totalElement);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const titles = [
    { title: 'id', type: 'number' },
    { title: 'productName', type: 'text' },
    { title: 'productDesc', type: 'text' },
    { title: 'unit', type: 'text' },
    { title: 'price', type: 'number' },
    { title: 'quantity', type: 'number' },
    { title: 'featureMode', type: 'text' },
    { title: 'categoryId', type: 'number' },
    { title: 'suppliers', type: 'number' },
    { title: 'images', type: 'file' }
  ];

  const handleSaveEdit = async (rowIndex: number, editedData: any) => {
    try {
      const res = await updateProduct(rowIndex + 1, editedData);
      setUpdateProducts(res.data);
      const updatedProducts = products.map(product => {
        if (product.id === rowIndex) {
          return { ...product, ...editedData };
        }
        return product;
      });
      setProducts(updatedProducts);
    } catch (error) {
      console.error('Error updating product:', error);
    }
  };

  const handleCreate = async (newData: any) => {
    const newDataParsed = {
      productName: newData.productName,
      productDesc: newData.productDesc,
      unit: newData.unit,
      price: Number(newData.price),
      quantity: Number(newData.quantity),
      featureMode: newData.featureMode,
      categoryId: Number(newData.categoryId),
      suppliers: newData.suppliers.split(',').map(Number),
      images: newData.images,
    };
    try {
      const res = await createProduct(newDataParsed);
      setNewProducts(res.data);
      fetchProducts(); // Refresh the product list
    } catch (error) {
      console.error('Error creating product:', error);
    }
  };

  const handleCreateFile = async (file: File, callback: (fileUrl: string) => void) => {
    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await createImage(formData);
      const fileUrl = response.data.fileName;
      callback(fileUrl);
    } catch (error) {
      console.error('Error uploading file:', error);
    }
  };

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  return (
    <div>
      <TableComponent
        titles={titles}
        data={products}
        onEdit={handleSaveEdit}
        onCreate={handleCreate}
        onFileChange={handleCreateFile}
      />
      <Pagination
        className='my-10 mx-auto'
        onChange={(page, size) => onPageChange(page, size)}
        current={pagination.currentPage}
        total={totalElement}
        pageSize={pagination.pageSize}
      />
    </div>
  );
};
