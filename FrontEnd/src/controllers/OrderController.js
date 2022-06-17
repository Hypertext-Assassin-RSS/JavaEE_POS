
let New = new Date();

let year = New.getFullYear()
let month = New.getMonth()
let day = New.getDate();

if (New.getMonth() < 10) {
    month = "0" + month
}

let today = year +"-"+month +"-"+day

$("#iDate").val(today)

loadAllCusID();
loadAllItemCode();


function loadAllCusID() {
    let i = 1
    $("#idCmb").empty();
        $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/customer",
        method:"GET",
        success:function (resp) {
            for (const customer of resp.data){
                let row = `<option value= {i}>${customer.id}</option>`;
                $("#idCmb").append(row);
                i = i+1;
            }

        },
        error:function (ob, errorStatus,t) {
            console.log(ob)
            console.log(errorStatus)
            console.log(t)
        }
    })
}

function loadAllItemCode() {
    let i = 1
    $("#itemIdCmb").empty();
    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/item",
        method:"GET",
        success:function (resp) {
            for (const item of resp.data){
                let row = `<option value= {i}>${item.id}</option>`
                $("#itemIdCmb").append(row);
                i = i+1;
            }


        },error:function (ob,errorStatus,t) {
            console.log(ob)
            console.log(errorStatus)
            console.log(t)
        }
    })
}

