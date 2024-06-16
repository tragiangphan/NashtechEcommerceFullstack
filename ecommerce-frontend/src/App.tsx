import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import { HomeComponent } from './components/client/pages/HomeComponent';
import { HeaderComponent } from './components/commons/HeaderComponent';
import { FooterComponent } from './components/commons/FooterComponent';
import { StoreComponent } from './components/client/pages/StoreComponent';
import { SignInComponent } from './components/auths/SignInComponent';
import { AboutComponent } from './components/client/pages/AboutComponent';
import { DetailComponent } from './components/client/pages/DetailComponent';
import { UserDetailComponent } from './components/client/features/UserDetailComponent';
import { DashboardComponent } from './components/admin/pages/DashboardComponent';
import { SignUpComponent } from './components/auths/SignUpComponent';
import 'flowbite/dist/flowbite.min.css';
import { PermissionCheck } from './components/commons/PermissionCheck';
import { ErrorComponent } from './components/commons/ErrorComponent';

function App() {
  return (
    <div className="flex flex-col min-h-screen">
      <BrowserRouter>
        <HeaderComponent />
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<SignInComponent />} />
            <Route path="/sign_in" element={<SignInComponent />} />
            <Route path="/sign_up" element={<SignUpComponent />} />

            {/* client */}
            <Route element={<PermissionCheck requiredRole={2} />}>
              <Route path="/home" element={<HomeComponent />} />
              <Route path="/store" element={<StoreComponent />} />
              <Route path="/store/:productName" element={<DetailComponent />} />
              <Route path="/about" element={<AboutComponent />} />
              <Route path="/:username" element={<UserDetailComponent />} />
            </Route>

            {/* admin */}
            <Route element={<PermissionCheck requiredRole={1} />}>
              <Route path="/admin" element={<DashboardComponent />} />
            </Route>

            {/* forbidden */}
            <Route path="*" element={<ErrorComponent code={404} message={"Sorry, the page you requested does not exist."} />} />
            <Route path="/403" element={<ErrorComponent code={403} message={"Sorry, you are not authorized to access this page."} />} />
          </Routes>
        </div>
        <FooterComponent />
      </BrowserRouter>
    </div>
  );
}

export default App;
