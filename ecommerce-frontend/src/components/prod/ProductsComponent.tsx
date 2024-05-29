import React, { useState } from 'react';

interface Device {
  name: string;
  brand: string;
  price: string;
  image?: string;
}



const devices: Device[] = [
  { name: 'Laptop 1', brand: 'Brand A', price: '$1200', image: 'path/to/laptop1.jpg' },
  { name: 'Earphones 1', brand: 'Brand B', price: '$99', image: 'path/to/earphones1.jpg' },
  { name: 'Smartphone 1', brand: 'Brand C', price: '$699', image: 'path/to/smartphone1.jpg' },
  { name: 'Monitor 1', brand: 'Brand D', price: '$300', image: 'path/to/monitor1.jpg' },
  { name: 'Laptop 2', brand: 'Brand E', price: '$1100', image: 'path/to/laptop2.jpg' },
  { name: 'Earphones 2', brand: 'Brand F', price: '$89', image: 'path/to/earphones2.jpg' },
  { name: 'Smartphone 2', brand: 'Brand G', price: '$799', image: 'path/to/smartphone2.jpg' },
  { name: 'Monitor 2', brand: 'Brand H', price: '$350', image: 'path/to/monitor2.jpg' },
];

const ProductsComponent: React.FC = () => {
  const [sortOption, setSortOption] = useState('on sale');
  const [showCount, setShowCount] = useState(20);

  return (
    <div className="container product-list-container">
      <h2>Products (Filtered by Category #1)</h2>
      <div className="filter-options row">
        <div className="filter-by col-3">
          <h3>Filter By</h3>
          <div className="filter-category">
            <h4>Category</h4>
            <ul>
              <li>Category Name</li>
              <li>Category #1</li>
              <li>Category #2</li>
            </ul>
          </div>
          <div className="filter-author">
            <h4>Brand</h4>
            <ul>
              <li>Brand Name</li>
              <li>Brand #1</li>
              <li>Brand #2</li>
            </ul>
          </div>
          <div className="filter-rating">
            <h4>Rating Review</h4>
            <ul>
              <li>1 Star</li>
              <li>2 Star</li>
              <li>3 Star</li>
              <li>4 Star</li>
              <li>5 Star</li>
            </ul>
          </div>
        </div>
        <div className="sort-options col-9">
          <div className='container'>
            <div className="d-flex row justify-content-between mb-3">
              <div className='col container'>
                <div className="row align-items-center">
                <label className='col' htmlFor='sort-by'>Sort by</label>
                <select id='sort-by' className='form-select col' value={sortOption} onChange={(e) => setSortOption(e.target.value)}>
                  <option value="on sale">Sort by on sale</option>
                  <option value="popularity">Sort by popularity</option>
                  <option value="low to high">Sort by price: low to high</option>
                  <option value="high to low">Sort by price: high to low</option>
                </select>
                </div>
              </div>
              <div className='col container'>
                <div className="row align-items-center">
                <label className='col' htmlFor='show-limit'>Show</label>
                <select id='show-limit' className='form-select col' value={showCount} onChange={(e) => setShowCount(parseInt(e.target.value))}>
                  <option value={20}>Show 20</option>
                  <option value={50}>Show 50</option>
                  <option value={100}>Show 100</option>
                </select>
                </div>
              </div>
            </div>
            <div className="products-grid">
              {devices.slice(0, showCount).map((device, index) => (
                <div key={index} className="product-card">
                  <div className="product-image" style={{ backgroundImage: `url(${device.image})` }}>
                    {!device.image && <div className="placeholder">Device Image</div>}
                  </div>
                  <h3>{device.name}</h3>
                  <p>{device.brand}</p>
                  <p className="price">{device.price}</p>
                </div>
              ))}
            </div>
            <div className="pagination">
              <button>Previous</button>
              <button>1</button>
              <button>2</button>
              <button>3</button>
              <button>Next</button>
            </div>
          </div>
        </div>
      </div>

    </div>
  );
};

export default ProductsComponent;
