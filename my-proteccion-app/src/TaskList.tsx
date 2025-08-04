import { useEffect, useState } from 'react';

interface Task {
  taskId: number;
  name: string;
  status: string;
}

function TaskList() {
  const [userId, setUserId] = useState<string | null>(null);
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedUserId = localStorage.getItem('uid');
    if (storedUserId) {
      setUserId(storedUserId);
        }
  }, []);

  useEffect(() => {
    if (!userId) return;

    const fetchTasks = async () => {
      try {
        const response = await fetch(`http://localhost:8080/tasks/user/${userId}`, {
          credentials: 'include',
        });

        if (!response.ok) {
          throw new Error('Failed to fetch tasks');
        }

        const data = await response.json();
        setTasks(data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    fetchTasks();
  }, [userId]);

  if (loading) {
    return <p>Loading tasks...</p>;
  }

  return (
    <div>
      <h3>Task List</h3>
      <table className="table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map(task => (
            <tr key={task.taskId}>
              <td>{task.name}</td>
              <td>{task.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TaskList;
