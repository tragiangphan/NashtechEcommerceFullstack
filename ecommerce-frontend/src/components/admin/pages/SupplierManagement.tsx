import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { TableComponent } from '../features/TableComponent';
import { Pagination } from 'antd';
import { getAllCategory } from '../../../services/prod/CategoryServices';
import { SupplierResponse } from '../../../models/prod/response/SupplierResponse';
import { updateSupplier } from '../../../services/prod/SupplierServices';

export const SupplierManagement: React.FC = () => {
  const [suppliers, setSuppliers] = useState<SupplierResponse[]>([]);
  const [totalElement, setTotalElement] = useState(0);
  const [updateSuppliers, setUpdateSuppliers] = useState();
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchSuppliers();
  }, [pagination, updateSuppliers]);

  const fetchSuppliers = async () => {
    try {
      const res = await getAllCategory(pagination);
      setSuppliers(res.data.categoryResponseDTOs);
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
      const res = await updateSupplier(rowIndex + 1, editedData);
      console.log(res.data);
      setUpdateSuppliers(res.data);
      console.log("Category updated successfully");
      const updatedSupplier = suppliers.map(supplier => {
        if (supplier.id === rowIndex) {
          return { ...supplier, ...editedData};
        }
        return supplier;
      });
      setSuppliers(updatedSupplier);
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
      <TableComponent titles={titles} data={suppliers} onEdit={handleSaveEdit} />
      <Pagination className='my-10 mx-auto' onChange={(page, size) => { onPageChange(page, size) }}
        current={pagination.currentPage} total={totalElement} pageSize={pagination.pageSize} />
    </div>
  );
};
