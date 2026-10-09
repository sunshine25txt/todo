const KEY = "tod-android-preview";

function load() {
  return JSON.parse(localStorage.getItem(KEY) || '{"users":[],"session":null,"tasks":null}');
}
function save(state) {
  localStorage.setItem(KEY, JSON.stringify(state));
}

const defaultTasks = [
  { id: "1", title: "Learning Programming by 12PM", done: true },
  { id: "2", title: "Learn how to cook by 1PM", done: false },
  { id: "3", title: "Learn how to play at 3PM", done: false },
  { id: "4", title: "Have lunch at 4PM", done: false },
  { id: "5", title: "Going to travel 6PM", done: false }
];

let state = load();
if (!state.tasks) state.tasks = defaultTasks;

function show(id) {
  document.querySelectorAll(".screen").forEach((el) => el.classList.add("hidden"));
  document.getElementById(id).classList.remove("hidden");
  if (id === "dashboard") renderDashboard();
}

function currentUser() {
  return state.users.find((u) => u.email === state.session) || null;
}

document.querySelectorAll("[data-go]").forEach((el) => {
  el.addEventListener("click", () => show(el.dataset.go));
});

document.getElementById("registerBtn").onclick = () => {
  const name = document.getElementById("regName").value.trim();
  const email = document.getElementById("regEmail").value.trim();
  const password = document.getElementById("regPass").value;
  const confirm = document.getElementById("regConfirm").value;
  const err = document.getElementById("regError");
  if (!name || !email || !password) return (err.textContent = "Please fill in every field.");
  if (!email.includes("@")) return (err.textContent = "Enter a valid email.");
  if (password.length < 6) return (err.textContent = "Password must be at least 6 characters.");
  if (password !== confirm) return (err.textContent = "Passwords do not match.");
  if (state.users.some((u) => u.email.toLowerCase() === email.toLowerCase())) {
    return (err.textContent = "An account with this email already exists.");
  }
  state.users.push({ name, email, password });
  state.session = email;
  save(state);
  err.textContent = "";
  show("dashboard");
};

document.getElementById("loginBtn").onclick = () => {
  const email = document.getElementById("loginEmail").value.trim();
  const password = document.getElementById("loginPass").value;
  const err = document.getElementById("loginError");
  const user = state.users.find((u) => u.email.toLowerCase() === email.toLowerCase());
  if (!user) return (err.textContent = "No account found for this email.");
  if (user.password !== password) return (err.textContent = "Incorrect password.");
  state.session = user.email;
  save(state);
  err.textContent = "";
  show("dashboard");
};

document.getElementById("signOut").onclick = () => {
  state.session = null;
  save(state);
  show("login");
};

function greeting() {
  const h = new Date().getHours();
  if (h < 12) return "Good Morning";
  if (h < 17) return "Good Afternoon";
  return "Good Evening";
}

function renderDashboard() {
  const user = currentUser();
  const name = user ? user.name : "Jeegar goyani";
  document.getElementById("welcomeName").textContent = `Welcome ${name}`;
  const parts = name.split(" ").map((p) => p[0]).join("").slice(0, 2).toUpperCase();
  document.querySelector(".avatar").textContent = parts || "JG";
  document.getElementById("greeting").textContent = greeting();
  const list = document.getElementById("taskList");
  list.innerHTML = "";
  state.tasks.forEach((task) => {
    const li = document.createElement("li");
    li.innerHTML = `<input type="checkbox" ${task.done ? "checked" : ""} /><span>${task.title}</span>`;
    li.onclick = () => {
      task.done = !task.done;
      save(state);
      renderDashboard();
    };
    list.appendChild(li);
  });
}

const modal = document.getElementById("modal");
const modalInput = document.getElementById("modalInput");
const modalInput2 = document.getElementById("modalInput2");
const modalTitle = document.getElementById("modalTitle");
const modalError = document.getElementById("modalError");
let modalMode = "add";

function openModal(mode) {
  modalMode = mode;
  modal.classList.remove("hidden");
  modalError.textContent = "";
  modalInput.value = "";
  modalInput2.value = "";
  if (mode === "add") {
    modalTitle.textContent = "Add daily task";
    modalInput.placeholder = "Task title";
    modalInput2.classList.add("hidden");
  } else {
    modalTitle.textContent = "Reset password";
    modalInput.placeholder = "Email";
    modalInput2.classList.remove("hidden");
    modalInput2.placeholder = "New password";
  }
}

document.getElementById("addTask").onclick = () => openModal("add");
document.getElementById("forgotLink").onclick = () => openModal("reset");
document.getElementById("modalCancel").onclick = () => modal.classList.add("hidden");
document.getElementById("modalOk").onclick = () => {
  if (modalMode === "add") {
    const title = modalInput.value.trim();
    if (!title) return (modalError.textContent = "Enter a task.");
    state.tasks.push({ id: String(Date.now()), title, done: false });
    save(state);
    modal.classList.add("hidden");
    renderDashboard();
  } else {
    const email = modalInput.value.trim();
    const pass = modalInput2.value;
    const user = state.users.find((u) => u.email.toLowerCase() === email.toLowerCase());
    if (!user) return (modalError.textContent = "No account found for this email.");
    if (pass.length < 6) return (modalError.textContent = "Password must be at least 6 characters.");
    user.password = pass;
    save(state);
    modalError.textContent = "Password updated. You can log in now.";
  }
};

function drawClock() {
  const canvas = document.getElementById("clock");
  const ctx = canvas.getContext("2d");
  const now = new Date();
  const w = canvas.width;
  const h = canvas.height;
  const c = w / 2;
  const r = w / 2 - 8;
  ctx.clearRect(0, 0, w, h);
  ctx.beginPath();
  ctx.arc(c, c, r, 0, Math.PI * 2);
  ctx.fillStyle = "#fff";
  ctx.fill();
  ctx.strokeStyle = "#e4eeec";
  ctx.lineWidth = 3;
  ctx.stroke();
  for (let i = 0; i < 12; i++) {
    const a = ((i * 30 - 90) * Math.PI) / 180;
    ctx.beginPath();
    ctx.moveTo(c + Math.cos(a) * r * 0.78, c + Math.sin(a) * r * 0.78);
    ctx.lineTo(c + Math.cos(a) * r * 0.9, c + Math.sin(a) * r * 0.9);
    ctx.strokeStyle = "#9aaeab";
    ctx.lineWidth = i % 3 === 0 ? 3.5 : 2;
    ctx.lineCap = "round";
    ctx.stroke();
  }
  const hour = ((now.getHours() % 12) + now.getMinutes() / 60) * 30;
  const min = now.getMinutes() * 6;
  const sec = now.getSeconds() * 6;
  function hand(deg, len, width, color) {
    const a = ((deg - 90) * Math.PI) / 180;
    ctx.beginPath();
    ctx.moveTo(c, c);
    ctx.lineTo(c + Math.cos(a) * len, c + Math.sin(a) * len);
    ctx.strokeStyle = color;
    ctx.lineWidth = width;
    ctx.lineCap = "round";
    ctx.stroke();
  }
  hand(hour, r * 0.45, 7, "#4ec8c6");
  hand(min, r * 0.62, 5, "#2c3a39");
  hand(sec, r * 0.7, 2, "#4ec8c6");
  ctx.beginPath();
  ctx.arc(c, c, 6, 0, Math.PI * 2);
  ctx.fillStyle = "#4ec8c6";
  ctx.fill();
}

setInterval(() => {
  const d = new Date();
  document.getElementById("statusTime").textContent = d.toLocaleTimeString([], {
    hour: "numeric",
    minute: "2-digit"
  });
  drawClock();
}, 1000);
drawClock();

if (currentUser()) show("dashboard");
else show("splash");
