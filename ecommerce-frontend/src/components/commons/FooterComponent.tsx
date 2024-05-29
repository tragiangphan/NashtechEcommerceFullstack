import React from 'react';

const FooterComponent: React.FC = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <img src="public/vite.svg" alt="TechStore Logo" className="footer-logo" />
        <div className="footer-details">
          <p>TECHSTORE</p>
          <p>Address</p>
          <p>Phone</p>
        </div>
      </div>
    </footer>
  )
};

export default FooterComponent;
