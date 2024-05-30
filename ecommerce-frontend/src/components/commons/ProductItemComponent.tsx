import React, { useEffect, useState } from 'react'
import { RatingComponent } from './RatingComponent';
import { Product } from '../../models/prod/entity/Product';

export const ProductItemComponent: React.FC<{ prods: Product[] }> = ({ prods }) => {
  const [products, setProducts] = useState<Product[]>([]);
  useEffect(() => {
    setProducts(prods)
  }, [prods])
  return (
    <>
      {
        products.map(product => (
          <div key={product.id} className="h-auto max-w-full rounded-lg bg-white border border-gray-200 shadow dark:bg-gray-800 dark:border-gray-700">
            <a className='grid content-center' href="#">
              <img className="p-8 object-scale-down h-96 w-96 rounded-t-lg" src={product.images[0]?.url} alt={product.images[0]?.desc} />
            </a>
            <div className="px-5 pb-5">
              <a href="#"><h5 className="text-xl font-semibold tracking-tight text-gray-900 dark:text-white">{product.productName}</h5></a>
              <RatingComponent />
              <div className="flex items-center justify-between">
                <span className="text-3xl font-bold text-gray-900 dark:text-white">${product.price}</span>
                <a href="#" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Add to cart</a>
              </div>
            </div>
          </div>
        ))
      }
    </>
  )
}
