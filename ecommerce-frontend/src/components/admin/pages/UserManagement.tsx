import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { TableComponent } from '../features/TableComponent';
import { Pagination } from 'antd';
import { updateCategory } from '../../../services/prod/CategoryServices';
import { UserResponse } from '../../../models/user/response/UserReponse';
import { createUser, getAllUser } from '../../../services/user/UserServices';

export const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<UserResponse[]>([]);
  const [totalElement, setTotalElement] = useState(0);
  const [updateUsers, setUpdateUsers] = useState();
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchUsers();
  }, [pagination, updateUsers]);

  const fetchUsers = async () => {
    try {
      const res = await getAllUser(pagination);
      setUsers(res.data.users);
      setTotalElement(res.data.totalElement);
    } catch (error) {
      console.error('Error fetching category:', error);
    }
  };

  const titles = [
    { title: 'id', type: 'number' },
    { title: 'firstName', type: 'text' },
    { title: 'lastName', type: 'text' },
    { title: 'email', type: 'text' },
    { title: 'phoneNo', type: 'text' },
    { title: 'username', type: 'text' },
    { title: 'activeMode', type: 'text' },
    { title: 'inforId', type: 'number' },
  ];

  const handleSaveEdit = async (rowIndex: number, editedData: any) => {
    try {
      console.log(editedData);
      const res = await updateCategory(rowIndex + 1, editedData);
      console.log(res.data);
      setUpdateUsers(res.data);
      console.log("Category updated successfully");
      const updatedUser = users.map(user => {
        if (user.id === rowIndex) {
          return { ...user, ...editedData};
        }
        return user;
      });
      setUsers(updatedUser);
    } catch (error) {
      console.error('Error updating user:', error);
    }
  };

  const handleCreate = async (newData: any) => {
    try {
      const res = await createUser(newData);
      console.log("Category created successfully", res.data);
      setUpdateUsers(res.data);
      fetchUsers(); // Refresh the category list
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
      <TableComponent titles={titles} data={users} onEdit={handleSaveEdit} onCreate={handleCreate} onFileChange={handleFileChange} />
      <Pagination className='my-10 mx-auto' onChange={(page, size) => { onPageChange(page, size) }}
        current={pagination.currentPage} total={totalElement} pageSize={pagination.pageSize} />
    </div>
  );
};
