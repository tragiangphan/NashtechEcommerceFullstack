import React, { useEffect, useState } from 'react';
import { getAllProduct } from '../../../services/prod/ProductServices';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { ProductResponse } from '../../../models/prod/response/ProductResponse';
import { Pagination } from 'antd';
import { AdvancedTableComponent } from '../features/AdvancedTableComponent';
import { ProductModal } from '../features/ProductModal';
import { getAllCategory } from '../../../services/prod/CategoryServices';
import { getAllSupplier } from '../../../services/prod/SupplierServices';
import { CategoryResponse } from '../../../models/prod/response/CategoryResponse';
import { SupplierResponse } from '../../../models/prod/response/SupplierResponse';
import { Mode } from '../../../models/utils/ModeEnum';
import { Status } from '../../../models/utils/StatusEnum';

export const ProductManagement: React.FC = () => {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [categories, setCategories] = useState<string[]>([]);
  const [suppliers, setSuppliers] = useState<string[]>([]);
  const [statusCode, setStatusCode] = useState<boolean>(false);
  const [totalElement, setTotalElement] = useState(0);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchProducts();
  }, [pagination, totalElement, statusCode]);

  const fetchProducts = async () => {
    try {
      const resProd = await getAllProduct(pagination);
      const resCate = await getAllCategory({
        direction: 'ASC',
        currentPage: 1,
        pageSize: 50
      });
      const resSupp = await getAllSupplier({
        direction: 'ASC',
        currentPage: 1,
        pageSize: 50
      });

      setCategories(resCate.data.categoryResponseDTOs.map((res: CategoryResponse) => res.categoryName));
      setSuppliers(resSupp.data.supplierResponseDTOs.map((res: SupplierResponse) => res.supplierName));
      setProducts(resProd.data.products);
      console.log(resProd.data.products);
      
      setTotalElement(resProd.data.totalElement);

      console.log(resCate.data.categoryResponseDTOs);
      console.log(resSupp.data.supplierResponseDTOs);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const columns = [
    { header: 'ID', accessor: (row: ProductResponse) => row.id, width: '5%' },
    { header: 'Product Name', accessor: (row: ProductResponse) => row.productName, width: '30%' },
    { header: 'Description', accessor: (row: ProductResponse) => row.productDesc, width: '30%' },
    { header: 'Unit', accessor: (row: ProductResponse) => row.unit, width: '5%' },
    { header: 'Price', accessor: (row: ProductResponse) => row.price, width: '5%' },
    { header: 'Quantity', accessor: (row: ProductResponse) => row.quantity, width: '5%' },
    { header: 'Feature Mode', accessor: (row: ProductResponse) => row.featureMode, width: '5%' },
    { header: 'Category', accessor: (row: ProductResponse) => row.categoryId, width: '5%' },
    { header: 'Supplier', accessor: (row: ProductResponse) => row.suppliers, width: '5%' },
    { header: 'Images', accessor: (row: ProductResponse) => row.images.join(', '), width: '5%' },
    { header: 'Last Modified Date', accessor: (row: ProductResponse) => new Date(row.lastUpdatedOn).toUTCString(), width: '5%' }
  ];

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  const handleChangeProduct = (statusCode: Status) => {
    if (statusCode == Status.SUCCESS) {
      setStatusCode((s)=>!s);
    }
  }

  return (
    <div>
      <AdvancedTableComponent
        data={products}
        columns={columns}
        dataType='Product'
        statusCode={handleChangeProduct}
        editModal={({ row, closeModal, onSave }) => <ProductModal row={row} categories={categories} closeModal={closeModal} suppliers={suppliers} onSave={onSave} mode={Mode[Mode.EDIT]} />}
        createModal={({ closeModal, onSave }) => <ProductModal row={[]} categories={categories} closeModal={closeModal} suppliers={suppliers} onSave={onSave} mode={Mode[Mode.CREATE]} />} />
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
