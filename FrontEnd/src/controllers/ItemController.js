loadAllItems();


var RegItemID = /^[A-z/0-9]{5,10}$/;
var RegExItemName = /^[A-z ]{4,20}$/;
var RegExItemQTY = /^[0-9/]{1,10}$/;
var RegExPrice = /^[0-9]{1,}[.]?[0-9]{1,2}$/;


$("#itemCode").keydown(function (event) {
    if (event.key === "Enter") {
            if (RegItemID.test($("#itemCode").val())){
                $("#itemName").focus().keydown(function (event) {
                    if (event.key === "Enter") {
                            if (RegExItemName.test($("#itemName").val())){
                                $("#itemQty").focus().keydown(function (event) {
                                    if (event.key === "Enter") {
                                            if (RegExItemQTY.test($("#itemQty").val())){
                                                $("#itemPrice").focus().keydown(function (event) {
                                                    if (event.key === "Enter") {
                                                            if (RegExPrice.test($("#itemPrice").val())){
                                                                $("#addItemOutBtn").focus().keydown(function (event) {
                                                                    if (event.key === "Enter") {
                                                                        clear();
                                                                        $("#itemCode").focus();

                                                                    }
                                                                });
                                                            }else{

                                                            }

                                                    }
                                                })
                                            }else{

                                            }

                                    }
                                })
                            }else{

                            }

                    }
                });
            }else{

            }

    }
});

function clear() {
    $("#itemCode").val("")
    $("#itemName").val("")
    $("#itemQty").val("")
    $("#itemPrice").val("")
}


function loadAllItems() {
    $("#itemTblBody").empty();
    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/item",
        method:"GET",
        success:function (resp) {
            for (const item of resp.data){
                let row = `<tr><td>${item.id}</td><td>${item.name}</td><td>${item.QTY}</td><td>${item.price}</td></tr>`
                $("#itemTblBody").append(row);
            }
            valueClick();

        },error:function (ob,errorStatus,t) {
            console.log(ob)
            console.log(errorStatus)
            console.log(t)
        }
    })

}

function valueClick() {
    $("#itemTblBody>tr").click(function () {
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let QTY = $(this).children().eq(2).text();
        let price = $(this).children().eq(3).text();


        $("#itemCode").val(id)
        $("#itemName").val(name)
        $("#itemQty").val(QTY)
        $("#itemPrice").val(price)
    });
}


$("#addItem").click(function () {
    let serialize = $("#addItemForm").serialize();

    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/item",
        method:"POST",
        data:serialize,
        success:function (resp) {
            loadAllItems();
            alert(resp.message);
        },
        error:function (ob,errorStatus,t) {
            alert(errorStatus);
            console.log(ob)
            console.log(errorStatus)
        }
    })

});