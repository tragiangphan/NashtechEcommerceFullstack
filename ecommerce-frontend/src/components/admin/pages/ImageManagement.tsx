import { Pagination } from 'antd'
import React, { useEffect, useState } from 'react'
import { TableComponent } from '../features/TableComponent'
import { PaginationModel } from '../../../models/commons/PaginationModel'
import { createImage, getImageByProductId } from '../../../services/prod/ImageServices'
import { updateProduct, createProduct, getAllProduct } from '../../../services/prod/ProductServices'
import { ImageResponse } from '../../../models/prod/response/ImageResponse'
import { ProductResponse } from '../../../models/prod/response/ProductResponse'


export const ImageManagement: React.FC = () => {
  const [images, setImages] = useState<ImageResponse[]>([]);
  const [productId, setProductId] = useState(0);
  const [totalElement, setTotalElement] = useState(0);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  });

  useEffect(() => {
    fetchImages();
  }, [pagination]);

  const fetchImages = async () => {
    try {
      const res = await getAllProduct(pagination);
      console.log(res.data);
      const products = res.data.products;
  
      // Fetch images for all products
      const imageRes = await Promise.all(
        products.map(async (product: ProductResponse) => {
          const imageResponse = await getImageByProductId(product.id, pagination);
          console.log(imageResponse.data);
          const images = imageResponse.data.map((image: any) => ({
            productId: product.id,
            productName: product.productName,  // Assuming 'name' is the field for product name
            imageLink: image.imageLink,
            imageDesc: image.imageDesc,
          }));
          return images;
        })
      );
  
      // Flatten the array of arrays
      const flattenedImageRes = imageRes.flat();
  
      setImages(flattenedImageRes);
      setTotalElement(res.data.totalElement);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const titles = [
    { title: 'productId', type: 'number' },
    { title: 'productName', type: 'text' },
    { title: 'imageLink', type: 'text' },
    { title: 'imageDesc', type: 'text' }
  ];

  const handleSaveEdit = async (rowIndex: number, editedData: any) => {
    try {
      const res = await updateProduct(rowIndex + 1, editedData);
      console.log(res.data);
      fetchImages();
    } catch (error) {
      console.error('Error updating product:', error);
    }
  };

  const handleCreate = async (newData: any) => {
    const newDataParsed = {
      productName: newData.productName,
      productDesc: newData.productDesc,
      unit: newData.unit,
      price: Number(newData.price),
      quantity: Number(newData.quantity),
      featureMode: newData.featureMode,
      categoryId: Number(newData.categoryId),
      suppliers: newData.suppliers.split(',').map(Number),
      images: ['']
    };

    try {
      const productRes = await createProduct(newDataParsed);
      setProductId(productRes.data.productId);
      // fetchProducts(); // Refresh the product list
      return productRes.data; // Return product ID after creation
    } catch (error) {
      console.error('Error creating product:', error);
      throw error; // Ensure the error is propagated
    }
  };

  const uploadImage = async (imageFile: File, productId: string, imageDesc: string) => {
    const formData = new FormData();
    formData.append('imageFile', imageFile);
    formData.append('productId', productId);
    formData.append('imageDesc', imageDesc);

    try {
      const response = await createImage(formData);
      return response.data.fileName;
    } catch (error) {
      console.error('Error uploading file:', error);
      throw error;
    }
  };

  const onFileChange = async (file: File, newData: any, callback: (fileUrl: string) => void) => {
    try {
      const resProd = await handleCreate(newData); // Wait for handleCreate to complete
      const fileUrl = await uploadImage(file, productId.toString(), `Image for product ${resProd.productName}`);
      callback(fileUrl); // Call the callback with the uploaded file URL
    } catch (error) {
      console.error('Error during file change:', error);
    }
  };

  const onPageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };
  
  return (
    <div>
      <TableComponent
        titles={titles}
        data={images}
        onEdit={handleSaveEdit}
        onCreate={handleCreate} 
        onFileChange={onFileChange} />
      <Pagination
        className='my-10 mx-auto'
        onChange={(page, size) => onPageChange(page, size)}
        current={pagination.currentPage}
        total={totalElement}
        pageSize={pagination.pageSize}
      />
    </div>
  )
}
