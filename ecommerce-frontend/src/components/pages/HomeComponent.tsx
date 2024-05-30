import { Pagination } from "../../models/commons/Pagination"
import React, { useEffect, useState } from "react"
import { getProductByFeatureMode } from "../../services/prod/ProductServices"
import { ThumbSearch } from "../home/ThumbSearch";
import { ProductComponent } from "../commons/ProductComponent";


export const HomeComponent: React.FC<{}> = () => {
	const [featureProducts, setFeatureProducts] = useState<ProductResponse[]>([]);
	const [totalPage, setTotalPage] = useState(0);
	const pagination: Pagination = {
		direction: 'ASC',
		currentPage: 1,
		pageSize: 9
	}

	useEffect(() => {
		getProductsByFeatureMode();
	}, []);

	function getProductsByFeatureMode() {
		getProductByFeatureMode('FEATURED', pagination)
			.then((res) => {
				setFeatureProducts(res.data?.products);
				setTotalPage(res.data?.totalPage)
				console.log(res.data);
			})
			.catch((error) => {
				console.error(error);
			});
	}

	return (
		<div>
			<ThumbSearch />
			<div className="container mx-auto">
				<h1 className="text-xl font-bold">ON SALE!!!</h1>
				<ProductComponent  prods={featureProducts} totalPage={totalPage} currentPage={pagination.currentPage} pageSize={pagination.pageSize} />
			</div>
		</div>
	)
}

