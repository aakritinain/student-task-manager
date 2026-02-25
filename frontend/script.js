function addTask() {
    const input = document.getElementById("taskInput");
    const task = input.value.trim();

    if (task === "") return;

    fetch("http://localhost:8080/addTask", {
        method: "POST",
        body: task
    }).then(() => {
        input.value = "";
        loadTasks();
    });
}

function deleteTask(index) {
    fetch("http://localhost:8080/deleteTask", {
        method: "POST",
        body: index.toString()
    }).then(() => loadTasks());
}

function completeTask(index) {
    fetch("http://localhost:8080/completeTask", {
        method: "POST",
        body: index.toString()
    }).then(() => loadTasks());
}

function loadTasks() {
    fetch("http://localhost:8080/getTasks")
    .then(res => res.text())
    .then(data => {
        const taskList = document.getElementById("taskList");
        const lines = data.trim().split("\n");

        let html = "";

        for (let i = 0; i < lines.length; i++) {
            if (lines[i] !== "") {
                const parts = lines[i].split("|");
                const task = parts[0];
                const done = parts[1] === "true";

                html += `<div class="task ${done ? "done" : ""}">
                            <span>${task}</span>
                            <div>
                                <button onclick="completeTask(${i})">✔</button>
                                <button class="delete-btn" onclick="deleteTask(${i})">Delete</button>
                            </div>
                         </div>`;
            }
        }

        taskList.innerHTML = html;
    });
}

window.onload = loadTasks;