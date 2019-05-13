let rowTouched;

function highlightRow(event) {
    rowTouched = event.target;
    rowTouched.classList.add("highlighted");
}

function revertHighlightRow(event) {
    rowTouched.classList.remove("highlighted");
}

function showRowDetail(event) {
    //only a cell is clicked upon, so we need its parent in order the get a whole row
    let rowClicked = event.target.parentNode;
    rowClicked.classList.toggle("row_open");

    let detailRow1 = rowClicked.nextElementSibling;
    console.log(detailRow1);
    let detailRow2 = detailRow1.nextElementSibling;
    console.log(detailRow2);
    let detailRow3 = detailRow2.nextElementSibling;
    console.log(detailRow3);

    if (rowClicked.classList.contains("row_open")) {
        detailRow1.classList.remove("row_closed");
        detailRow2.classList.remove("row_closed");
        detailRow3.classList.remove("row_closed");
        detailRow1.classList.add("row_open");
        detailRow2.classList.add("row_open");
        detailRow3.classList.add("row_open");
    } else {
        detailRow1.classList.remove("row_open");
        detailRow2.classList.remove("row_open");
        detailRow3.classList.remove("row_open");
        detailRow1.classList.add("row_closed");
        detailRow2.classList.add("row_closed");
        detailRow3.classList.add("row_closed");
    }

}

