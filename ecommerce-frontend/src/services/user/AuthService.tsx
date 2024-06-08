import axios from 'axios';
import { SIGN_IN_REST_API_URL, SIGN_UP_REST_API_URL } from '../../utils/RestAPIConstants';
import { CORS_CONFIG } from '../../utils/CORSConfig';

export const signUp = (signUpInfor: SignUpRequest) => axios.post(SIGN_UP_REST_API_URL, signUpInfor, CORS_CONFIG);
export const signIn = (signInInfor: SignInRequest) => axios.post(SIGN_IN_REST_API_URL, signInInfor, CORS_CONFIG);