import React, { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { Pagination } from 'antd';
import { AdvancedTableComponent } from '../features/AdvancedTableComponent';
import { getAllCategory } from '../../../services/prod/CategoryServices';
import { CategoryResponse } from '../../../models/prod/response/CategoryResponse';
import { Mode } from '../../../models/utils/ModeEnum';
import { CategoryModal } from '../features/CategoryModal';

export const CategoryManagement: React.FC = () => {
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const [totalElement, setTotalElement] = useState(0);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchCategories();
  }, [pagination, totalElement]);

  const fetchCategories = async () => {
    try {
      const resProd = await getAllCategory(pagination);
      console.log(resProd.data);

      setCategories(resProd.data.categoryResponseDTOs);
      setTotalElement(resProd.data.totalElement);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const columns = [
    { header: 'ID', accessor: (row: CategoryResponse) => row.id, width: '5%' },
    { header: 'Category Name', accessor: (row: CategoryResponse) => row.categoryName, width: '35%' },
    { header: 'Category Description', accessor: (row: CategoryResponse) => row.categoryDesc, width: '40%' },
    { header: 'Active Mode', accessor: (row: CategoryResponse) => row.activeMode, width: '5%' }
  ];

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  return (
    <div>
      <AdvancedTableComponent
        data={categories}
        columns={columns}
        dataType={'Category'} 
        editModal={({ row, closeModal, onSave }) => <CategoryModal row={row} closeModal={closeModal} onSave={onSave} mode={Mode[Mode.EDIT]} />}
        createModal={({ closeModal, onSave }) => <CategoryModal row={[]} closeModal={closeModal} onSave={onSave} mode={Mode[Mode.CREATE]} />} />
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
