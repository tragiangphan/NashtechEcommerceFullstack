import React, { useEffect, useState } from 'react'
import { Images } from '../../models/prod/entity/Images';
import { Product } from '../../models/prod/entity/Product';
import { getImageById } from '../../services/prod/ImageServices';
import { Pagination } from 'antd';
import { ProductItemComponent } from './ProductItemComponent';

export const ProductComponent: React.FC<{ prods: ProductResponse[], totalPage: number, currentPage: number, pageSize: number }> = ({ prods, totalPage, currentPage, pageSize }) => {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [productStore, setProductStore] = useState<Product[]>([]);

  useEffect(() => {
    setProducts(prods);
    getAllProducts();
  }, [prods]);


  function onChangePage(page: number, size: number) {
    
  }


  const getImages = (product: ProductResponse) => {
    let listImage: Images[] = [];
    product.images.map((image) => {
      getImageById(image).then((res) => {
        const imageObj: Images = {
          id: res.data?.id,
          url: res.data?.imageLink,
          desc: res.data?.imageDesc
        };
        listImage.push(imageObj);
      }).catch((err) => console.error(err)
      );
    })
    console.log(listImage);
    return listImage;
  }

  const getAllProducts = () => {
    const featureProducts: Product[] = products.map(prod => ({
      id: prod.id,
      productName: prod.productName,
      productDesc: prod.productDesc,
      unit: prod.unit,
      price: prod.price,
      quantity: prod.quantity,
      images: getImages(prod)
    }));
    setProductStore(featureProducts);
    console.log(featureProducts);
  }

  return (
    <>
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <ProductItemComponent prods={productStore} />
      </div>
      <div className='grid content-center'>
        <Pagination className='my-10 mx-auto' onChange={(currentPage, pageSize) => { onChangePage(currentPage, pageSize) }}
          current={currentPage} total={totalPage} pageSize={pageSize} />
      </div>
    </>
  )
}
