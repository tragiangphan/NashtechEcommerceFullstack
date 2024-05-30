import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import { HomeComponent } from './components/pages/HomeComponent'
import { HeaderComponent } from './components/commons/HeaderComponent';
import { FooterComponent } from './components/commons/FooterComponent';
import { StoreComponent } from './components/pages/StoreComponent';
import { SignInComponent } from './components/user/SignInComponent';
import { AboutComponent } from './components/pages/AboutComponent';

function App() {
  return (
    <BrowserRouter>
      <HeaderComponent />
      <Routes>
        <Route path="/" element={<SignInComponent />} />
        <Route path="/home" element={<HomeComponent />} />
        <Route path="/store" element={<StoreComponent />} />
        <Route path="/about" element={<AboutComponent />} />
      </Routes>
      <FooterComponent />
    </BrowserRouter>
  )
}

export default App
