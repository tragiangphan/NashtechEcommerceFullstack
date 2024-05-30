import React, { useEffect, useState } from 'react';
import { Images } from '../../models/prod/entity/Images';
import { Product } from '../../models/prod/entity/Product';
import { getImageById } from '../../services/prod/ImageServices';
import { Pagination } from 'antd';
import { ProductItemComponent } from './ProductItemComponent';

export const ProductComponent: React.FC<{ prods: ProductResponse[], totalPage: number, currentPage: number, pageSize: number, onPageChange: (page: number, size: number) => void }> = ({ prods, totalPage, currentPage, pageSize, onPageChange }) => {
  const [productStore, setProductStore] = useState<Product[]>([]);

  useEffect(() => {
    fetchAllProducts(prods);
  }, [prods]);

  const fetchAllProducts = async (products: ProductResponse[]) => {
    const featureProducts: Product[] = await Promise.all(products.map(async (prod) => ({
      id: prod.id,
      productName: prod.productName,
      productDesc: prod.productDesc,
      unit: prod.unit,
      price: prod.price,
      quantity: prod.quantity,
      images: await getImages(prod)
    })));
    setProductStore(featureProducts);
    console.log(featureProducts);
  };

  const getImages = async (product: ProductResponse): Promise<Images[]> => {
    const imagePromises = product.images.map((image) =>
      getImageById(image).then((res) => ({
        id: res.data?.id,
        url: res.data?.imageLink,
        desc: res.data?.imageDesc
      })).catch((err) => {
        console.error(err);
        return null;
      })
    );
    const images = await Promise.all(imagePromises);
    return images.filter((image) => image !== null) as Images[];
  };

  return (
    <>
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <ProductItemComponent prods={productStore} />
      </div>
      <div className='grid content-center'>
        <Pagination className='my-10 mx-auto' onChange={(page, size) => { onPageChange(page, size) }}
          current={currentPage} total={totalPage} pageSize={pageSize} />
      </div>
    </>
  )
}
