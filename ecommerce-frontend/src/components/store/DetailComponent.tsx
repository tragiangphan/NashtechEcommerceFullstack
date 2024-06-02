import { FormEvent, useEffect, useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Product } from '../../models/prod/entity/Product';
import { Carousel, InputNumber, InputNumberProps, Pagination } from 'antd';
import { Rating } from '../../models/cart/entity/Rating';
import { createRating, getAverageRatingByProductId, getRatingByProductId } from '../../services/cart/RatingServices';
import { PaginationModel } from '../../models/commons/PaginationModel';
import { getUserById } from '../../services/user/UserServices';
import { User } from '../../models/user/entity/User';
import { RatingRequest } from '../../models/cart/request/RatingRequest';
import { useCookies } from 'react-cookie';

export const DetailComponent: React.FC<{}> = () => {
  const { productName } = useParams<{ productName: string }>();
  const navigate = useNavigate();
  const [product, setProduct] = useState<Product | null>(null);
  const [ratings, setRatings] = useState<Rating[]>([]);
  const [avgRating, setAvgRatings] = useState<number>(0);
  const [cookies, setCookies] = useCookies(['username'])
  const [totalPage, setTotalPage] = useState(0);
  const commentRef = useRef<HTMLTextAreaElement>(null);
  const rateScoreRef = useRef<HTMLInputElement>(null);
  const quantityRef = useRef<HTMLInputElement>(null);
  const [pagination, setPagination] = useState<PaginationModel>({
    direction: 'ASC',
    currentPage: 1,
    pageSize: 5
  });

  useEffect(() => {
    // Lấy thông tin sản phẩm từ localStorage
    const productData = localStorage.getItem('productDetail');
    if (productData) {
      const parsedProduct = JSON.parse(productData);
      console.log(parsedProduct);

      // Kiểm tra xem tên sản phẩm có khớp với URL không
      if (parsedProduct.productName === productName) {
        setProduct(parsedProduct);
      } else {
        // Điều hướng về trang store nếu không khớp
        navigate('/store');
      }
    } else {
      // Điều hướng về trang store nếu không có dữ liệu sản phẩm
      navigate('/store');
    }
  }, [productName, navigate]);

  useEffect(() => {
    if (product && product.id !== undefined) {
      fetchRatings(product.id);
    }
  }, [product]);

  if (!product) {
    return <div>Loading...</div>;
  }

  const fetchUser = async (userId: number): Promise<User | null> => {
    try {
      const res = await getUserById(userId, pagination);
      console.log(res.data);
      const user: User = {
        id: res.data.users[0].id,
        username: res.data.users[0].username,
        email: res.data?.users[0].email,
        password: res.data?.users[0].password,
        role: res.data?.users[0].roleId,
        createdAt: res.data?.users[0].createdOn
      };
      return user;
    } catch (err) {
      console.error(err);
      return null;
    }
  };

  const fetchRatings = async (productId: number) => {
    try {
      const avgResponse = await getAverageRatingByProductId(productId, pagination);
      console.log(avgResponse.data);
      const prodResponse = await getRatingByProductId(productId, pagination);
      console.log(prodResponse.data);
      setAvgRatings(avgResponse.data);
      setTotalPage(prodResponse.data?.totalElement)

      if (Array.isArray(prodResponse.data.ratings)) {
        const ratingPromises: Rating[] = prodResponse.data.ratings.map(async (res: any) => ({
          id: res.id,
          createOn: res.createOn,
          updateOn: res.lastUpdatedOn,
          comment: res.comment,
          rateScore: res.rateScore,
          prodId: res.productId,
          user: await fetchUser(res.userId)
        }));
        // Chờ tất cả các promise được giải quyết
        const resolvedRatings = await Promise.all(ratingPromises);
        setRatings(resolvedRatings);
      } else {
        console.error("Rating data is not an array:", prodResponse.data);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleAddToCart = () => {

  }

  const handleSubmitReview = (event: FormEvent) => {
    event.preventDefault();

    const rateScoreValue = rateScoreRef.current?.value;
    const commentValue = commentRef.current?.value;

    console.log("Rate Score Value:", rateScoreValue);
    console.log("Comment Value:", commentValue);

    if (rateScoreValue === undefined || rateScoreValue === null) {
      console.error("Rate score is invalid");
      return;
    }

    if (commentValue === undefined || commentValue === null) {
      console.error("Comment is invalid");
      return;
    }

    const rating: RatingRequest = {
      username: cookies.username,
      rateScore: Number(rateScoreValue),
      comment: commentValue,
      productId: product.id,
    };

    console.log(rating);

    createRating(rating)
      .then((res) => {
        console.log(res.data);
        fetchRatings(product.id);
      })
      .catch((err) => console.error(err));
  };


  const handlePageChange = (page: number, size: number) => {
    setPagination({ ...pagination, currentPage: page, pageSize: size });
  };

  return (
    <div className="max-w-4xl mx-auto p-4">
      <div className="flex flex-col md:flex-row bg-white shadow-md rounded-lg overflow-hidden">
        <div className="md:w-1/3">
          <Carousel arrows infinite={true} >
            {/* <!-- Items --> */}
            {product.images.map(img => (
              <div key={img.id}>
                <img src={img.url} className="w-full h-auto object-cover p-10" alt={img.desc} />
              </div>
            ))}
          </Carousel>
        </div>
        <div className="md:w-2/3 p-4">
          <h1 className="text-3xl font-bold mb-2">{product.productName}</h1>
          <p className="text-gray-700 mb-4">
            {product.productDesc}
          </p>
          <p className="text-lg font-semibold mb-2">
            Supplier: {product.unit}
          </p>
          <p className="mb-4">
            <strong className="text-green-600">${product.price}</strong>
            {/* <span className="line-through text-gray-500">$49.99</span> */}
          </p>
          <form onSubmit={handleAddToCart}>
            <div className="flex flex-col md:flex-row items-center mb-4 space-y-2 md:space-y-0 md:space-x-4">
              <label htmlFor="stars-input" className="text-sm font-medium text-gray-900 dark:text-white">Quantity:</label>
              <InputNumber ref={quantityRef} className='text-sm p-1 text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500' id='star-input' min={1} max={5} defaultValue={5} />
              <button type="button" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Add to Cart</button>
            </div>
          </form>
        </div>
      </div>
      <div className="mt-6">
        <h2 className="text-2xl font-bold mb-4">Customer Reviews</h2>
        <div className="flex items-center">
          <svg className="w-6 h-6 text-yellow-300 me-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
            <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z" />
          </svg>
          <p className="ms-1 text-xl font-bold text-gray-900 dark:text-white">{avgRating}</p>
          <span className="w-1.5 h-1.5 mx-2 bg-gray-500 rounded-full dark:bg-gray-400"></span>
          <span className="text-xl first-letter:font-medium text-gray-900 dark:text-white">{ratings.length} {ratings.length > 1 ? "reviews" : "review"}</span>
        </div>

        <div className="space-y-4 my-4">
          {ratings && ratings.length > 0 ? (
            <div className="space-y-4">
              {ratings.map((rate) => (
                <div key={rate.id} className="border p-4 rounded">
                  <div className="flex justify-between">
                    <p className="text-lg mb-1 font-semibold">{rate.user.username}</p>
                    <div className='flex items-center'>
                      <p className="me-1 text-xl font-bold text-gray-900 dark:text-white">{rate.rateScore}</p>
                      <svg className="w-6 h-6 text-yellow-300 me-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
                        <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z" />
                      </svg>
                    </div>
                  </div>
                  <p className="text-md text-gray-600">{rate.comment}</p>
                  <p className="text-gray-500 text-sm mt-2">{new Date(rate.createOn).toUTCString()}</p>
                </div>
              ))}
            </div>
          ) : (
            <span className="text-lg font-bold text-gray-400 italic">No Review Yet</span>
          )}
        </div>

        <div className='grid content-center'>
          <Pagination className='my-5 mx-auto' onChange={(page, size) => handlePageChange(page, size)}
            current={pagination.currentPage} total={totalPage} pageSize={pagination.pageSize} />
        </div>

        <div className="mt-6">
          <h3 className="text-xl font-bold mb-2">Write a Review</h3>
          <form onSubmit={handleSubmitReview} className="space-y-4 mx-auto">
            <div className="mb-5">
              <label htmlFor="message" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Comment</label>
              <textarea ref={commentRef} id="message" rows={4} className="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Leave a comment..."></textarea>
            </div>
            <div className="mb-5">
              <label htmlFor="stars-input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Rating Star:</label>
              <InputNumber ref={rateScoreRef} className='block p-2 w-full text-base text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500' id='star-input' min={1} max={5} defaultValue={5} />
              <p id="helper-text-explanation" className="mt-2 text-sm text-gray-500 dark:text-gray-400">Please select the number of stars.</p>
            </div>
            <button type="submit" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Submit Review</button>
          </form>
        </div>
      </div>
    </div>
  );
};
