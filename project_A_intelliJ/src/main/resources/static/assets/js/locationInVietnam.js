fetch("https://esgoo.net/api-tinhthanh/1/0.htm")
    .then(response => response.json())
    .then(data => {
        let provinces = data.data;
        let provincesSelect = document.getElementById('provinces');

        provincesSelect.innerHTML = `<option value=''>Select</option>`;

        provinces.forEach(value => {
            provincesSelect.innerHTML += `<option value="${value.name}" data-id="${value.id}">${value.name}</option>`;
        });

        $(provincesSelect).niceSelect('update');
    })
    .catch(error => console.error("Error fetching provinces:", error));

// Fetch districts based on selected province ID
function fetchDistricts(provinceID) {
    fetch(`https://esgoo.net/api-tinhthanh/2/${provinceID}.htm`)
        .then(response => response.json())
        .then(data => {
            let districts = data.data;
            let districtsSelect = document.getElementById('districts');

            districtsSelect.innerHTML = `<option value=''>Select</option>`;

            districts.forEach(value => {
                districtsSelect.innerHTML += `<option value="${value.name}" data-id="${value.id}">${value.name}</option>`;
            });

            $(districtsSelect).niceSelect('update');
        })
        .catch(error => console.error("Error fetching districts:", error));
}

// Fetch wards based on selected district ID
function fetchWards(districtID) {
    fetch(`https://esgoo.net/api-tinhthanh/3/${districtID}.htm`)
        .then(response => response.json())
        .then(data => {
            let wards = data.data;
            let wardsSelect = document.getElementById('wards');

            wardsSelect.innerHTML = `<option value=''>Select</option>`;

            wards.forEach(value => {
                wardsSelect.innerHTML += `<option value="${value.name}" data-id="${value.id}">${value.name}</option>`;
            });

            $(wardsSelect).niceSelect('update');
        })
        .catch(error => console.error("Error fetching wards:", error));
}

// Handle province selection
function getProvinces(event) {
    let selectedOption = event.target.options[event.target.selectedIndex];
    let provinceId = selectedOption.getAttribute("data-id");

    fetchDistricts(provinceId);
    document.getElementById('wards').innerHTML = `<option value=''>Select</option>`;

    updateAddress();
}

// Handle district selection
function getDistricts(event) {
    let selectedOption = event.target.options[event.target.selectedIndex];
    let districtId = selectedOption.getAttribute("data-id");

    fetchWards(districtId);
    updateAddress();
}

// Update hidden address field
function updateAddress() {
    let province = document.getElementById('provinces').options[document.getElementById('provinces').selectedIndex].value;
    let district = document.getElementById('districts').options[document.getElementById('districts').selectedIndex].value;
    let ward = document.getElementById('wards').options[document.getElementById('wards').selectedIndex].value;
    let detail = document.getElementById('detailAddr').value;

    let fullAddress = [detail, ward, district, province].filter(Boolean).join(", ");
    document.getElementById("fullAddress").value = fullAddress;
    console.log("address: " + fullAddress)
}