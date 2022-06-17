loadAllCustomer();

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
    alert("Clear")
    $("#cusIdAdd").val("");
    $("#cusNameAdd").val("");
    $("#cusAddressAdd").val("");
    $("#cusSalaryAdd").val("");
}

$("#clear-btn-cus").click(function () {
    alert("OK")
    clear();
});

function loadAllCustomer() {
    $("#cusTblBody").empty();
    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/customer",
        method:"GET",
        success:function (resp) {
            for (const customer of resp.data){
                let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`;
                $("#cusTblBody").append(row);
            }
            valueClick();
        },
        error:function (ob, errorSatus,t) {
            console.log(ob)
            console.log(errorSatus)
            console.log(t)
        }
    })
}

$("#addCus").click(function () {
    let serialize = $("#addCusForm").serialize();
   $.ajax({
      url:"http://localhost:8080/BackEnd_Web_exploded/customer",
       method:"POST",
       data:serialize,
       success:function (res) {
          clear();
          console.log(res.message)
           console.log(res)
            alert(res.message);
            loadAllCustomer();
       },
       error:function (ob,status,t,res) {
          alert(res.message+":"+res.data)
           console.log(ob)
           console.log(status)
       }
   }); 
});


function valueClick() {
    $("#cusTblBody>tr").click(function () {
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let address = $(this).children().eq(2).text();
        let salary = $(this).children().eq(3).text();


        $("#cusIdAdd").val(id);
        $("#cusNameAdd").val(name);
        $("#cusAddressAdd").val(address);
        $("#cusSalaryAdd").val(salary);
    });
}

$("#delCus").click(function () {
    let cusID = $("#cusIdAdd").val();
    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/customer" +cusID,
        method:"DELETE",
        success:function (res) {
            clear();
            console.log(res.message)
            console.log(res)
            alert(res.message);
            loadAllCustomer();
        },
        error:function (ob,status,t,res) {
            alert()
            console.log(ob)
            console.log(status)
        }
    })
});








