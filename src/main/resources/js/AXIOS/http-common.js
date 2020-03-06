import axios from 'axios'
import nprogress from 'nprogress'

export const AXIOS = axios.create({
    baseURL: `/api`
});

AXIOS.interceptors.request.use(config => {
    nprogress.start();
    return config
});

AXIOS.interceptors.response.use(response => {
    nprogress.done();
    return response
});