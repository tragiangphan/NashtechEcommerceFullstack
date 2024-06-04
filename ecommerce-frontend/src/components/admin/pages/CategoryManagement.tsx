import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { TableComponent } from '../features/TableComponent';
import { Pagination } from 'antd';
import { createCategory, getAllCategory, updateCategory } from '../../../services/prod/CategoryServices';
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
    { title: 'activeMode', type: 'text' }
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
          return { ...category, ...editedData };
        }
        return category;
      });
      setCategories(updatedCategory);
    } catch (error) {
      console.error('Error updating category:', error);
    }
  };

  const handleCreate = async (newData: any) => {
    try {
      console.log(newData);
      const newDataParsed = {
        id: Number(newData.id),
        categoryName: newData.categoryName,
        categoryDesc: newData.categoryDesc,
        activeMode: newData.activeMode
      }
      console.log(newDataParsed);
      const res = await createCategory(newDataParsed);
      console.log("Category created successfully", res.data);
      setUpdateCategories(res.data);
      fetchCategories(); // Refresh the category list
    } catch (error) {
      console.error('Error creating category:', error);
    }
  };

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  }

  const handleFileChange = async () => {
    console.log("");
  };

  return (
    <div>
      <TableComponent titles={titles} data={categories} onEdit={handleSaveEdit} onCreate={handleCreate} onFileChange={handleFileChange} />
      <Pagination className='my-10 mx-auto' onChange={(page, size) => { onPageChange(page, size) }}
        current={pagination.currentPage} total={totalElement} pageSize={pagination.pageSize} />
    </div>
  );
};
