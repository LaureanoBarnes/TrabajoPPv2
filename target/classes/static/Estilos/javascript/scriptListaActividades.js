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
    const activityTitle = element.getAttribute('data-activity-title');
    const activityDescription = element.getAttribute('data-activity-description');
    const activityDeadline = element.getAttribute('data-activity-deadline');
    const activityFileUrl = `/upload/${element.getAttribute('data-activity-file')}`;
    const activityEntregado = element.getAttribute('data-activity-entregado');

    document.getElementById('activityTitle').textContent = activityTitle;
    document.getElementById('activityDescription').textContent = activityDescription;
    document.getElementById('activityDeadline').textContent = activityDeadline;
    document.getElementById('activityFile').href = activityFileUrl;

    // Verifica si hay un archivo entregado para mostrar el ícono de entrega
    const entregaMessage = document.getElementById('entregaMessage');
    entregaMessage.style.display = activityEntregado ? 'inline' : 'none';
    entregaMessage.href = activityEntregado ? `/upload/${activityEntregado}` : '#';

    // Verificar expiración de fecha de entrega
    const deliveryFormContainer = document.getElementById('deliveryFormContainer');
    const expiredMessage = document.getElementById('expiredMessage');
    const deliveryForm = document.getElementById('deliveryForm');

    const deadlineDate = new Date(activityDeadline);
    const currentDate = new Date();

    if (currentDate > deadlineDate) {
        // Fecha de entrega ha expirado
        deliveryForm.style.display = 'none';
        expiredMessage.style.display = 'block';
    } else {
        // Fecha de entrega aún no expira
        deliveryForm.style.display = 'block';
        expiredMessage.style.display = 'none';
    }

    activityModal.classList.add('show');
}

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
