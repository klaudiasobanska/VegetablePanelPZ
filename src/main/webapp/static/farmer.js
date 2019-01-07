var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});

$(function () {



    showMenu(5);

    $("#searchBox").dxTextBox({
        placeholder: "Wyszukaj",
        showClearButton: true,
        mode: "search",
        onEnterKey: function (o) {
            farmerRefresh();
        }
    });

    $("#addButton").dxButton({
        icon: "add",
        onClick: function () {
            addOrEditFarmer(false);
        }
    });

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#farmerGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                addOrEditFarmer(true);
                farmerRefresh();
            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }
    });

    $("#deleteButton").dxButton({
        icon: "trash",
        onClick: function () {

            var dataGrid = $("#farmerGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                deleteFarmer(true);

            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }

    });

    showFarmerGrid();
});

function showFarmerGrid(){
    $.get('./vegetable/centre/user?userId='+userId, function (result) {

        console.log(result)
        var farmerDataSource = new DevExpress.data.DataSource({


            load: function (loadOptions) {
                console.log(loadOptions)
                var params = {
                    p: $("#searchBox").dxTextBox('instance').option('value'),
                    centreId: result.id
                };
                var d = $.Deferred();
                $.getJSON('/farmer/search', {
                    p: params.p ? params.p : "",
                    centreId: params.centreId ? params.centreId:-1,
                    page: loadOptions.skip / loadOptions.take,
                    size: loadOptions.take,
                    sort: sortString(loadOptions)
                }).done(function (result) {
                    d.resolve(result.content, {
                        totalCount: result.totalElements

                    });
                });
                return d.promise();
            }
        });


        /*show grid with farmers in farmer page*/
        $("#farmerGrid").dxDataGrid({
            dataSource: farmerDataSource,
            key: "id",
            columnAutoWidth: true,
            remoteOperations: {groupPaging: true},
            selection: {
                mode: "single"
            },
            scrolling: {
                "showScrollbar": "never"
            },
            paging: {
                pageSize: 3
            },
            pager: {
                showPageSizeSelector: true,
                allowedPageSizes: [3, 6, 12]
            },
            showBorders: true,

            hoverStateEnabled: true,

            columns: [{
                caption: "Nazwa",
                dataField: "name"
            }, {
                caption: "Miejscowość",
                dataField: "city"
            }, {
                caption: "Adres",
                dataField: "address"

            }, {
                caption: "Email",
                dataField: "email"

            }, {
                caption: "Numer telefonu",
                dataField: "phoneNumber",
                width: 150

            }]
        });
    });

};

function sortString(loadOptions) {

    /*settings of sorting for clint grid*/

    if(!loadOptions.sort) return 'id,asc';

    var sort=loadOptions.sort[0].selector;
    var sort1= loadOptions.sort[0].desc===true?',desc':',asc';
    if (sort == "phoneNumber"){
        sort = "phone_number"
    }

    var sort2 = sort + sort1;

    return sort2;

}

function getFarmer(){
    var dataGrid = $("#farmerGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}

function addOrEditFarmer(editTrueFlag){

    var farmer = getFarmer();

    $.get('./vegetable/centre/user?userId='+userId, function (result) {

        $("#addOrEditPopup").dxPopup({
            height: 270,
            width: 900
        }).dxPopup("show");

        var newFarmer = {
            name: "",
            city: "",
            address: "",
            email: "",
            phoneNumber: "",
            userId: "",

        };

        if (editTrueFlag == true) {
            newFarmer.name = farmer.name;
            newFarmer.city = farmer.city;
            newFarmer.address = farmer.address;
            newFarmer.email = farmer.email;
            newFarmer.phoneNumber = farmer.phoneNumber;
        } else {
            $("#addOrEditPopup").dxPopup({
                title: "Nowy Rolnik"
            });
            $("#addOrEditFarmerForm").val("");
        }


        $("#addOrEditFarmerForm").dxForm({
            formData: newFarmer,
            readOnly: false,
            colCount: 2,
            items: [{
                dataField: "name",
                label: {
                    text: "Nazwa"
                },
                validationRules: [{
                    type: "required",
                    message: "Nazwa jest wymagana"
                }]
            }, {
                dataField: "city",
                label: {
                    text: "Miejscowość"
                },
                validationRules: [{
                    type: "required",
                    message: "Miejscowość jest wymagana"
                }]
            }, {
                dataField: "address",
                label: {
                    text: "Adres"
                },
                validationRules: [{
                    type: "required",
                    message: "Adres jest wymagany"
                }]
            }, {
                dataField: "email",
                label: {
                    text: "Email"
                },
                validationRules: [{
                    type: "required",
                    message: "Email jest wymagany"
                }, {
                    type: "email",
                    message: "Email jest niepoprawny"
                }]
            }, {
                dataField: "phoneNumber",
                label: {
                    text: "Numer telefony"
                },
                validationRules: [{
                    type: "required",
                    message: "Numer telefonu jest wymagany"
                }]
            }]
        });

        $("#saveFarmerButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditFarmerForm").dxForm('instance');
                var newName = f.getEditor('name').option('value');
                var newCity = f.getEditor('city').option('value');
                var newAddress = f.getEditor('address').option('value');
                var newEmail = f.getEditor('email').option('value');
                var newPhoneNumber = f.getEditor('phoneNumber').option('value');

                var ret = f.validate();

                if (ret.isValid) {
                    newFarmer.name = newName;
                    newFarmer.city = newCity;
                    newFarmer.address = newAddress;
                    newFarmer.email = newEmail;
                    newFarmer.phoneNumber = newPhoneNumber;

                    newFarmer = JSON.stringify(newFarmer);

                    if (editTrueFlag) {
                        $.ajax({
                            url: './farmer/' + farmer.id,
                            type: 'put',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function (result) {
                                farmerRefresh();
                            },
                            data: newFarmer
                        });
                    } else {

                        $.ajax({
                            url: './vegetable/add/farmer?centreId='+result.id,
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                farmerRefresh();
                            },
                            data: newFarmer
                        });
                    }
                    $("#addOrEditPopup").dxPopup("hide");
                    showFarmerGrid();
                }

            }
        });

        $("#cancelFarmerButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        })
    });
}


function deleteFarmer(){

    var farmer = getFarmer();


    $.get('./vegetable/centre/user?userId='+userId, function (result) {
        $.post('./vegetable/delete/farmer?farmerId=' + farmer.id + "&centreId=" + result.id, function (result) {
                if (result.errorMsg) {
                    $("#errorDeleteToast").dxToast({
                        message: "Nastąpił błąd w trakcie usuwania",
                        type: "error",
                        displayTime: 2000
                    }).dxToast("show");

                } else {
                    $("#okDeleteToast").dxToast({
                        message: "Farmer został poprawnie usunięty",
                        type: "success",
                        displayTime: 2000
                    }).dxToast("show");
                    farmerRefresh();

                }
            }
        )
    });
}

function farmerRefresh(){

    var dataSource = $("#farmerGrid").dxDataGrid("getDataSource");
    dataSource.reload()
    var dataGridInstance = $("#farmerGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();
}

