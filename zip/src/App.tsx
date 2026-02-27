/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import ClassDetails from './pages/ClassDetails';
import Tuition from './pages/Tuition';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        
        <Route path="/" element={<Layout><Dashboard /></Layout>} />
        <Route path="/dashboard" element={<Layout><Dashboard /></Layout>} />
        <Route path="/class" element={<Layout><Dashboard /></Layout>} />
        <Route path="/class/:id" element={<Layout><ClassDetails /></Layout>} />
        <Route path="/students" element={<Layout><ClassDetails /></Layout>} />
        <Route path="/tuition" element={<Layout><Tuition /></Layout>} />
        <Route path="/settings" element={<Layout><Dashboard /></Layout>} />
        
        <Route path="*" element={<Navigate to="/dashboard" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
