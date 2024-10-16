document.addEventListener('DOMContentLoaded', function() {
    lucide.createIcons();

    const profileIcon = document.getElementById('profile-icon');
    const profileDropdown = document.getElementById('profile-dropdown');
    const darkModeToggle = document.getElementById('dark-mode-toggle');

    if (profileIcon && profileDropdown) {
        profileIcon.addEventListener('click', function(e) {
            e.stopPropagation();
            profileDropdown.classList.toggle('show');
        });

        document.addEventListener('click', function() {
            profileDropdown.classList.remove('show');
        });
    }

    // Función para alternar modo oscuro y guardar en localStorage
    const toggleDarkMode = () => {
        document.body.classList.toggle('dark-mode');
        const isDarkMode = document.body.classList.contains('dark-mode');
        localStorage.setItem('darkMode', isDarkMode ? 'enabled' : 'disabled');

        if (darkModeToggle) {
            const icon = darkModeToggle.querySelector('i');
            if (icon) {
                icon.setAttribute('data-lucide', isDarkMode ? 'sun' : 'moon');
                lucide.createIcons();
            }
        }
    };

    // Aplicar estado de modo oscuro desde localStorage al cargar
    if (localStorage.getItem('darkMode') === 'enabled') {
        document.body.classList.add('dark-mode');
        if (darkModeToggle) {
            const icon = darkModeToggle.querySelector('i');
            if (icon) {
                icon.setAttribute('data-lucide', 'sun');
            }
        }
    }

    if (darkModeToggle) {
        darkModeToggle.addEventListener('click', toggleDarkMode);
    }

    // File upload functionality
    const uploadForm = document.getElementById('upload-form');
    const fileInput = document.getElementById('file-upload');
    const fileName = document.getElementById('file-name');
    const uploadMessage = document.getElementById('upload-message');

    if (fileInput && fileName) {
        fileInput.addEventListener('change', function() {
            fileName.textContent = this.files[0] ? this.files[0].name : 'Ningún archivo seleccionado';
        });
    }

    if (uploadForm) {
        uploadForm.addEventListener('submit', function(e) {
            if (!fileInput.files[0]) {
                e.preventDefault();
                showMessage('Por favor, seleccione un archivo.', 'error');
            }
        });
    }

    function showMessage(message, type) {
        if (uploadMessage) {
            uploadMessage.textContent = message;
            uploadMessage.className = 'message ' + type;
            setTimeout(() => {
                uploadMessage.textContent = '';
                uploadMessage.className = 'message';
            }, 3000);
        }
    }

    const materialList = document.getElementById('material-list');
    if (materialList) {
        materialList.addEventListener('click', function(e) {
            if (e.target.closest('.options-btn')) {
                const id = e.target.closest('.options-btn').dataset.id;
                document.querySelectorAll('.options-dropdown').forEach(dropdown => {
                    dropdown.classList.remove('show');
                });
                document.getElementById(`dropdown-${id}`).classList.toggle('show');
            }
        });

        document.addEventListener('click', function(e) {
            if (!e.target.closest('.material-options')) {
                document.querySelectorAll('.options-dropdown').forEach(dropdown => {
                    dropdown.classList.remove('show');
                });
            }
        });
    }
});

function mostrarFormularioEditar(archivoId) {
    var form = document.getElementById("form-editar-" + archivoId);
    if (form) {
        form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
    }
}

window.addEventListener("scroll", function () {
    const sidebar = document.querySelector(".left-aside");
    if (sidebar) {
        sidebar.style.top = window.scrollY > 0 ? "0" : "70px";
    }
});
