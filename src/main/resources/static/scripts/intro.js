const bookmark = document.getElementById("bookmark");
const aboutProject = document.getElementById("about");

function showAboutProject() {
    if (aboutProject.style.transform !== "none") {
        aboutProject.style.transform = "none";
        bookmark.style.background = "rgb(251,1,3)";
    } else {
        aboutProject.style.transform = "translate(50vw)";
        bookmark.style.background = "transparent";
    }
}