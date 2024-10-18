document.addEventListener('DOMContentLoaded', function() {
    lucide.createIcons();

    const profileIcon = document.getElementById('profile-icon');
    const profileDropdown = document.getElementById('profile-dropdown');
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const menuToggle = document.getElementById('menu-toggle');
    const leftAside = document.getElementById('left-aside');

    // Función para alternar el dropdown del perfil
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

    // Script de interacciones de foro (likes, responder)
    const idAula = document.getElementById('foro-container').dataset.idAula;
    const foroId = document.getElementById('foro-container').dataset.idForo;

    // Función para "Me gusta"
    document.querySelectorAll('.btn-reaccion').forEach(button => {
        button.addEventListener('click', function() {
            const mensajeId = this.dataset.id;
            const botonReaccion = this;
            const contadorLikes = document.getElementById(`likes-count-${mensajeId}`);

            fetch(`/aula/${idAula}/foro/${foroId}/mensaje/${mensajeId}/reaccion`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ tipo: "LIKE" })
            })
                .then(response => response.text())
                .then(nuevaCantidad => {
                    const textoBoton = botonReaccion.textContent === "Me gusta" ? "Quitar Me gusta" : "Me gusta";
                    botonReaccion.textContent = textoBoton;
                    contadorLikes.textContent = nuevaCantidad;
                })
                .catch(() => {
                    alert('Ocurrió un error al intentar reaccionar.');
                });
        });
    });

    document.querySelectorAll('.btn-responder').forEach(button => {
        button.addEventListener('click', function() {
            const respuestaDiv = this.nextElementSibling;
            respuestaDiv.style.display = respuestaDiv.style.display === 'none' ? 'block' : 'none';

            // Encuentra el contenedor del mensaje principal (el ancestro más cercano con la clase 'mensaje-principal')
            const mensajePrincipal = this.closest('.mensaje-principal');

            // Busca el elemento que contiene el nombre del autor dentro del mensaje principal
            const autorNombre = mensajePrincipal.querySelector('p').textContent;

            // Asigna el nombre del autor al placeholder del textarea
            respuestaDiv.querySelector('textarea').placeholder = `Respondiendo a ${autorNombre}...`;
        });
    });
});
