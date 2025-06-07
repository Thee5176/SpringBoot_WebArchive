// Modal functionality
const addBtn = document.getElementById("addBookmarkBtn");
const modal = document.getElementById("addBookmarkModal");
const submitBtn = document.getElementById("submitBookmark");
const table = document
  .getElementById("bookmarksTable")
  .getElementsByTagName("tbody")[0];

addBtn.addEventListener("click", () => {
  modal.style.display = "flex";
});

// Close modal when clicking outside
window.addEventListener("click", (e) => {
  if (e.target === modal) {
    modal.style.display = "none";
  }
});

// Get DOM elements
const addTagBtn = document.getElementById('addTagBtn');
const addTagModal = document.getElementById('addTagModal');
const submitTagBtn = document.getElementById('submitTag');
const tagNameInput = document.getElementById('tagName');

// Open modal when + button is clicked
addTagBtn.addEventListener('click', () => {
    addTagModal.style.display = 'flex';
    tagNameInput.focus(); // Focus the input field when modal opens
});

// Close modal when clicking outside
window.addEventListener('click', (event) => {
    if (event.target === addTagModal) {
        addTagModal.style.display = 'none';
    }
});