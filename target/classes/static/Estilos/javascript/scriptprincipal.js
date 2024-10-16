document.addEventListener('DOMContentLoaded', function() {
    lucide.createIcons();

    const profileIcon = document.getElementById('profile-icon');
    const profileDropdown = document.getElementById('profile-dropdown');
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const menuToggle = document.getElementById('menu-toggle');
    const leftAside = document.getElementById('left-aside');

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

    if (profileIcon && profileDropdown) {
        profileIcon.addEventListener('click', function(e) {
            e.stopPropagation();
            profileDropdown.classList.toggle('show');
        });
        document.addEventListener('click', function(e) {
            if (!profileDropdown.contains(e.target) && !profileIcon.contains(e.target)) {
                profileDropdown.classList.remove('show');
            }
        });
    }

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

    if (menuToggle && leftAside) {
        menuToggle.addEventListener('click', () => {
            leftAside.classList.toggle('show');
        });
        document.addEventListener('click', (e) => {
            if (!leftAside.contains(e.target) && !menuToggle.contains(e.target)) {
                leftAside.classList.remove('show');
            }
        });
        window.addEventListener('resize', () => {
            if (window.innerWidth > 768) {
                leftAside.classList.remove('show');
            }
        });
    }
});
