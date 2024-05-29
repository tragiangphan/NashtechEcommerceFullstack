import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import { HomeComponent } from './components/home/HomeComponent'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { SignInComponent } from './components/user/SignInComponent'
import HeaderComponent from './components/commons/HeaderComponent'
import FooterComponent from './components/commons/FooterComponent'

function App() {
  return (
    <BrowserRouter>
      <HeaderComponent />
      <Routes>
        <Route path="/" element={<SignInComponent />} />
        <Route path="/home" element={<HomeComponent />} />
      </Routes>
      <FooterComponent />
    </BrowserRouter>
  )
}

export default App
