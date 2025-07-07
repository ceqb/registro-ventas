function deleteTask(id) {
            fetch(`/api/v1/personal-tasks/deleteTask/${id}`, {
                method: 'DELETE'
            }).then(response => {
                if (response.ok) {
                  document.location.href="/api/v1/personal-tasks/listTask",true;
                } else {
                    alert('Error al eliminar la foto');
                }
            });
        }