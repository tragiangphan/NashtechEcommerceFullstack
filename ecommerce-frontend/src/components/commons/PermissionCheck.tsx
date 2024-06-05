import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useCookies } from 'react-cookie';

export const PermissionCheck: React.FC<{ requiredRole: number }> = ({ requiredRole }) => {
  const [cookies] = useCookies(['roleId']);

  if (cookies.roleId !== requiredRole) {
    return <Navigate to="/403" />;
  }

  return <Outlet />;
};
