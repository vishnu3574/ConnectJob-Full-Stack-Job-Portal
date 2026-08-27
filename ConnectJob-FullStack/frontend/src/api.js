import axios from 'axios';
const api = axios.create({
  baseURL: 'https://connectjob-backend-t8c9.onrender.com',
});
export default api;
