import React, { useEffect, useState } from 'react';
import { Carousel } from 'react-responsive-carousel';
import { getProductByFeatureMode } from '../../services/prod/ProductServices';
import { Pagination } from '../../models/commons/Pagination';
import { ImageResponse } from '../../models/prod/response/ImageResponse';
import { getImageById } from '../../services/prod/ImageServices';
import { FeatureProduct } from '../../models/prod/response/FeatureProduct';

// const featureResponses: ProductResponse[] = [
//   {
//     id: 1, productName: "Wireless Mouse",
//     productDesc: "Ergonomic wireless mouse with adjustable DPI.",
//     unit: "piece", price: 25.99, quantity: 150, featureMode: "FEATURED",
//     categoryId: 2, suppliers: [101, 102, 103],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 2, productName: "Bluetooth Keyboard",
//     productDesc: "Compact and portable Bluetooth keyboard.",
//     unit: "piece", price: 45.00, quantity: 200, featureMode: "FEATURED",
//     categoryId: 3, suppliers: [104, 105],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 3, productName: "USB-C Hub",
//     productDesc: "Multiport USB-C hub with HDMI and USB 3.0 ports.",
//     unit: "piece", price: 35.50, quantity: 100, featureMode: "UNFEATURED",
//     categoryId: 4, suppliers: [106, 107, 108],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 4, productName: "Gaming Headset",
//     productDesc: "Surround sound gaming headset with noise-canceling mic.",
//     unit: "piece", price: 79.99, quantity: 80, featureMode: "FEATURED",
//     categoryId: 5, suppliers: [109, 110],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 5, productName: "4K Monitor",
//     productDesc: "27-inch 4K UHD monitor with HDR support.",
//     unit: "piece", price: 299.99, quantity: 50, featureMode: "UNFEATURED",
//     categoryId: 6, suppliers: [111, 112],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 6, productName: "External SSD",
//     productDesc: "1TB external SSD with high-speed data transfer.",
//     unit: "piece", price: 120.00, quantity: 300, featureMode: "FEATURED",
//     categoryId: 7, suppliers: [113, 114, 115],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 7, productName: "Webcam",
//     productDesc: "1080p HD webcam with built-in microphone.",
//     unit: "piece", price: 49.99, quantity: 120, featureMode: "FEATURED",
//     categoryId: 8, suppliers: [116, 117],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 8, productName: "Mechanical Keyboard",
//     productDesc: "RGB backlit mechanical keyboard with programmable keys.",
//     unit: "piece", price: 89.99, quantity: 60, featureMode: "FEATURED",
//     categoryId: 3, suppliers: [118, 119, 120],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 9, productName: "Portable Charger",
//     productDesc: "10000mAh portable charger with fast charging capability.",
//     unit: "piece", price: 29.99, quantity: 250, featureMode: "FEATURED",
//     categoryId: 9, suppliers: [121, 122],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   },
//   {
//     id: 10, productName: "Smart Speaker",
//     productDesc: "Voice-controlled smart speaker with virtual assistant.",
//     unit: "piece", price: 59.99, quantity: 90, featureMode: "UNFEATURED",
//     categoryId: 10, suppliers: [123, 124],
//     images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg", "https://example.com/image3.jpg"]
//   }
// ];



const FeatureComponent: React.FC = () => {
  const [selectedTab, setSelectedTab] = useState<'recommended' | 'popular'>('recommended');
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [featureProduct, setFeatureProduct] = useState<FeatureProduct[]>([]);
  const pagination: Pagination = {
    direction: 'ASC',
    currentPage: 1,
    pageSize: 5
  }

  useEffect(() => {
    getProductsByFeatureMode();
    getFeatureProduct();
  }, []);

  const getProductsByFeatureMode = () => {
    getProductByFeatureMode('FEATURED', pagination)
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
    console.log(listImage);
    return listImage;
  }

  const getFeatureProduct = () => {
    getProductsByFeatureMode();
    const featureProducts: FeatureProduct[] = products.map(product => ({
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
    setFeatureProduct(featureProducts);    
  }

  return (
    <div className="featured-products-container">
      <h2>Featured Products</h2>
      <div className="tabs">
        <button onClick={() => setSelectedTab('recommended')} className={selectedTab === 'recommended' ? 'FEATURED' : ''}>Recommended</button>
        <button onClick={() => setSelectedTab('popular')} className={selectedTab === 'popular' ? 'FEATURED' : ''}>Popular</button>
      </div>
      <div className="products-grid">
        {featureProduct.map((device, index, array) => (
          <div key={device.id} className="product-card">
            <div className="product-image">
              <Carousel showArrows={true} showThumbs={false} infiniteLoop={true} autoPlay={true} interval={3000}>
                {device.images.map((image, imgIndex) => (
                  <div key={image.id}>
                    <img src={image.img_url} alt={`${device.productName} ${imgIndex + 1}`} />
                  </div>
                ))}
              </Carousel>
            </div>
            <h3>{device.productName}</h3>
            <p>{device.productDesc}</p>
            <p>Suppliers: {device.suppliers.join(", ")}</p>
            <p className="price">${device.price.toFixed(2)}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FeatureComponent;
