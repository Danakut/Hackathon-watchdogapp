let rowTouched;
let rowClicked;
let detailRow;


function highlightRow(event) {
    rowTouched = event.target;
    detailRow = rowTouched.nextElementSibling;

    rowTouched.classList.add("highlighted");
    detailRow.classList.add("highlighted");
}

function showRowDetail(event) {
    //only a cell is clicked upon, so we need its parent in order the get a whole row
    rowClicked = event.target.parentNode;
    detailRow = rowClicked.nextElementSibling;

    detailRow.classList.toggle("row_opened");

}

function revertHighlightRow(event) {
    rowTouched.classList.remove("highlighted");
    detailRow.classList.remove("highlighted");
}
