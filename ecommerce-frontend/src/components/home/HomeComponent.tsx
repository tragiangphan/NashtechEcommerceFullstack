import { Pagination } from "../../models/commons/Pagination"
import React, { useEffect, useState } from "react"
import { getAllProduct } from "../../services/prod/ProductServices"
import CarouselComponent from "./CarouselComponent";
import FeatureComponent from "./FeatureComponent";


export const HomeComponent: React.FC = () => {
	const [products, setProducts] = useState<ProductResponse[]>();
	const pagination: Pagination = {
		direction: 'ASC',
		currentPage: 1,
		pageSize: 10
	}

	useEffect(() => {
		getAllProducts();
	}, []);

	// Get all employees on component mount
	function getAllProducts() {
		getAllProduct(pagination)
			.then((res) => {
				setProducts(res.data);
				console.log(res.data);
			})
			.catch((error) => {
				console.error(error);
			});
	}

	return (
		<div className="content">
		<CarouselComponent />
		<FeatureComponent />
	</div>
	)
}

