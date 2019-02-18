const alarms = document.getElementById("alarms");
const events = document.getElementById("events");
const attended = document.getElementById("attended");

function showBoard(event) {

    let boardClicked = event.target;
    let boardName = boardClicked.dataset.board;

    if (alarms.classList.contains("unfolded")) {
        alarms.classList.remove("unfolded");
    }
    if (events.classList.contains("unfolded")) {
        events.classList.remove("unfolded");
    }
    if (attended.classList.contains("unfolded")) {
        attended.classList.remove("unfolded");
    }

    if (boardName === "alarms") {
        events.classList.remove("unfolded");
        attended.classList.remove("unfolded");
        alarms.classList.add("unfolded");
    }

    if (boardName === "events") {
        alarms.classList.remove("unfolded");
        attended.classList.remove("unfolded");
        events.classList.add("unfolded");
    }

    if (boardName === "attended") {
        alarms.classList.remove("unfolded");
        events.classList.remove("unfolded");
        attended.classList.add("unfolded");
    }

}