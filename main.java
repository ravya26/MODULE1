console.log("Welcome to the Community Portal");

window.pageLoaded = () => {
  alert("Page fully loaded!");
  renderEvents(events);
};

const events = [
  { id: 1, name: "Music Fest", date: new Date("2025-08-01"), seats: 10, category: "music" },
  { id: 2, name: "Coding Bootcamp", date: new Date("2025-06-15"), seats: 5, category: "coding" },
  { id: 3, name: "Baking Workshop", date: new Date("2025-07-20"), seats: 5, category: "food" },
];

function renderEvents(eventList) {
  const container = document.querySelector("#eventsContainer");
  container.innerHTML = "";

  eventList.forEach(event => {
    if (event.seats > 0 && event.date >= new Date()) {
      const card = document.createElement("div");
      card.className = "col-md-4 mb-3";
      card.innerHTML = `
        <div class="card h-100">
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">${event.name}</h5>
            <p>Date: ${event.date.toDateString()}</p>
            <p>Seats left: <span id="seats-${event.id}">${event.seats}</span></p>
            <button class="btn btn-success mt-auto" id="regBtn-${event.id}">Register</button>
          </div>
        </div>
      `;
      container.appendChild(card);

      document.getElementById(`regBtn-${event.id}`).onclick = () => {
        if (event.seats > 0) {
          event.seats--;
          document.getElementById(`seats-${event.id}`).textContent = event.seats;
          alert(`Registered for ${event.name}! Seats left: ${event.seats}`);
        } else {
          alert("No seats left.");
        }
      };
    }
  });
}

const form = document.getElementById("eventForm");
const formMessage = document.getElementById("formMessage");

form.addEventListener("submit", e => {
  e.preventDefault();

  const name = form.elements["name"].value.trim();
  const email = form.elements["email"].value.trim();
  const eventCategory = form.elements["event"].value;

  if (!name || !email || !eventCategory) {
    formMessage.textContent = "Fill all required fields and choose an event.";
    formMessage.className = "text-danger fw-bold";
    return;
  }

  const event = events.find(ev => ev.category === eventCategory && ev.seats > 0 && ev.date >= new Date());

  if (!event) {
    formMessage.textContent = `No available upcoming events for ${eventCategory}.`;
    formMessage.className = "text-danger fw-bold";
    return;
  }

  event.seats--;
  const seatSpan = document.getElementById(`seats-${event.id}`);
  if (seatSpan) seatSpan.textContent = event.seats;

  formMessage.textContent = `Thank you ${name}! Registered for ${event.name}.`;
  formMessage.className = "text-success fw-bold";

  form.reset();
});
