import { Tabs } from 'antd';
import { ProductOutlined, BuildOutlined, ApartmentOutlined, HeatMapOutlined } from '@ant-design/icons';
import React from 'react';
import { ProductManagement } from './ProductManagement';
import { CategoryManagement } from './CategoryManagement';
import { SupplierManagement } from './SupplierManagement';
import { UserManagement } from './UserManagement';

export const DashboardComponent: React.FC = () => {
  const tabItems = [
    {
      key: '1',
      label: (
        <div className='text-lg font-semibold'>
          <ProductOutlined className='me-2' size={24} />
          Product Management
        </div>
      ),
      children: <ProductManagement />
    },
    {
      key: '2',
      label: (
        <div className='text-lg font-semibold'>
          <BuildOutlined className='me-2' />
          Category Management
        </div>
      ),
      children: <CategoryManagement />,
    },
    {
      key: '3',
      label: (
        <div className='text-lg font-semibold'>
          <ApartmentOutlined className='me-2' />
          Supplier Management
        </div>
      ),
      children: <SupplierManagement />,
    },
    {
      key: '4',
      label: (
        <div className='text-lg font-semibold'>
          <HeatMapOutlined className='me-2' />
          User Management
        </div>
      ),
      children: <UserManagement />,
    }
  ];

  return (
    <Tabs
      className="container mx-auto my-5"
      defaultActiveKey="1"
      items={tabItems}
    />
  );
};
