import { useEffect, useState } from 'react';
import { RatingComponent } from './RatingComponent';
import { Product } from '../../../models/prod/entity/Product';
import { Tooltip } from 'antd';
import { useNavigate } from 'react-router-dom';
import { getAverageRatingByProductId } from '../../../services/cart/RatingServices';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { Images } from '../../../models/prod/entity/Images';


interface ProductShow {
  id: number,
  productName: string,
  productDesc: string,
  price: number,
  unit: string,
  quantity: number,
  averageRating: number,
  images: Images[]
}

export const ProductItemComponent: React.FC<{ prods: ProductShow[] }> = ({ prods }) => {
  const [products, setProducts] = useState<ProductShow[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    setProducts(prods);
  }, [prods]);
  
  const handleShowProductDetail = (product: Product) => {
    // Lưu thông tin sản phẩm vào localStorage
    localStorage.setItem('productDetail', JSON.stringify(product));
    navigate(`/store/${product.productName}`);
  };

  return (
    <>
      {
        products.map(product => (
          <button onClick={() => handleShowProductDetail(product)} key={product.id} className="h-auto max-w-full rounded-lg bg-white border border-gray-200 shadow dark:bg-gray-800 dark:border-gray-700">
            <a className='grid content-center' href="#">
              <img className="p-8 object-cover h-96 w-96 rounded-t-lg" src={'http://localhost:8080' + product.images[product.images.length - 1]?.url} alt={product.images[product.images.length - 1]?.desc} />
            </a>
            <div className="px-5 pb-5">
              <a href="#" data-tooltip-target="tooltip-default">
                <Tooltip className='text-md'
                  title={`${product.productName} ${product.productName} ${product.productName}
                          ${product.productName} ${product.productName} ${product.productName}`}>
                  <h5 className="text-xl line-clamp-2 truncate overflow-hidden font-semibold tracking-tight text-gray-900 dark:text-white" >
                    {product.productName} {product.productName} {product.productName}
                    {product.productName} {product.productName} {product.productName}
                  </h5>
                </Tooltip>
              </a>
              {<RatingComponent average={product.averageRating} />}
              <div className="flex items-center justify-between">
                <span className="text-3xl font-bold text-gray-900 dark:text-white">${product.price}</span>
                <a href="#" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Add to cart</a>
              </div>
            </div>
          </button>
        ))
      }
    </>
  );
};
