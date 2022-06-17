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
            cusValueClick();
        },
        error:function (ob, errorStatus,t) {
            console.log(ob)
            console.log(errorStatus)
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


function cusValueClick() {
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
        url:"http://localhost:8080/BackEnd_Web_exploded/customer?CusID=" +cusID,
        method:"DELETE",
        success:function (res) {
            alert(res.message)
            loadAllCustomer();
            console.log(res.message)
            console.log(res)
        },
        error:function (ob,status,t,res) {
            alert(res.data+":"+res.message)
            console.log(ob)
            console.log(status)
        }
    })
});



$("#updateCus").click(function () {
    var cusOb = {
        id: $("#cusIdAdd").val(),
        name: $("#cusNameAdd").val(),
        address: $("#cusAddressAdd").val(),
        salary: $("#cusSalaryAdd").val()
    }
    $.ajax({
        url: "http://localhost:8080/BackEnd_Web_exploded/customer",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(cusOb),
        success: function (res) {
            if (res.status === 200) {
                alert(res.message);
                loadAllCustomer();
                console.log(res.message)
                console.log(res)
            } else if (res.status === 400) {
                alert(res.message);
                console.log(res.message)
                console.log(res)
            } else {
                alert(res.data);
                console.log(res.message)
                console.log(res)
            }
        },
        error: function (ob, errorStatus) {
            console.log(ob);
            console.log(errorStatus)

        }
    });
});






