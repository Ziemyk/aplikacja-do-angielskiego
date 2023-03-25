const buttons = document.getElementsByTagName("button");
console.log("Odpaliłem ")
const buttonPressed = e => {
    var value = e.target.innerHTML;

    //var value = pole.innerHTML;

    // from here edit
    var response = fetch("/link/", {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(res => {

        console.log("Request complete! response:", res);
        return res;
    });
    //to here edit

    if (response.CzyByłoWHaśle) {
        console.log("Im in response = true :) it is all w prawo ")
        //nothing albo na zielono literka ale to raczej nie bo litemozerka  byc 2 razy w  hasle
    }
    else if (!response.CzyByłoWHaśle) {
        ChangeButtonColor(e.target)
    }
    else {
        console.log("something is wrong with response")
    }

}

function ChangeButtonColor(buttonClicked) {
    console.log("im in changeButtonCOLOR method :)")
    buttonClicked.style.style.backgroundColor = "red";
}

for (let button of buttons) {
    button.addEventListener("click", buttonPressed);
}
