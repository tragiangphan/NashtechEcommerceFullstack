import { useEffect, useState } from 'react';
import { PaginationModel } from '../../../models/commons/PaginationModel';
import { getAllProduct, getProductByCategoryName, getProductByProductName } from '../../../services/prod/ProductServices';
import { SearchComponent } from '../features/SearchComponent';
import { getAllCategory } from '../../../services/prod/CategoryServices';
import { Category } from '../../../models/prod/entity/Category';
import { ProductComponent } from '../features/ProductComponent';
import { TagComponent } from '../features/TagComponent';
import { ProductResponse } from '../../../models/prod/response/ProductResponse';

export const StoreComponent: React.FC<{}> = () => {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [categories, setCategories] = useState<string[]>([]);
  const [currentCategory, setCurrentCategory] = useState<string>('All Categories');
  const [searchKeys, setSearchKeys] = useState<string>('');
  const [totalElement, setTotalElement] = useState(0);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 8
  });

  useEffect(() => {
    getCategories();
  }, []);

  useEffect(() => {
    fetchProducts(currentCategory, searchKeys);
  }, [pagination]);

  const getCategories = () => {
    getAllCategory(pagination).then((res) => {
      const cates: string[] = res.data.map((cate: Category) => cate.categoryName);
      setCategories(['All Categories', ...cates]);
    }).catch((error) => {
      console.error(error);
    });
  };

  const fetchProducts = (category: string, searchKey: string) => {
    if (searchKey == '') {
      if (category === 'All Categories') {
        getAllProduct(pagination)
          .then((res) => {
            setProducts(res.data?.products);
            setTotalElement(res.data?.totalElement);
            console.log(res.data?.products);
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        getProductByCategoryName(category, pagination)
          .then((res) => {
            setProducts(res.data?.products);
            setTotalElement(res.data?.totalElement);
            console.log(res.data?.products);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    } else {
      getProductByProductName(searchKey, pagination)
        .then((res) => {
          setProducts(res.data?.products);
          setTotalElement(res.data?.totalElement);
          console.log(res.data?.products);
        })
        .catch((error) => {
          console.error(error);
        })
    }
  };

  const handleCategoryChange = (selectedCategory: string) => {
    setCurrentCategory(selectedCategory);
    setPagination({ ...pagination, currentPage: 1 }); // Reset to first page on category change
    fetchProducts(selectedCategory, '');
  };

  const handlePageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  const handleSearch = (searchKey: string) => {
    setSearchKeys(searchKey);
    setPagination({ ...pagination, currentPage: 1 }); // Reset to first page on category change
    fetchProducts('', searchKey);
  };

  return (
    <div className="products container mx-auto">
      <SearchComponent searchKeyword={handleSearch} />
      <TagComponent
        tags={categories}
        tagsChange={handleCategoryChange}
      />
      <ProductComponent prods={products} totalPage={totalElement}
        currentPage={pagination.currentPage} pageSize={pagination.pageSize} onPageChange={handlePageChange} />
    </div>
  );
};
