import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { Pagination } from 'antd';
import { AdvancedTableComponent } from '../features/AdvancedTableComponent';
import { Mode } from '../../../models/utils/ModeEnum';
import { UserResponse } from '../../../models/user/response/UserReponse';
import { getAllUser } from '../../../services/user/UserServices';
import { UserModal } from '../features/UserModal';
import { Status } from '../../../models/utils/StatusEnum';

export const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<UserResponse[]>([]);
  const [status, setStatus] = useState<boolean>(false);
  const [totalElement, setTotalElement] = useState(0);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchUsers();
  }, [pagination, totalElement, status]);

  const fetchUsers = async () => {
    try {
      const resProd = await getAllUser(pagination);
      console.log(resProd.data);

      setUsers(resProd.data.users);
      setTotalElement(resProd.data.totalElement);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const columns = [
    { header: 'ID', accessor: (row: UserResponse) => row.id, width: '5%' },
    { header: 'First Name', accessor: (row: UserResponse) => row.firstName, width: '15%' },
    { header: 'Last Name', accessor: (row: UserResponse) => row.lastName, width: '15%' },
    { header: 'Email', accessor: (row: UserResponse) => row.email, width: '40%' },
    { header: 'Username', accessor: (row: UserResponse) => row.username, width: '40%' },
    { header: 'Active Mode', accessor: (row: UserResponse) => row.activeMode, width: '5%' },
    { header: 'Phone No.', accessor: (row: UserResponse) => row.phoneNo, width: '10%' },
    { header: 'Role', accessor: (row: UserResponse) => row.roleId, width: '5%' },
    // { header: 'Address', accessor: (row: SupplierResponse) => row.address, width: '40%' },
    // { header: 'Ward', accessor: (row: SupplierResponse) => row.ward, width: '40%' },
    // { header: 'City', accessor: (row: SupplierResponse) => row.city, width: '40%' },
    // { header: 'Country', accessor: (row: SupplierResponse) => row.country, width: '40%' },
    // { header: 'Postal Code', accessor: (row: SupplierResponse) => row.postalCode, width: '40%' },
  ];

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  const handleStatusCode = (statusCode: Status) => {
    if (statusCode == Status.SUCCESS) {
      setStatus((s) => !s);
    }
  }

  return (
    <div>
      <AdvancedTableComponent
        data={users}
        columns={columns}
        dataType={'User'}
        editModal={({ row, closeModal, onSave }) => <UserModal row={row} closeModal={closeModal} onSave={onSave} mode={Mode[Mode.EDIT]} />}
        createModal={({ closeModal, onSave }) => <UserModal row={[]} closeModal={closeModal} onSave={onSave} mode={Mode[Mode.CREATE]} />} statusCode={handleStatusCode} />
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
