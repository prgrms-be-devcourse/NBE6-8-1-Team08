import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Choice from './screen/choice';

import UserMain from './screen/user/UserMain'
import UserProductInfo from './screen/user/ProductInfo'

import AdminLogin from './screen/admin/AdminLogin'
// import AdminLogin from './screen/admin/AdminLogin'
// import AdminCancelList from './screen/admin/AdminCancelList'




function App() {


  return (
    <Router>
      <Routes>
        <Route path="/" element={<Choice />} />
        
        <Route path="/user" element={<UserMain />} />
        <Route path="/user/menu" element={<UserProductInfo />} /> 
          
        <Route path="/admin/login" element={<AdminLogin />} />
        {/* <Route path="/admin/login" element={<AdminLogin />} />
        <Route path="/admin/cancel" element={<AdminCancelList />} /> */}
      </Routes>
    </Router>

  )
}

export default App
