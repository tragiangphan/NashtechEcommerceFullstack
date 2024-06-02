// ProductManagement.tsx
import React, { useEffect, useState } from 'react';
import { getAllProduct, updateProduct } from '../../../services/prod/ProductServices';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { ProductResponse } from '../../../models/prod/response/ProductResponse';
import { TableComponent } from '../features/TableComponent';
import { Pagination } from 'antd';

export const ProductManagement: React.FC = () => {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [totalElement, setTotalElement] = useState(0);
  const [updateProducts, setUpdateProducts] = useState();
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchProducts();
  }, [pagination, updateProducts]);

  const fetchProducts = async () => {
    try {
      const res = await getAllProduct(pagination);
      setProducts(res.data.products);
      setTotalElement(res.data.totalElement);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  // Định nghĩa mảng các đối tượng TableColumn
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
    { title: 'images', type: 'number' }
  ];

  const handleSaveEdit = async (rowIndex: number, editedData: any) => {
    try {
      console.log(editedData);
      const res = await updateProduct(rowIndex + 1, editedData);
      console.log(res.data);
      setUpdateProducts(res.data);
      console.log("Product updated successfully");
      const updatedProducts = products.map(product => {
        if (product.id === rowIndex) {
          return { ...product, ...editedData};
        }
        return product;
      });
      setProducts(updatedProducts);
    } catch (error) {
      console.error('Error updating product:', error);
    }
  };

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  }

  return (
    <div>
      {/* Truyền mảng titles vào TableComponent */}
      <TableComponent titles={titles} data={products} onEdit={handleSaveEdit} />
      <Pagination className='my-10 mx-auto' onChange={(page, size) => { onPageChange(page, size) }}
        current={pagination.currentPage} total={totalElement} pageSize={pagination.pageSize} />
    </div>
  );
};
