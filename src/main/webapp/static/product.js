var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});


$(function () {

    showMenu(3);

    $("#searchBox").dxTextBox({
        placeholder: "Wyszukaj",
        showClearButton: true,
        mode: "search",
        onEnterKey: function (o) {
            productRefresh();
        }
    });

    $("#addButton").dxButton({
        icon: "add",
        onClick: function () {
            addOrEditProduct(false);
        }
    });

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#productGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                addOrEditProduct(true);
                productRefresh();
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

            var dataGrid = $("#productGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                deleteProduct();

            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }

    });

    showProductGrid();

});
function showProductGrid() {
    $.get('./vegetable/centre/user?userId=' + userId, function (result) {
        var productDataSource = new DevExpress.data.DataSource({


            load: function (loadOptions) {
                var params = {
                    param: $("#searchBox").dxTextBox('instance').option('value'),
                    centreId: result.id
                };
                var d = $.Deferred();
                $.getJSON('./product/search', {
                    param: params.param ? params.param : "",
                    centreId: params.centreId ? params.centreId : -1,
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


        /*show grid with clients in clint page*/
        $("#productGrid").dxDataGrid({
            dataSource: productDataSource,
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
                caption: "Indeks",
                dataField: "index",
                width: 200
            }, {
                caption: "Nazwa",
                dataField: "name"
            }, {
                caption: "Cena",
                dataField: "price",
                width: 200
            }, {
                caption: "Jednostka miary",
                dataField: "unitName",
                width: 250

            }, {
                caption: "Stawka VAT",
                dataField: "vatName",
                width: 200

            }]
        });
    });

};

function sortString(loadOptions) {

    /*settings of sorting for clint grid*/

    if(!loadOptions.sort) return 'id,asc';

    var sort=loadOptions.sort[0].selector;
    var sort1= loadOptions.sort[0].desc===true?',desc':',asc';
    if (sort == "unitId"){
        sort = "unit_id"
    }

    var sort2 = sort + sort1;

    return sort2;

}

function productRefresh(){
    var dataSource = $("#productGrid").dxDataGrid("getDataSource");
    dataSource.reload()
    var dataGridInstance = $("#productGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();
}

function getProduct() {
    var dataGrid = $("#productGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}



function addOrEditProduct(editTrueFlag){

    var productE = getProduct();

    $.get('./vegetable/centre/user?userId='+userId, function (result) {

        $("#addOrEditPopup").dxPopup({
            height: 270,
            width: 900
        }).dxPopup("show");

        var newProduct = {
            index: "",
            name: "",
            price: "",
            vat: "",
            unitId: "",
            vatName: "",
            unitName: "",
            vegetableCentreId: ""

        };

        if (editTrueFlag == true) {
            newProduct.name = productE.name;
            newProduct.price = productE.price;
            newProduct.vatName = productE.vatName;
            newProduct.unitName = productE.unitName;
            newProduct.index = productE.index;
        } else {
            $("#addOrEditPopup").dxPopup({
                title: "Nowy Klient"
            });
            $("#addOrEditProductForm").val("");
            productE = {
                unitId: "",
                vat: ""
            }

        }

        $("#addOrEditProductForm").dxForm({
            formData: newProduct,
            readOnly: false,
            colCount: 2,
            items: [{
                dataField: "index",
                label: {
                    text: "Indeks"
                },
                editorOptions: {
                    maxLength: 20
                },
                validationRules: [{
                    type: "required",
                    message: "Indeks jest wymagany"
                }, {
                    type: "stringLength",
                    max: 15,
                    message: "Indeks może mieć maksymalnie 15 znaków"
                }]
            }, {
                dataField: "name",
                label: {
                    text: "Nazwa"
                },
                validationRules: [{
                    type: "required",
                    message: "Nazwa jest wymagana"
                }]
            }, {
                dataField: "price",
                label: {
                    text: "Cena"
                },
                validationRules: [{
                    type: "required",
                    message: "Cena jest wymagana"
                }]
            }, {
                dataField: "vatName",
                editorType: "dxSelectBox",
                label: {
                    text: "Stawka VAT"
                },
                validationRules: [{
                    type: "required",
                    message: "Stawka VAT jest wymagana"
                }],
                editorOptions: {
                    dataSource: "./vat/all",
                    displayExpr: 'name',
                    valueExpr: 'id',
                    value: productE.vat,
                    showClearButton: false,
                    searchEnabled: true
                }
            }, {
                dataField: "unitName",
                editorType: "dxSelectBox",
                label: {
                    text: "Jednostka masy"
                },
                validationRules: [{
                    type: "required",
                    message: "Jednostka masy jest wymagana"
                }],
                editorOptions: {
                    dataSource: "./unit/all",
                    displayExpr: 'name',
                    valueExpr: 'id',
                    value: productE.unitId,
                    showClearButton: false,
                    searchEnabled: true
                }
            }]
        });

        $("#saveProductButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditProductForm").dxForm('instance');
                var newName = f.getEditor('name').option('value');
                var newPrice = f.getEditor('price').option('value');
                var newVat = f.getEditor('vatName').option('value');
                var newUnit = f.getEditor('unitName').option('value');
                var newIndex = f.getEditor('index').option('value');


                var ret = f.validate();

                if (ret.isValid) {
                    newProduct.name = newName;
                    newProduct.price = newPrice;
                    newProduct.vat = newVat;
                    newProduct.unitId = newUnit;
                    newProduct.index = newIndex;
                    newProduct.unitName = "";
                    newProduct.vatName = "";
                    newProduct.vegetableCentreId= result.id;

                    newProduct = JSON.stringify(newProduct);

                    if (editTrueFlag) {
                        $.ajax({
                            url: './product/' + productE.id,
                            type: 'put',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function (result) {
                                productRefresh();
                            },
                            data: newProduct
                        });
                    } else {

                        $.ajax({
                            url: './product/add',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                productRefresh();
                            },
                            data: newProduct
                        });
                    }
                    $("#addOrEditPopup").dxPopup("hide");
                }

            }
        });

        $("#cancelProductButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        })
    });
}

function deleteProduct(){

    var product = getProduct();


    $.post('/product/delete?id=' + product.id, function (result) {
            if(result.errorMsg){
                $("#errorDeleteToast").dxToast({
                    message: "Nastąpił błąd w trakcie usuwania",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            } else {
                $("#okDeleteToast").dxToast({
                    message: "Towar został poprawnie usunięty",
                    type: "success",
                    displayTime: 2000
                }).dxToast("show");
                productRefresh();

            }
        }
    )
}
