
var RegCusID = /^[A-z/0-9]{5,10}$/;
var RegExCusName = /^[A-z ]{4,20}$/;
var RegExCusAddress = /^[0-9/A-z. ,]{7,}$/;
var RegExCusSalary = /^[0-9]{1,}[.]?[0-9]{1,2}$/;

$("#cusIdAdd").keydown(function (event) {
   if (event.key === "Enter") {

        if (RegCusID.test($("#cusIdAdd").val())){
            $("#error1").css("display","none")
            $("#cusNameAdd").focus().keydown(function (event) {
                if (event.key === "Enter") {

                    if (RegExCusName.test($("#cusNameAdd").val())){
                        $("#error2").css("display","none")
                        $("#cusAddressAdd").focus().keydown(function (event) {
                                if (event.key === "Enter") {

                                    if (RegExCusAddress.test($("#cusAddressAdd").val())){
                                        $("#error3").css("display","none")
                                        $("#cusSalaryAdd").focus().keydown(function (event) {
                                            if (event.key === "Enter") {


                                                    if (RegExCusSalary.test($("#cusSalaryAdd").val())){
                                                        $("#error4").css("display","none")
                                                        $("#addCus").focus().keydown(function (event) {
                                                            if (event.key === "Enter") {
                                                                clear();
                                                                $("#cusIdAdd").focus();
                                                            }
                                                        });
                                                    }else {

                                                    }

                                            }
                                        })
                                    }else {

                                    }

                                }
                            })
                    }else{

                        }

                }
            });
        }else{
            alert("Customer ID is a required field, Pattern C0001")
            $("error1").text("CusID");
            $("error1").css("color","red").css("font-weight","bold");
        }

   }
});

function clear() {
    $("#cusIdAdd").val("")
    $("#cusNameAdd").val("")
    $("#cusAddressAdd").val("")
    $("#cusSalaryAdd").val("")
}




