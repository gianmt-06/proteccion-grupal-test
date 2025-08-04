import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from './store';
import Login from './Login';
import TaskList from './TaskList';

function App() {
  const token = useSelector((state: RootState) => state.auth.token);

  return (
    <Router>
      <Routes>
        <Route path="/" element={!token ? <Login /> : <Navigate to="/tasks" />} />
        <Route path="/tasks" element={token ? <TaskList /> : <Navigate to="/" />} />
      </Routes>
    </Router>
  );
}

export default App;
