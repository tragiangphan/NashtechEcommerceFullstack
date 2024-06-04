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

export const ProductManagement: React.FC = () => {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [categories, setCategories] = useState<string[]>([]);
  const [suppliers, setSuppliers] = useState<string[]>([]);
  // const [productId, setProductId] = useState(0);
  const [totalElement, setTotalElement] = useState(0);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchProducts();
  }, [pagination]);

  const fetchProducts = async () => {
    try {
      const resProd = await getAllProduct(pagination);
      const resCate = await getAllCategory(pagination);
      const resSupp = await getAllSupplier(pagination);

      setCategories(resCate.data.categoryResponseDTOs.map((res: CategoryResponse) => res.categoryName));
      setSuppliers(resSupp.data.supplierResponseDTOs.map((res: SupplierResponse) => res.supplierName));
      setProducts(resProd.data.products);
      setTotalElement(resProd.data.totalElement);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  // const titles = [
  //   { title: 'id', type: 'number' },
  //   { title: 'productName', type: 'text' },
  //   { title: 'productDesc', type: 'text' },
  //   { title: 'unit', type: 'text' },
  //   { title: 'price', type: 'number' },
  //   { title: 'quantity', type: 'number' },
  //   { title: 'featureMode', type: 'text' },
  //   { title: 'categoryId', type: 'number' },
  //   { title: 'suppliers', type: 'number' }
  // ];

  const productColumns = [
    { header: 'ID', accessor: (row: ProductResponse) => row.id, width: '5%' },
    { header: 'Product Name', accessor: (row: ProductResponse) => row.productName, width: '30%' },
    { header: 'Description', accessor: (row: ProductResponse) => row.productDesc, width: '30%' },
    { header: 'Unit', accessor: (row: ProductResponse) => row.price, width: '5%' },
    { header: 'Price', accessor: (row: ProductResponse) => row.price, width: '5%' },
    { header: 'Quantity', accessor: (row: ProductResponse) => row.price, width: '5%' },
    { header: 'Feature Mode', accessor: (row: ProductResponse) => row.price, width: '10%' },
    { header: 'Category', accessor: (row: ProductResponse) => row.categoryId, width: '10%' },
    { header: 'Supplier', accessor: (row: ProductResponse) => row.suppliers, width: '10%' },
    { header: 'Images', accessor: (row: ProductResponse) => row.images, width: '30%' },
  ];


  // const handleSaveEdit = async (rowIndex: number, editedData: any) => {
  //   try {
  //     const res = await updateProduct(rowIndex + 1, editedData);
  //     console.log(res.data);
  //     fetchProducts();
  //   } catch (error) {
  //     console.error('Error updating product:', error);
  //   }
  // };

  // const handleCreate = async (newData: any) => {
  //   const newDataParsed = {
  //     productName: newData.productName,
  //     productDesc: newData.productDesc,
  //     unit: newData.unit,
  //     price: Number(newData.price),
  //     quantity: Number(newData.quantity),
  //     featureMode: newData.featureMode,
  //     categoryId: Number(newData.categoryId),
  //     suppliers: newData.suppliers.split(',').map(Number),
  //     images: ['']
  //   };

  //   try {
  //     const productRes = await createProduct(newDataParsed);
  //     setProductId(productRes.data.productId);
  //     fetchProducts(); // Refresh the product list
  //     return productRes.data; // Return product ID after creation
  //   } catch (error) {
  //     console.error('Error creating product:', error);
  //     throw error; // Ensure the error is propagated
  //   }
  // };

  // const onFileChange = async (file: File, newData: any, callback: (fileUrl: string) => void) => {
  //   console.log("");
  // };

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  return (
    <div>
      <AdvancedTableComponent
        data={products}
        columns={productColumns}
        editModal={({ row, closeModal, onSave }) => <ProductModal row={row} categories={categories} closeModal={closeModal} suppliers={suppliers} onSave={onSave} />}
        createModal={({ closeModal, onSave }) => <ProductModal row={[]} categories={categories} closeModal={closeModal} suppliers={suppliers} onSave={onSave} />} />
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
