import React, { useEffect, useState } from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { ImageResponse } from '../../models/prod/response/ImageResponse';
import { Pagination } from '../../models/commons/Pagination';
import { getImageById } from '../../services/prod/ImageServices';
import { getProductByFeatureMode } from '../../services/prod/ProductServices';
import { FeatureProduct } from '../../models/prod/response/FeatureProduct';


const CarouselComponent: React.FC = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 1,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 1,
          infinite: true,
          dots: true,
        },
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 1,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
    ],
  };

  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [newProduct, setNewProduct] = useState<FeatureProduct[]>([]);
  const [images, setImages] = useState<ImageResponse[]>([]);

  const pagination: Pagination = {
    direction: 'ASC',
    currentPage: 1,
    pageSize: 10
  }

  useEffect(() => {
    getFeatureProduct();
  }, [])


  
  const getProductsByFeatureMode = () => {
    getProductByFeatureMode('NEW', pagination)
      .then((res) => {
        setProducts(res.data?.products);
        console.log(products);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  const getImages = (product: ProductResponse) => {
    let listImage: ImageResponse[] = [];
    product.images.map((image, index) => {
      getImageById(image).then((res) => {
        listImage.push(res.data);
      }).catch((err) => console.error(err)
      )
    })
    setImages(listImage);
    return listImage;
  }

  const getFeatureProduct = () => {
    getProductsByFeatureMode();
    const newProduct: FeatureProduct[] = products.map(product => ({
      id: product.id,
      productName: product.productName,
      productDesc: product.productDesc,
      unit: product.unit,
      price: product.price,
      quantity: product.quantity,
      featureMode: product.featureMode,
      categoryId: product.categoryId,
      suppliers: product.suppliers,
      images: getImages(product)
    }));
    setNewProduct(newProduct);
  }

  return (
    <div className="carousel-container bg-dark">
      <h2 className='text-white'>On Sale</h2>
      <Slider {...settings}>
        {newProduct.map((product, index) => (
          <div key={product.id} className="carousel-item mx-3">
            <div className="product-card">
              {product.images.map((image, index) => (
                <div key={image.id} className="product-image" style={{ backgroundImage: `url(${image.img_url})` }}>
                  {!product.images && <div className="placeholder">Product Image</div>}
                </div>
              ))}
              <h3>{product.productName}</h3>
              <p>{product.productDesc}</p>
              <p>
                {!product.price && <span className="original-price">{product.price}</span>}
                <span className="price">{product.price}</span>
              </p>
            </div>
          </div>
        ))}
      </Slider>
    </div>
  );
};

export default CarouselComponent;