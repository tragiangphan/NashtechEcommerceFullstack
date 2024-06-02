import { PaginationModel } from "../../models/commons/PaginationModel"
import { useEffect, useState } from "react"
import { getProductByFeatureMode } from "../../services/prod/ProductServices"
import { ThumbSearch } from "../home/ThumbSearch";
import { ProductComponent } from "../commons/ProductComponent";


export const HomeComponent: React.FC<{}> = () => {
	const [saleProducts, setSaleProducts] = useState<ProductResponse[]>([]);
	const [featureProducts, setFeatureProducts] = useState<ProductResponse[]>([]);
	const [newProducts, setNewProducts] = useState<ProductResponse[]>([]);
	const [totalPage, setTotalPage] = useState(0);
	const [pagination, setPagination] = useState<PaginationModel>({
		direction: 'ASC',
		currentPage: 1,
		pageSize: 4
	});

	useEffect(() => {
		getProductsByFeatureMode('FEATURED');
		getProductsByFeatureMode('SALE');
		getProductsByFeatureMode('NEW');
	}, [pagination]);

	function getProductsByFeatureMode(featureMode: string) {
		getProductByFeatureMode(featureMode, pagination)
			.then((res) => {
				switch (featureMode) {
					case 'SALE':
						setSaleProducts(res.data?.products);
						break;
					case 'FEATURED':
						setFeatureProducts(res.data?.products);
						break;
					case 'NEW':
						setNewProducts(res.data?.products);
						break;
					default:
						break;
				}
				setTotalPage(res.data?.totalPage)
				console.log(res.data);
			})
			.catch((error) => {
				console.error(error);
			});
	}

	const handlePageChange = (page: number, size: number) => {
		setPagination({ ...pagination, currentPage: page, pageSize: size });
	};

	return (
		<div>
			<ThumbSearch />
			<div className="container mx-auto">
				<h1 className="text-3xl font-bold my-5">ON SALE!!!</h1>
				<ProductComponent prods={saleProducts} totalPage={totalPage} currentPage={pagination.currentPage} pageSize={pagination.pageSize} onPageChange={handlePageChange} />
			</div>
			<div className="container mx-auto mt-10">
				<h1 className="text-3xl font-bold my-5">NEW!!!</h1>
				<ProductComponent prods={newProducts} totalPage={totalPage} currentPage={pagination.currentPage} pageSize={pagination.pageSize} onPageChange={handlePageChange} />
			</div>
			<div className="container mx-auto mt-10">
				<h1 className="text-3xl font-bold my-5">FEATURE</h1>
				<ProductComponent prods={featureProducts} totalPage={totalPage} currentPage={pagination.currentPage} pageSize={pagination.pageSize} onPageChange={handlePageChange} />
			</div>
		</div>
	)
}

