import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import { HomeComponent } from './components/client/pages/HomeComponent'
import { HeaderComponent } from './components/commons/HeaderComponent';
import { FooterComponent } from './components/commons/FooterComponent';
import { StoreComponent } from './components/client/pages/StoreComponent';
import { SignInComponent } from './components/auths/SignInComponent';
import { AboutComponent } from './components/client/pages/AboutComponent';
import { DetailComponent } from './components/client/pages/DetailComponent';
import { UserDetailComponent } from './components/client/features/UserDetailComponent';
import { DashboardComponent } from './components/admin/pages/DashboardComponent';

function App() {
  return (
    <BrowserRouter>
      <HeaderComponent />
      <Routes>
        <Route path="/" element={<SignInComponent />} />

        {/* client */}
        <Route path="/home" element={<HomeComponent />} />
        <Route path="/store" element={<StoreComponent />} />
        <Route path="/store/:productName" element={<DetailComponent />} />
        <Route path="/about" element={<AboutComponent />} />
        <Route path="/:username" element={<UserDetailComponent />} />

        {/* admin */}
        <Route path="/admin" element={<DashboardComponent />} />
      </Routes>
      <FooterComponent />
    </BrowserRouter>
  )
}

export default App
