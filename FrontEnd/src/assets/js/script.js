
$("#customer-click").click(function () {
    $("#order").css("display","none");
    $("#customer").css("display","block");
    $("#item").css("display","none");
    $("#orderDetailsSec").css("display","none");

    $("#home-click").css("color","black");
    $("#customer-click").css("color","white");
    $("#item-click").css("color","black");
    $("#order-click").css("color","black");
    $("#orderDetails-click").css("color","black");

    $("#cusIdAdd").focus();
});

$("#item-click").click(function () {
    $("#order").css("display","none");
    $("#customer").css("display","none");
    $("#item").css("display","block");
    $("#orderDetailsSec").css("display","none");

    $("#home-click").css("color","black");
    $("#customer-click").css("color","black");
    $("#item-click").css("color","white");
    $("#order-click").css("color","black");
    $("#orderDetails-click").css("color","black");

    $("#itemCode").focus();
});

$("#order-click").click(function () {
    $("#order").css("display","block");
    $("#customer").css("display","none");
    $("#item").css("display","onne");
    $("#orderDetailsSec").css("display","none");

    $("#home-click").css("color","black");
    $("#customer-click").css("color","black");
    $("#item-click").css("color","black");
    $("#order-click").css("color","white");
    $("#orderDetails-click").css("color","black");

    $("#oId").focus();
});

$("#orderDetails-click").click(function () {
    $("#order").css("display","none");
    $("#customer").css("display","none");
    $("#item").css("display","none");
    $("#orderDetailsSec").css("display","block");

    $("#home-click").css("color","black");
    $("#customer-click").css("color","black");
    $("#item-click").css("color","black");
    $("#order-click").css("color","black");
    $("#orderDetails-click").css("color","white");
});







