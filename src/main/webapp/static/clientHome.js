var menuData = [{
    id: 1,
    name: "Home"
}, {
    id: 2,
    name: "Skupy"

}, {
    id: 3,
    name: "Kontrakty"
}];


function showMenuClient(page) {

    /*if (page <= 7 ){

    }else{
        menuData[page - 1].selected = true;
    }*/

    menuData[page -1].selected = true;

    $("#panelMenu").dxMenu({
        dataSource: menuData,
        displayExpr: "name",
        selectionMode: "single",
        selectionByClick: true,
        onItemClick: function (data) {
            switch (data.itemData.id) {
                case 1:
                    location.href = './centre';
                    break;
                case 2:
                    location.href = './contract';
                    break;
                /*case 1:
                    location.href = './home';*/

            }
        }

    })
};

$(function loadPanel() {

    showMenuClient(1);

});