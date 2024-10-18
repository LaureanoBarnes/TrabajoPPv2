document.addEventListener('DOMContentLoaded', function() {
    // Initialize Lucide icons
    lucide.createIcons();

    const profileIcon = document.getElementById('profile-icon');
    const profileDropdown = document.getElementById('profile-dropdown');
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const menuToggle = document.getElementById('menu-toggle');
    const leftAside = document.getElementById('left-aside');

    // Profile dropdown functionality
    if (profileIcon) {
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

    // Toggle Dark Mode and save to localStorage
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

    // Apply dark mode on page load if saved in localStorage
    if (localStorage.getItem('darkMode') === 'enabled') {
        document.body.classList.add('dark-mode');
    }

    if (darkModeToggle) {
        darkModeToggle.addEventListener('click', toggleDarkMode);
    }

    // Toggle the left-aside on mobile
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

    // Form submission handling (if needed)
    const createForumForm = document.getElementById('create-forum-form');
    if (createForumForm) {
        createForumForm.addEventListener('submit', function(e) {
            // Handle form submission via AJAX if needed
        });
    }
});
