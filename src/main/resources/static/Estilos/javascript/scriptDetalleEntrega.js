// Obtener los elementos del DOM
const profileButton = document.getElementById('profileButton');
const profileDropdown = document.getElementById('profileDropdown');
const themeToggle = document.getElementById('themeToggle');
const themeIcon = themeToggle.querySelector('i');
const modal = document.getElementById('gradeModal');
const closeButton = modal.querySelector('.close-button');
const gradeForm = document.getElementById('gradeForm');
const toast = document.getElementById('toast');

let selectedStudentId = null;
let idAula = document.body.getAttribute("data-id-aula");
let idActividad = document.body.getAttribute("data-id-actividad");

// Función para cambiar el tema
function toggleTheme() {
    document.body.classList.toggle('dark-theme');
    themeIcon.className = document.body.classList.contains('dark-theme') ? 'bx bx-moon' : 'bx bx-sun';
}

// Dropdown de perfil
function toggleDropdown() {
    profileDropdown.classList.toggle('show');
}

// Cerrar dropdown al hacer clic fuera
document.addEventListener('click', (e) => {
    if (!profileButton.contains(e.target)) {
        profileDropdown.classList.remove('show');
    }
});

// Abrir el modal y cargar datos del estudiante desde atributos de datos
function openGradeModal(element) {
    selectedStudentId = element.getAttribute('data-student-id');
    document.getElementById('studentName').value = element.getAttribute('data-student-name');
    document.getElementById('status').value = element.getAttribute('data-status');
    document.getElementById('grade').value = element.getAttribute('data-grade');
    document.getElementById('feedback').value = element.getAttribute('data-feedback');
    modal.classList.add('show');
}

// Cerrar modal
function closeModal() {
    modal.classList.remove('show');
    selectedStudentId = null;
}

// Mostrar mensaje de confirmación
function showToast(message) {
    toast.textContent = message;
    toast.classList.add('show');
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// Manejar el envío del formulario
gradeForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const calificacion = document.getElementById('grade').value;
    const feedback = document.getElementById('feedback').value;
    const estado = document.getElementById('status').value;

    fetch(`/aula/${idAula}/actividades/${idActividad}/entregas/${selectedStudentId}/calificar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            calificacion,
            feedback,
            estado,
        }),
    })
        .then((response) => {
            if (response.ok) {
                showToast('Calificación actualizada correctamente');
                closeModal();
            } else {
                showToast('Error al actualizar la calificación');
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            showToast('Error al actualizar la calificación');
        });
});

// Event Listeners
themeToggle.addEventListener('click', toggleTheme);
profileButton.addEventListener('click', toggleDropdown);
closeButton.addEventListener('click', closeModal);
modal.addEventListener('click', (e) => {
    if (e.target === modal) closeModal();
});
