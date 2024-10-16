document.addEventListener('DOMContentLoaded', function() {
    lucide.createIcons();

    const darkModeToggle = document.getElementById('darkModeToggle');
    const menuToggle = document.getElementById('menuToggle');
    const leftAside = document.getElementById('leftAside');
    const profileDropdown = document.querySelector('.profile-dropdown');

    // Función para alternar modo oscuro y guardar en localStorage
    const toggleDarkMode = () => {
        document.body.classList.toggle('dark-mode');
        const isDarkMode = document.body.classList.contains('dark-mode');
        localStorage.setItem('darkMode', isDarkMode ? 'enabled' : 'disabled');

        const icon = darkModeToggle.querySelector('i');
        if (icon) {
            icon.setAttribute('data-lucide', isDarkMode ? 'sun' : 'moon');
            lucide.createIcons();
        }
    };

    // Aplicar estado de modo oscuro desde localStorage al cargar
    if (localStorage.getItem('darkMode') === 'enabled') {
        document.body.classList.add('dark-mode');
    }

    if (darkModeToggle) {
        darkModeToggle.addEventListener('click', toggleDarkMode);
    }

    // Animación para los elementos de noticias
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

    // Mobile menu toggle
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

    // Profile dropdown functionality
    if (profileDropdown) {
        const profileButton = profileDropdown.querySelector('.profile-button');
        const dropdownContent = profileDropdown.querySelector('.dropdown-content');

        profileButton.addEventListener('click', (e) => {
            e.stopPropagation();
            dropdownContent.classList.toggle('show');
        });
        document.addEventListener('click', () => {
            dropdownContent.classList.remove('show');
        });
    }

    // Settings dropdown functionality
    const settingsButtons = document.querySelectorAll('.settings-btn');
    settingsButtons.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.stopPropagation();
            const dropdown = btn.nextElementSibling;
            if (dropdown) {
                dropdown.classList.toggle('show');
            }
        });
    });

    document.addEventListener('click', () => {
        const dropdowns = document.querySelectorAll('.dropdown-content');
        dropdowns.forEach(dropdown => {
            dropdown.classList.remove('show');
        });
    });
});
