const menuButton = document.querySelector('[data-menu-button]');
const menu = document.querySelector('[data-menu]');
if (menuButton && menu) {
  menuButton.addEventListener('click', () => {
    const open = menu.classList.toggle('is-open');
    menuButton.setAttribute('aria-expanded', String(open));
  });
  menu.querySelectorAll('a').forEach(link => link.addEventListener('click', () => {
    menu.classList.remove('is-open');
    menuButton.setAttribute('aria-expanded', 'false');
  }));
}

const modal = document.querySelector('[data-modal]');
const modalImage = document.querySelector('[data-modal-image]');
const modalCaption = document.querySelector('[data-modal-caption]');
const closeModal = () => modal?.classList.remove('is-open');
document.querySelectorAll('[data-shot]').forEach(button => button.addEventListener('click', () => {
  modalImage.src = button.dataset.full;
  modalImage.alt = button.dataset.alt;
  modalCaption.textContent = button.dataset.alt;
  modal.classList.add('is-open');
  modal.querySelector('button').focus();
}));
modal?.addEventListener('click', event => { if (event.target === modal) closeModal(); });
document.querySelector('[data-close-modal]')?.addEventListener('click', closeModal);
document.addEventListener('keydown', event => { if (event.key === 'Escape') closeModal(); });
