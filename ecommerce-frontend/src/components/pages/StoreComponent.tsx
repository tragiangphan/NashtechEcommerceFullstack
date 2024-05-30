import React, { useEffect, useState } from 'react';
import { TagFilterComponent } from '../commons/TagFilterComponent';
import { Pagination } from '../../models/commons/Pagination';
import { getAllProduct, getProductByCategoryName } from '../../services/prod/ProductServices';
import { ProductComponent } from '../commons/ProductComponent';
import { SearchComponent } from '../store/SearchComponent';
import { getAllCategory } from '../../services/prod/CategoryServices';
import { Category } from '../../models/prod/entity/Category';


export const StoreComponent: React.FC<{}> = () => {
  const [categories, setCategories] = useState<string[]>([]);
  const [totalPage, setTotalPage] = useState(0);
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [currentCategory, setCurrentCategory] = useState<string>(categories[0]);
	const pagination: Pagination = {
		direction: 'ASC',
		currentPage: 1,
		pageSize: 12
	};

	useEffect(() => {
    getCategories();
    getProductsByCategoryName(currentCategory);
    getAllProducts();
	}, [currentCategory]);

  useEffect(() => {
    if (currentCategory) {
      console.log("Current category changed:", currentCategory);
    }
  }, [currentCategory]);

  function getCategories() {
    getAllCategory().then((res) => {
      const cates: string[] = res.data.map((cate: Category) => cate.categoryName);
      setCategories(cates);
      console.log(res.data);
    })
  }

  function getAllProducts() {
		getAllProduct(pagination)
			.then((res) => {
				setProducts(res.data?.products);
        setTotalPage(res.data?.totalElement);
				console.log(res.data);
			})
			.catch((error) => {
				console.error(error);
			});
	}

  function getProductsByCategoryName(currentCategory: string) {
		getProductByCategoryName(currentCategory, pagination)
			.then((res) => {
				setProducts(res.data?.products);
        setTotalPage(res.data?.totalElement);
				console.log(res.data);
			})
			.catch((error) => {
				console.error(error);
			});
	}

  return (
    <div className="products container mx-auto">
      <SearchComponent />
      <TagFilterComponent tags={categories} tagsChange={(selectedCategory) => setCurrentCategory(selectedCategory)} />
      <ProductComponent prods={products} totalPage={totalPage} currentPage={pagination.currentPage} pageSize={pagination.pageSize} />
    </div>
  );
};
