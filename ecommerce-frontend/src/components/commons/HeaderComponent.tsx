import React from 'react'

const HeaderComponent: React.FC = () => {
  return (
    <header className="container navbar d-inline-flex justify-content-between align-items-center">
      {/* <div className="row"> */}
      <div className="logo">
        <img src="public/vite.svg" alt="TechStore Logo" />
        <span><b>TECHSTORE</b></span>
      </div>
      <nav className="nav">
        <a className="nav-item" href="#home">Home</a>
        <a className="nav-item" href="#shop">Shop</a>
        <a className="nav-item" href="#about">About</a>
        <a className="nav-item" href="#cart">Cart (0)</a>
      </nav>
      {/* </div> */}
    </header>
  )
}

export default HeaderComponent;