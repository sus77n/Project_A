fetch("https://esgoo.net/api-tinhthanh/1/0.htm")
    .then(response => response.json())
    .then(data => {
        let provinces = data.data;
        let provincesSelect = document.getElementById('provinces');

        provincesSelect.innerHTML = `<option value=''>Select</option>`;

        provinces.forEach(value => {
            provincesSelect.innerHTML += `<option value="${value.id}">${value.name}</option>`;
        });

        $(provincesSelect).niceSelect('update');
    })
    .catch(error => {
        console.error("Error fetching provinces:", error);
    });

function fetchDistricts(provincesID) {
    fetch(`https://esgoo.net/api-tinhthanh/2/${provincesID}.htm`)
        .then(response => response.json())
        .then(data => {
            let districts = data.data;
            let districtsSelect = document.getElementById('districts');
            districtsSelect.innerHTML = "";

            districts.forEach(value => {
                districtsSelect.innerHTML += `<option value='${value.id}'>${value.name}</option>`;
            })

            $(districtsSelect).niceSelect('update');

        })
        .catch(error => {
            console.error('Lỗi khi gọi API:', error);
        });
}

function fetchWards(districtsID) {
    fetch(`https://esgoo.net/api-tinhthanh/3/${districtsID}.htm`)
        .then(response => response.json())
        .then(data => {
            let wards = data.data;
            let wardsSelect = document.getElementById('wards');

            wardsSelect.innerHTML = "";
            wards.forEach(value => {
                wardsSelect.innerHTML += `<option value='${value.id}'>${value.name}</option>`
            })

            $(wardsSelect).niceSelect('update');
        })
        .catch(error => {
            console.error('Lỗi khi gọi API:', error);
        });
}

function getProvinces(event) {
    fetchDistricts(event.target.value);
    document.getElementById('wards').innerHTML = `<option value=''>-- select wards --</option>`;
}

function getDistricts(event) {
    fetchWards(event.target.value);
}
