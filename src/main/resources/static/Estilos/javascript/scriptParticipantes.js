document.addEventListener('DOMContentLoaded', function () {
    // Inicializar los íconos de Lucide
    lucide.createIcons();

    // Modo oscuro
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const applyDarkMode = () => {
        document.body.classList.toggle('dark-mode');
        const isDarkMode = document.body.classList.contains('dark-mode');
        localStorage.setItem('darkMode', isDarkMode ? 'enabled' : 'disabled');

        const icon = darkModeToggle.querySelector('i');
        icon.setAttribute('data-lucide', isDarkMode ? 'sun' : 'moon');
        lucide.createIcons();
    };

    // Aplicar el estado del modo oscuro desde localStorage
    if (localStorage.getItem('darkMode') === 'enabled') {
        document.body.classList.add('dark-mode');
    }

    if (darkModeToggle) {
        darkModeToggle.addEventListener('click', applyDarkMode);
    }

    // Funcionalidad para el dropdown del perfil
    const profileIcon = document.getElementById('profile-icon');
    const profileDropdown = document.getElementById('profile-dropdown');

    if (profileIcon && profileDropdown) {
        profileIcon.addEventListener('click', function (e) {
            e.stopPropagation();
            // Alternar la visibilidad del dropdown
            profileDropdown.classList.toggle('show');
        });

        document.addEventListener('click', function (e) {
            // Cierra el dropdown si se hace clic fuera de él
            if (!profileDropdown.contains(e.target) && !profileIcon.contains(e.target)) {
                profileDropdown.classList.remove('show');
            }
        });
    }

    // Mobile menu toggle (si aplica)
    const menuToggle = document.getElementById('menuToggle');
    const leftAside = document.getElementById('leftAside');

    if (menuToggle && leftAside) {
        menuToggle.addEventListener('click', () => {
            leftAside.classList.toggle('show');
        });
        document.addEventListener('click', (e) => {
            if (!leftAside.contains(e.target) && !menuToggle.contains(e.target)) {
                leftAside.classList.remove('show');
            }
        });
    }

    // Animación para los elementos de noticias (si aplica)
    const newsItems = document.querySelectorAll('.news-item');
    newsItems.forEach((item, index) => {
        item.style.opacity = '0';
        item.style.transform = 'translateY(20px)';
        setTimeout(() => {
            item.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            item.style.opacity = '1';
            item.style.transform = 'translateY(0)';
        }, 100 * (index + 1));
    });
});
