import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Choice from './screen/choice';

import UserMain from './screen/user/UserMain'
import UserProductInfo from './screen/user/ProductInfo'
import UserOrderList from './screen/user/UserOrderList'
import UserSearchOrder from './screen/user/UserSearchOrder'

import AdminLogin from './screen/admin/AdminLogin'
import AdminRegister from './screen/admin/AdminRegister';
import AdminOrderList from './screen/admin/AdminOrderList'




function App() {


  return (
    <Router>
      <Routes>
        <Route path="/" element={<Choice />} />
        
        <Route path="/user" element={<UserMain />} />
        <Route path="/user/menu" element={<UserProductInfo />} /> 
        <Route path="/user/searchorder" element={<UserSearchOrder />} />     
        <Route path="/user/orderlist" element={<UserOrderList />} />       

        <Route path="/admin/login" element={<AdminLogin />} />
        <Route path="/admin/register" element={<AdminRegister />} />
        <Route path="/admin/adminorderpage" element={<AdminOrderList />} />
      </Routes>
    </Router>

  )
}

export default App
