
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
            let defaultOp = `<option >Select Customer ID</option>`;
            $("#idCmb").append(defaultOp);
            for (const customer of resp.data){
                let row = `<option >${customer.id}</option>`;
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
            let defaultOp = `<option >Select Item Code</option>`;
            $("#itemIdCmb").append(defaultOp);
            for (const item of resp.data){
                let row = `<option>${item.id}</option>`
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


$(function loadCustomer(){
    $('#idCmb').trigger('change');
    $('#idCmb').change(function(){
        var cusID= $(this).val();
        console.log(cusID);
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/order?object=customer&cusID="+cusID,
            method:"GET",
            success:function (resp) {
                for (const customer of resp.data){
                    $("#inCusName").val(customer.name);
                    $('#inCusAddress').val(customer.address);
                    $("#inCusSalary").val(customer.salary);
                }

            },
            error:function (ob, errorStatus,t) {
                console.log(ob)
                console.log(errorStatus)
                console.log(t)
            }
        })

    });
});


$(function loadItem(){
    $('#itemIdCmb').trigger('change');
    $('#itemIdCmb').change(function(){
        var itemCode= $(this).val();
        console.log(itemCode);

        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/order?itemCode"+itemCode,
            method:"GET",
            success:function (resp) {
                for (const item of resp.data){

                }



            },error:function (ob,errorStatus,t) {
                console.log(ob)
                console.log(errorStatus)
                console.log(t)
            }
        })

    });
});
