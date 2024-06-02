import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { TableComponent } from '../features/TableComponent';
import { Pagination } from 'antd';
import { getAllCategory, updateCategory } from '../../../services/prod/CategoryServices';
import { CategoryResponse } from '../../../models/prod/response/CategoryResponse';

export const CategoryManagement: React.FC = () => {
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const [totalElement, setTotalElement] = useState(0);
  const [updateCategories, setUpdateCategories] = useState();
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchCategories();
  }, [pagination, updateCategories]);

  const fetchCategories = async () => {
    try {
      const res = await getAllCategory(pagination);
      setCategories(res.data.categoryResponseDTOs);
      setTotalElement(res.data.totalElement);
    } catch (error) {
      console.error('Error fetching category:', error);
    }
  };

  const titles = [
    { title: 'id', type: 'number' },
    { title: 'categoryName', type: 'text' },
    { title: 'categoryDesc', type: 'text' },
    { title: 'activeMode', type: 'text' },
    { title: 'products', type: 'number' }
  ];

  const handleSaveEdit = async (rowIndex: number, editedData: any) => {
    try {
      console.log(editedData);
      const res = await updateCategory(rowIndex + 1, editedData);
      console.log(res.data);
      setUpdateCategories(res.data);
      console.log("Category updated successfully");
      const updatedCategory = categories.map(category => {
        if (category.id === rowIndex) {
          return { ...category, ...editedData};
        }
        return category;
      });
      setCategories(updatedCategory);
    } catch (error) {
      console.error('Error updating category:', error);
    }
  };

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  }

  return (
    <div>
      {/* Truyền mảng titles vào TableComponent */}
      <TableComponent titles={titles} data={categories} onEdit={handleSaveEdit} />
      <Pagination className='my-10 mx-auto' onChange={(page, size) => { onPageChange(page, size) }}
        current={pagination.currentPage} total={totalElement} pageSize={pagination.pageSize} />
    </div>
  );
};
