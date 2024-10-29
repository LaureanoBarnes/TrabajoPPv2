// DOM Elements
const profileButton = document.getElementById('profileButton');
const profileDropdown = document.getElementById('profileDropdown');
const themeToggle = document.getElementById('themeToggle');
const themeIcon = themeToggle.querySelector('i');
const activityModal = document.getElementById('activityModal');
const feedbackModal = document.getElementById('feedbackModal');
const entregaStatus = document.getElementById('entregaStatus');
const deliveryFormContainer = document.getElementById('deliveryFormContainer');
const entregaMessage = document.getElementById('entregaMessage');

document.addEventListener("DOMContentLoaded", function() {
    lucide.createIcons();
});

// Toggle Theme
function toggleTheme() {
    document.body.classList.toggle('dark-theme');
    themeIcon.className = document.body.classList.contains('dark-theme') ? 'bx bx-moon' : 'bx bx-sun';
}

// Toggle Dropdown
function toggleDropdown() {
    profileDropdown.classList.toggle('show');
}

// Open Activity Modal
function openActivityModal(element) {
    const idAula = document.body.getAttribute('data-id-aula');
    const idActividad = element.getAttribute('data-activity-id');
    const activityTitle = element.getAttribute('data-activity-title');
    const activityDescription = element.getAttribute('data-activity-description');
    const activityDeadline = element.getAttribute('data-activity-deadline');
    const activityFileUrl = `/upload/${element.getAttribute('data-activity-file')}`;
    const activityEntregado = element.getAttribute('data-activity-entregado');

    document.getElementById('activityTitle').textContent = activityTitle;
    document.getElementById('activityDescription').textContent = activityDescription;
    document.getElementById('activityDeadline').textContent = activityDeadline;
    document.getElementById('activityFile').href = activityFileUrl;

    const entregaMessage = document.getElementById('entregaMessage');
    entregaMessage.style.display = activityEntregado ? 'inline' : 'none';
    entregaMessage.href = activityEntregado ? `/upload/${activityEntregado}` : '#';

    const deliveryForm = document.getElementById('deliveryForm');
    deliveryForm.setAttribute('data-activity-id', idActividad); // Almacena el ID de actividad

    // Verifica la fecha límite para habilitar o deshabilitar la entrega
    const deadlineDate = new Date(activityDeadline);
    const currentDate = new Date();
    const expiredMessage = document.getElementById('expiredMessage');

    if (currentDate > deadlineDate) {
        deliveryForm.style.display = 'none';
        expiredMessage.style.display = 'block';
    } else {
        deliveryForm.style.display = 'block';
        expiredMessage.style.display = 'none';
    }

    activityModal.classList.add('show');
}

function deleteActivity(id) {
    const idAula = document.body.getAttribute('data-id-aula'); // Obtener id_aula del atributo de <body>
    if (confirm("¿Está seguro de que desea eliminar esta actividad?")) {
        fetch(`/aula/${idAula}/actividades/${id}/eliminar`, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                // Eliminar la fila de actividad correspondiente del DOM
                const activityRow = document.getElementById(`actividad-${id}`);
                if (activityRow) {
                    activityRow.remove();
                }
            }
        }
        );
    }
}
// Configura el formulario de entrega para que use la URL de subida dinámica
document.getElementById('deliveryForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita el envío predeterminado
    const idAula = document.body.getAttribute('data-id-aula');
    const idActividad = this.getAttribute('data-activity-id'); // Obtiene el ID de actividad desde el atributo

    // Construye la URL de subida dinámica
    const uploadUrl = `/aula/${idAula}/actividades/${idActividad}/entregas/subir`;
    this.setAttribute('action', uploadUrl);

    // Envía el formulario después de configurar la acción
    this.submit();
});

// Open Feedback Modal
function openFeedbackModal(element, activityId) {
    const feedbackFile = `/upload/${element.getAttribute('data-feedback-file')}`;
    const feedbackDate = element.getAttribute('data-feedback-date');
    const feedbackGrade = element.getAttribute('data-feedback-grade');
    const feedbackText = element.getAttribute('data-feedback-text');

    document.getElementById('feedbackFile').href = feedbackFile;
    document.getElementById('feedbackDate').textContent = feedbackDate;
    document.getElementById('feedbackGrade').textContent = feedbackGrade;
    document.getElementById('feedbackText').textContent = feedbackText;

    feedbackModal.classList.add('show');
}

// Close Modal
function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('show');
}

// Event Listeners
themeToggle.addEventListener('click', toggleTheme);
profileButton.addEventListener('click', toggleDropdown);
document.querySelectorAll('.close-button').forEach(button => button.addEventListener('click', () => closeModal(button.closest('.modal').id)));
