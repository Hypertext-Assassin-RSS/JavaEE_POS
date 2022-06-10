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