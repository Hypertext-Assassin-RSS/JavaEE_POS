
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
generateOrderID();

$("#oId").focus().keydown(function (e) {
    if (e.key == "Enter"){
        $("#idCmb").focus().keydown(function (e) {
            if (e.key == "Enter"){
                itemValidation();
            }
        })
    }
})

function itemValidation() {
    $("#itemIdCmb").focus().keydown(function (e) {
        if (e.key == "Enter"){
            $("#oQty").focus().keydown(function (e) {
                if (e.key == "Enter"){
                    $("#btnAddToCart").focus().keydown(function (e) {

                    });
                }

            })
        }
    })
}
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
            url:"http://localhost:8080/BackEnd_Web_exploded/order?object=item&itemCode="+itemCode,
            method:"GET",
            success:function (resp) {
                for (const item of resp.data){
                    $("#itemNameO").val(item.name)
                    $("#qtyOnHandO").val(item.QTY)
                    $("#priceO").val(item.price)
                }
            },error:function (ob,errorStatus,t) {
                console.log(ob)
                console.log(errorStatus)
                console.log(t)
            }
        })

    });
});


$("#btnAddToCart").click(function () {
    let itemOb = {
        itemCode:$("#itemIdCmb").val(),
        itemName: $("#itemNameO").val(),
        itemQty:$("#oQty").val(),
        itemPrice:$("#priceO").val()
    }

    orderDB.push(itemOb);

    console.log("order"+itemOb)
    console.log(orderDB)

    countSubTotal();
});

let preDiscount = 0;
let preBalance = 0;
let balance = 0;
function countBalance() {
    let total = parseInt($("#lblFullTotal").text());
    let cash = parseInt($("#cash").val());
    let discount = parseInt($("#discount").val());

    preDiscount = (total/100) * discount;
    console.log("preDis:"+preDiscount);
    preBalance = total - preDiscount;

    console.log("preBal:"+preBalance)
    balance = cash - preBalance;

    console.log("Bal:"+balance);
    $("#balanceO").val(balance);
}


$("#btnPurchase").click(function () {
    let order = {
        orderID: $("#oId").val(),
        orderDate: $("#iDate").val(),
        customerID:$("#idCmb").val(),
        total:$("#lblFullTotal").text()
    }
    countBalance();
    /*item:orderDB*/
    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/order",
        method:"POST",
        contentType:"application/json",
        data:JSON.stringify(order),
        success:function (res) {
                alert(res.message);
                loadAllItems();
                console.log(res.message)
                console.log(res)

            } ,error:function (ob, errorStatus) {
            console.log(ob);
            console.log(errorStatus)
        }


    })

});


function generateOrderID() {
    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/order?object=order",
        method:"GET",
        contentType:"application/json",
        success:function (res) {
            $("#oId").val(res.orderID)
            },error:function (ob, errorStatus) {
            console.log(ob);
            console.log(errorStatus)
        }

    })
}



let subtotal = 0;
let subtotalPre;
function countSubTotal() {
    let itemPrice = $("#priceO").val();
    let orderQty = $("#oQty").val();

    subtotalPre= itemPrice * orderQty;
    console.log(itemPrice)
    console.log(orderQty)
    console.log(subtotalPre)

    $("#lblSubTotal").text(subtotalPre+" LKR");
    subtotal = subtotalPre + subtotal;
    console.log(subtotal);

    $("#lblFullTotal").text(subtotal+" LKR")
}

