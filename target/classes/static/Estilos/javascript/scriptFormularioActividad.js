document.addEventListener('DOMContentLoaded', () => {
    // Initialize Lucide icons
    lucide.createIcons();

    // Theme Toggle
    const themeToggle = document.getElementById('themeToggle');
    let isDark = localStorage.getItem('theme') === 'dark';
    updateTheme();

    themeToggle.addEventListener('click', () => {
        isDark = !isDark;
        updateTheme();
        localStorage.setItem('theme', isDark ? 'dark' : 'light');
    });

    function updateTheme() {
        document.documentElement.setAttribute('data-theme', isDark ? 'dark' : 'light');
        themeToggle.innerHTML = isDark
            ? '<i data-lucide="sun"></i>'
            : '<i data-lucide="moon"></i>';
        lucide.createIcons();
    }

    // User Menu Toggle
    const userMenuBtn = document.getElementById('userMenuBtn');
    const userMenuDropdown = document.getElementById('userMenuDropdown');
    const chevronIcon = userMenuBtn.querySelector('[data-lucide="chevron-down"]');
    let isMenuOpen = false;

    userMenuBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        isMenuOpen = !isMenuOpen;
        userMenuDropdown.classList.toggle('show', isMenuOpen);
        chevronIcon.style.transform = isMenuOpen ? 'rotate(180deg)' : 'rotate(0)';
    });

    document.addEventListener('click', (e) => {
        if (!userMenuBtn.contains(e.target) && !userMenuDropdown.contains(e.target)) {
            isMenuOpen = false;
            userMenuDropdown.classList.remove('show');
            chevronIcon.style.transform = 'rotate(0)';
        }
    });

    userMenuDropdown.addEventListener('click', (e) => {
        e.stopPropagation();
    });

    // File Upload and Drag & Drop
    const dropZone = document.getElementById('dropZone');
    const fileInput = document.getElementById('file');
    const dropZoneContent = dropZone.querySelector('.drop-zone-content');
    let isDragging = false;

    dropZone.addEventListener('click', () => {
        fileInput.click();
    });

    fileInput.addEventListener('change', () => {
        updateDropZone();
    });

    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        if (!isDragging) {
            isDragging = true;
            dropZone.classList.add('dragging');
        }
    });

    dropZone.addEventListener('dragleave', (e) => {
        e.preventDefault();
        isDragging = false;
        dropZone.classList.remove('dragging');
    });

    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        isDragging = false;
        dropZone.classList.remove('dragging');

        const files = e.dataTransfer.files;
        if (files.length > 0) {
            fileInput.files = files;
            updateDropZone();
        }
    });

    function updateDropZone() {
        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];
            dropZoneContent.innerHTML = `<span>${file.name}</span>`;
        } else {
            dropZoneContent.innerHTML = `
            <i data-lucide="upload" class="upload-icon"></i>
            <div class="drop-zone-text">Arrastra y suelta tu archivo aquí o haz click para seleccionar</div>
            <p class="drop-zone-hint">PDF, DOC, DOCX hasta 10MB</p>
          `;
        }
    }
});