import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import axios from 'axios';
import './style.css';

const API = 'http://localhost:8080/api';
const api = axios.create({ baseURL: API });

// Add JWT token to every request
api.interceptors.request.use((c) => {
  const t = localStorage.getItem('token');
  if (t) c.headers.Authorization = `Bearer ${t}`;
  return c;
});

function App() {
  const [jobs, setJobs] = useState([]);
  const [q, setQ] = useState('');
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('user') || 'null'));
  const [show, setShow] = useState(null); // 'login' | 'register' | null

  useEffect(() => {
    api.get('/jobs').then(r => setJobs(r.data)).catch(() => {});
  }, []);

  const filtered = jobs.filter(j => 
    j.title.toLowerCase().includes(q.toLowerCase()) ||
    j.company.toLowerCase().includes(q.toLowerCase())
  );

  const logout = () => {
    localStorage.clear();
    setUser(null);
  };

  return (
    <div className="container">
      <header>
        <h1>ConnectJob</h1>
        <input placeholder="Search jobs..." value={q} onChange={e=>setQ(e.target.value)} />
        <div>
          {user ? (
            <>
              <span>{user.email} ({user.role})</span>
              <button onClick={logout}>Logout</button>
            </>
          ) : (
            <>
              <button onClick={()=>setShow('login')}>Login</button>
              <button onClick={()=>setShow('register')}>Register</button>
            </>
          )}
        </div>
      </header>

      {show && <Auth mode={show} setMode={setShow} close={()=>setShow(null)} setUser={setUser} />}

      <div className="jobs">
        {filtered.map(job => (
          <div key={job.id} className="job-card">
            <h3>{job.title}</h3>
            <p><b>{job.company}</b> - {job.location}</p>
            <p>{job.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

function Auth({ mode, setMode, close, setUser }) {
  const [form, setForm] = useState({ name: '', email: '', password: '', role: 'JOB_SEEKER' });
  const [err, setErr] = useState('');

  const submit = async (e) => {
    e.preventDefault();
    try {
      const url = mode === 'login' ? '/auth/login' : '/auth/register';
      const { data } = await api.post(url, form);
      localStorage.setItem('token', data.token);
      localStorage.setItem('user', JSON.stringify({ email: data.email, role: data.role }));
      setUser({ email: data.email, role: data.role });
      close();
    } catch (ex) {
      setErr(ex.response?.data?.message || 'Failed');
    }
  };

  return (
    <div className="modal">
      <form onSubmit={submit} className="auth-form">
        <h2>{mode === 'login' ? 'Login' : 'Register'}</h2>
        {err && <p className="error">{err}</p>}
        {mode === 'register' && (
          <input placeholder="Name" value={form.name} onChange={e=>setForm({...form,name:e.target.value})} required />
        )}
        <input placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})} required />
        <input type="password" placeholder="Password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})} required />
        {mode === 'register' && (
          <select value={form.role} onChange={e=>setForm({...form,role:e.target.value})}>
            <option value="JOB_SEEKER">Job Seeker</option>
            <option value="RECRUITER">Recruiter</option>
          </select>
        )}
        <button type="submit">{mode === 'login' ? 'Login' : 'Register'}</button>
        <p onClick={()=> setMode(mode==='login'?'register':'login')}>
          {mode==='login' ? 'No account? Register' : 'Have account? Login'}
        </p>
        <button type="button" onClick={close}>Close</button>
      </form>
    </div>
  );
}

createRoot(document.getElementById('root')).render(<App />);
