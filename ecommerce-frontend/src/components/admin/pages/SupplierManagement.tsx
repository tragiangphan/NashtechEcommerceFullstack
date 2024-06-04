import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { TableComponent } from '../features/TableComponent';
import { Pagination } from 'antd';
import { SupplierResponse } from '../../../models/prod/response/SupplierResponse';
import { createSupplier, getAllSupplier, updateSupplier } from '../../../services/prod/SupplierServices';

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
      const res = await getAllSupplier(pagination);
      setSuppliers(res.data.supplierResponseDTOs);
      setTotalElement(res.data.totalElement);
      console.log(res.data.supplierResponseDTOs);
    } catch (error) {
      console.error('Error fetching category:', error);
    }
  };

  const titles = [
    { title: 'id', type: 'number' },
    { title: 'supplierName', type: 'text' },
    { title: 'activeMode', type: 'text' },
    { title: 'supplierName', type: 'text'},
    { title: 'phoneNo', type: 'text'},
    { title: 'email', type: 'text'},
    { title: 'address', type: 'text'},
    { title: 'street', type: 'text'},
    { title: 'ward', type: 'text'},
    { title: 'city', type: 'text'},
    { title: 'country', type: 'text'},
    { title: 'postalCode', type: 'text'},
    { title: 'activeMode', type: 'text'},
    { title: 'products', type: 'number'}
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

  const handleCreate = async (newData: any) => {
    try {
      const res = await createSupplier(newData);
      console.log("Supplier created successfully", res.data);
      setUpdateSuppliers(res.data);
      fetchSuppliers(); // Refresh the category list
    } catch (error) {
      console.error('Error creating supplier:', error);
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
      <TableComponent titles={titles} data={suppliers} onEdit={handleSaveEdit} onCreate={handleCreate} onFileChange={handleFileChange} />
      <Pagination className='my-10 mx-auto' onChange={(page, size) => { onPageChange(page, size) }}
        current={pagination.currentPage} total={totalElement} pageSize={pagination.pageSize} />
    </div>
  );
};
