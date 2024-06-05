import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { Pagination } from 'antd';
import { AdvancedTableComponent } from '../features/AdvancedTableComponent';
import { Mode } from '../../../models/utils/ModeEnum';
import { SupplierResponse } from '../../../models/prod/response/SupplierResponse';
import { getAllSupplier } from '../../../services/prod/SupplierServices';
import { SupplierModal } from '../features/SupplierModal';

export const SupplierManagement: React.FC = () => {
  const [suppliers, setSuppliers] = useState<SupplierResponse[]>([]);
  const [totalElement, setTotalElement] = useState(0);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchSuppliers();
  }, [pagination, totalElement]);

  const fetchSuppliers = async () => {
    try {
      const resProd = await getAllSupplier(pagination);
      console.log(resProd.data);

      setSuppliers(resProd.data.supplierResponseDTOs);
      setTotalElement(resProd.data.totalElement);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const columns = [
    { header: 'ID', accessor: (row: SupplierResponse) => row.id, width: '5%' },
    { header: 'Supplier Name', accessor: (row: SupplierResponse) => row.supplierName, width: '35%' },
    { header: 'Active Mode', accessor: (row: SupplierResponse) => row.activeMode, width: '5%' },
    { header: 'Phone No.', accessor: (row: SupplierResponse) => row.phoneNo, width: '40%' },
    { header: 'Email', accessor: (row: SupplierResponse) => row.email, width: '40%' },
    { header: 'Address', accessor: (row: SupplierResponse) => row.address, width: '40%' },
    { header: 'Ward', accessor: (row: SupplierResponse) => row.ward, width: '40%' },
    { header: 'City', accessor: (row: SupplierResponse) => row.city, width: '40%' },
    { header: 'Country', accessor: (row: SupplierResponse) => row.country, width: '40%' },
    { header: 'Postal Code', accessor: (row: SupplierResponse) => row.postalCode, width: '40%' },
  ];

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  return (
    <div>
      <AdvancedTableComponent
        data={suppliers}
        columns={columns}
        dataType={'Supplier'}
        editModal={
          ({ row, closeModal, onSave }) => <SupplierModal row={row} closeModal={closeModal} onSave={onSave} mode={Mode[Mode.EDIT]} />}
        createModal={
          ({ closeModal, onSave }) => <SupplierModal row={[]} closeModal={closeModal} onSave={onSave} mode={Mode[Mode.CREATE]} />} />
      <Pagination
        className="my-10 mx-auto"
        onChange={(page, size) => onPageChange(page, size)}
        current={pagination.currentPage}
        total={totalElement}
        pageSize={pagination.pageSize}
      />
    </div>
  );
};
